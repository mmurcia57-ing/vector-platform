package com.vector.bff.intelligence;

import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Change;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.Incident;
import com.vector.bff.canonical.MetricObservation;
import com.vector.bff.canonical.MonitoringEvent;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.Service;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.TemporalSemantics;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class DeterministicIntelligenceServiceTests {
	private static final Instant EVALUATED_AT = Instant.parse("2025-02-01T00:00:00Z");
	private static final IntelligencePolicy POLICY = new IntelligencePolicy("test-policy-v1", 2, 2);

	@Test
	void createsAnEvidenceBackedRiskFindingForExplicitRecurringOperationalContext() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var result = new DeterministicIntelligenceService(repository).evaluate(input(
				service(IdentityResolutionState.INFERRED),
				List.of(event("event-1")), List.of(incident("incident-1"), incident("incident-2")),
				List.of(evidence("evidence-1"), evidence("evidence-2")), null, null));

			var finding = result.riskFinding().orElseThrow();
			assertThat(finding.metadata().identityState()).isEqualTo(IdentityResolutionState.INFERRED);
			assertThat(finding.metadata().sourceAuthority()).isEqualTo(new SourceAuthority("VECTOR-derived", true));
			assertThat(finding.explanation()).contains("2 incident(s)", "1 monitoring event(s)", "2 Evidence record(s)");
			assertThat(finding.explanation()).doesNotContainIgnoringCase("cause", "score", "priority");
			assertThat(repository.find("RiskFinding", finding.metadata().canonicalId())).isPresent();
		}
	}

	@Test
	void preservesMissingTelemetryAsALimitationAndDoesNotFabricateAFinding() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var result = new DeterministicIntelligenceService(repository).evaluate(input(
				service(IdentityResolutionState.CONFIRMED), List.of(), List.of(incident("incident-1"), incident("incident-2")),
				List.of(evidence("evidence-1"), evidence("evidence-2")), null, null));

			assertThat(result.riskFinding()).isEmpty();
			assertThat(result.limitations()).contains("No operational observations are available; missing telemetry is not healthy.");
			assertThat(repository.findAll("RiskFinding")).isEmpty();
		}
	}

	@Test
	void preservesAssociationAsEvidenceBackedAndNonCausal() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var correlation = new CorrelationContext("explicit temporal/contextual comparison", "before/during/after deployment window",
				List.of("evidence-1"), "association does not establish causation");
			var result = new DeterministicIntelligenceService(repository).evaluate(input(
				service(IdentityResolutionState.UNRESOLVED), List.of(event("event-1")),
				List.of(incident("incident-1"), incident("incident-2")), List.of(evidence("evidence-1"), evidence("evidence-2")),
				new Change(metadata("change-1", IdentityResolutionState.UNRESOLVED), "declared change", null), correlation));

			assertThat(result.correlationContext()).isEqualTo(correlation);
			assertThat(result.riskFinding()).isPresent();
			assertThat(result.riskFinding().orElseThrow().metadata().identityState()).isEqualTo(IdentityResolutionState.UNRESOLVED);
			assertThat(result.correlationContext().limitations()).contains("does not establish causation");
		}
	}

	@Test
	void rejectsCorrelationWithoutExplicitSupportingEvidence() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var correlation = new CorrelationContext("temporal comparison", "during deployment", List.of("missing-evidence"), "limited");
			assertThatIllegalArgumentException().isThrownBy(() -> new DeterministicIntelligenceService(repository).evaluate(input(
				service(IdentityResolutionState.CONFIRMED), List.of(event("event-1")),
				List.of(incident("incident-1"), incident("incident-2")), List.of(evidence("evidence-1"), evidence("evidence-2")),
				new Change(metadata("change-1", IdentityResolutionState.CONFIRMED), "declared change", null), correlation)));
		}
	}

	private static IntelligenceInput input(Service service, List<MonitoringEvent> events, List<Incident> incidents,
			List<Evidence> evidence, Change change, CorrelationContext correlation) {
		var metrics = events.isEmpty() ? List.<MetricObservation>of() : List.of(
			new MetricObservation(metadata("metric-1", service.metadata().identityState()), "latency", "p95=500ms", service.metadata().canonicalId(), null));
		return new IntelligenceInput(service, events, incidents, List.of(), List.of(),
			metrics,
			evidence, change, correlation, POLICY, EVALUATED_AT);
	}

	private static Service service(IdentityResolutionState state) {
		return new Service(metadata("service-1", state), "Payments", "area-1", "degraded");
	}

	private static MonitoringEvent event(String id) {
		return new MonitoringEvent(metadata(id, IdentityResolutionState.CONFIRMED), "latency", "degraded", "service-1");
	}

	private static Incident incident(String id) {
		return new Incident(metadata(id, IdentityResolutionState.CONFIRMED), "availability disruption", "service-1", null);
	}

	private static Evidence evidence(String id) {
		return new Evidence(metadata(id, IdentityResolutionState.CONFIRMED), "observation", "degradation context", "synthetic limitation");
	}

	private static CanonicalMetadata metadata(String id, IdentityResolutionState state) {
		return new CanonicalMetadata(id, state, new TemporalSemantics(EVALUATED_AT, EVALUATED_AT, EVALUATED_AT, null, null),
			new Provenance(List.of("source-" + id), "test", "synthetic"), SourceAuthority.unknown());
	}
}
