package com.vector.bff.intelligence;

import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.RiskFinding;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.TemporalSemantics;
import com.vector.bff.persistence.CanonicalRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Deterministic, evidence-first intelligence with no scoring, identity resolution, or causal attribution. */
public final class DeterministicIntelligenceService {
	private final CanonicalRepository repository;

	public DeterministicIntelligenceService(CanonicalRepository repository) {
		this.repository = repository;
	}

	public IntelligenceResult evaluate(IntelligenceInput input) {
		var serviceId = input.service().metadata().canonicalId();
		var incidents = input.incidents().stream().filter(incident -> serviceId.equals(incident.serviceId())).toList();
		var events = input.monitoringEvents().stream().filter(event -> serviceId.equals(event.serviceId())).toList();
		var metrics = input.metricObservations().stream().filter(metric -> serviceId.equals(metric.serviceId())).toList();
		var evidenceIds = input.supportingEvidence().stream().map(evidence -> evidence.metadata().canonicalId())
			.collect(java.util.stream.Collectors.toCollection(HashSet::new));
		var limitations = new ArrayList<String>();

		validateCorrelation(input.correlationContext(), input.change(), evidenceIds);
		if (events.isEmpty() && input.sloObservations().isEmpty() && input.metricObservations().isEmpty()) {
			limitations.add("No operational observations are available; missing telemetry is not healthy.");
		}
		if (incidents.size() < input.policy().minimumRecurringIncidents()) {
			limitations.add("Recurrence threshold from the explicit policy is not met.");
		}
		if (evidenceIds.size() < input.policy().minimumSupportingEvidence()) {
			limitations.add("Supporting Evidence is insufficient for a RiskFinding.");
		}
		if (input.service().metadata().identityState() == IdentityResolutionState.UNRESOLVED) {
			limitations.add("Service identity remains UNRESOLVED.");
		}

		Optional<RiskFinding> finding = Optional.empty();
		var hasOperationalContext = !events.isEmpty() || !input.sloObservations().isEmpty() || !metrics.isEmpty();
		var hasSufficientRecurrence = incidents.size() >= input.policy().minimumRecurringIncidents();
		var hasSufficientEvidence = evidenceIds.size() >= input.policy().minimumSupportingEvidence();
		if (hasOperationalContext && hasSufficientRecurrence && hasSufficientEvidence) {
			var sortedEvidenceIds = evidenceIds.stream().sorted().toList();
			var problemContext = input.problems().stream()
				.filter(problem -> serviceId.equals(problem.serviceId()) || incidents.stream()
					.anyMatch(incident -> incident.metadata().canonicalId().equals(problem.incidentId())))
				.map(problem -> problem.condition()).sorted().findFirst().orElse("available incident context");
			var findingId = "risk-finding:" + serviceId + ":" + input.evaluatedAt();
			var metadata = new CanonicalMetadata(findingId, input.service().metadata().identityState(),
				new TemporalSemantics(null, input.evaluatedAt(), null, null, null),
				new Provenance(sortedEvidenceIds, input.policy().policyReference(), "No score, root-cause, or causal attribution is asserted."),
				new SourceAuthority("VECTOR-derived", true));
			var explanation = "Evidence-backed recurrence context: " + incidents.size() + " incident(s), "
				+ events.size() + " monitoring event(s), and " + sortedEvidenceIds.size() + " Evidence record(s); "
				+ "Problem context: " + problemContext + ".";
			var created = new RiskFinding(metadata, "Persistent reliability condition", serviceId, explanation,
				String.join(",", sortedEvidenceIds));
			repository.save(created);
			finding = Optional.of(created);
		}

		return new IntelligenceResult(finding, incidents.size(), events.size(), metrics.size(),
			input.sloObservations().size(), input.correlationContext(), limitations);
	}

	private static void validateCorrelation(CorrelationContext context, com.vector.bff.canonical.Change change,
			Set<String> evidenceIds) {
		if (context == null) return;
		if (change == null) {
			throw new IllegalArgumentException("Change context is required for correlation");
		}
		if (!evidenceIds.containsAll(context.evidenceIds())) {
			throw new IllegalArgumentException("Correlation context must reference supplied Evidence");
		}
	}
}
