package com.vector.bff.canonical;

import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CanonicalCoreTests {
	private static final Instant OBSERVED = Instant.parse("2025-01-01T00:00:00Z");

	@Test
	void catalogContainsExactlyTheApprovedV1CanonicalVocabulary() {
		assertThat(CanonicalEntityCatalog.v1Types()).containsExactly(
			"AreaDomain", "Service", "ConfigurationItem", "MonitoringEvent", "Incident", "Problem",
			"Change", "Deployment", "SLO", "SLOObservation", "Commitment", "ImprovementAction",
			"OutcomeVerification", "Evidence", "RiskFinding", "MetricObservation", "SourceReference");
	}

	@Test
	void allCanonicalContractsExposeRequiredIdentityProvenanceAndTemporalContext() {
		var entities = entities();

		assertThat(entities).hasSize(17);
		assertThat(entities).extracting(CanonicalEntity::canonicalType).containsExactlyElementsOf(CanonicalEntityCatalog.v1Types());
		assertThat(entities).allSatisfy(entity -> {
			assertThat(entity.metadata().canonicalId()).isNotBlank();
			assertThat(entity.metadata().identityState()).isEqualTo(IdentityResolutionState.INFERRED);
			assertThat(entity.metadata().provenance().sourceReferenceIds()).containsExactly("source-ref-1");
			assertThat(entity.metadata().temporal().observedAt()).isEqualTo(OBSERVED);
		});
	}

	@Test
	void serializationIsStableAndDoesNotChangeIdentityOrAuthority() {
		var entity = new Service(metadata("service-1"), "Payments", "area-1", "degraded");
		var serialized = CanonicalSerializer.serialize(entity);

		assertThat(serialized).isEqualTo(CanonicalSerializer.serialize(entity));
		assertThat(serialized).contains("Service", "canonicalId=service-1", "identityState=INFERRED");
		assertThat(entity.metadata().sourceAuthority()).isEqualTo(SourceAuthority.unknown());
	}

	@Test
	void invalidIdsAndTemporalIntervalsAreRejected() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CanonicalMetadata("", IdentityResolutionState.UNRESOLVED,
			TemporalSemantics.empty(), Provenance.nativeOrUnspecified(), SourceAuthority.unknown()));
		assertThatIllegalArgumentException().isThrownBy(() -> new TemporalSemantics(null, null, null,
			OBSERVED, OBSERVED.minusSeconds(1)));
	}

	@Test
	void sqliteRoundTripPreservesCanonicalPayloadProvenanceAndTime() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var saved = repository.save(new Service(metadata("service-1"), "Payments", "area-1", "stable"));
			var loaded = repository.find("Service", "service-1").orElseThrow();

			assertThat(loaded).isEqualTo(saved);
			assertThat(loaded.payload()).contains("Service", "sourceReferenceIds=source-ref-1");
			assertThat(loaded.observedAt()).isEqualTo(OBSERVED);
			assertThat(repository.findAll("Service")).hasSize(1);
		}
	}

	private static CanonicalMetadata metadata(String id) {
		return new CanonicalMetadata(id, IdentityResolutionState.INFERRED,
			new TemporalSemantics(null, OBSERVED, OBSERVED, null, null),
			new Provenance(List.of("source-ref-1"), "tested-mapping", "source limitations retained"),
			SourceAuthority.unknown());
	}

	private static List<CanonicalEntity> entities() {
		var metadata = metadata("id");
		return List.of(
			new AreaDomain(metadata, "Technology", "platform"),
			new Service(metadata, "Payments", "id", "stable"),
			new ConfigurationItem(metadata, "runtime", "id"),
			new MonitoringEvent(metadata, "latency", "degraded", "id"),
			new Incident(metadata, "outage", "id", "id"),
			new Problem(metadata, "recurring failure", "id", "id"),
			new Change(metadata, "release", "id"),
			new Deployment(metadata, "release deployment", "id", "id"),
			new SLO(metadata, "availability", "id"),
			new SLOObservation(metadata, "below target", "id", "99.0"),
			new Commitment(metadata, "address risk", "id", "OPEN"),
			new ImprovementAction(metadata, "rotate dependency", "id", "COMPLETED"),
			new OutcomeVerification(metadata, "PERSISTENT", "evidence-1", "before/after"),
			new Evidence(metadata, "observation", "service degraded", "stale after 1h"),
			new RiskFinding(metadata, "persistent degradation", "id", "repeated evidence", "evidence-1"),
			new MetricObservation(metadata, "latency", "p95=500ms", "id", "id"),
			new SourceReference(metadata, "synthetic-source", "native-1", "service-1", null));
	}
}
