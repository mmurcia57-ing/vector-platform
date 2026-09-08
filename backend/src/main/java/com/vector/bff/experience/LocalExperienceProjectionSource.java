package com.vector.bff.experience;

import com.vector.bff.canonical.AreaDomain;
import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.Incident;
import com.vector.bff.canonical.MonitoringEvent;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.Service;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.SourceReference;
import com.vector.bff.canonical.TemporalSemantics;
import com.vector.bff.configuration.VectorReliabilityPolicyProperties;
import com.vector.bff.evidence.EvidencePath;
import com.vector.bff.intelligence.DeterministicIntelligenceService;
import com.vector.bff.intelligence.IntelligenceInput;
import com.vector.bff.intelligence.IntelligencePolicy;
import com.vector.bff.persistence.CanonicalRepository;

import java.time.Instant;
import java.util.List;

/** Deterministic local application data used to demonstrate the first vertical slice. */
public final class LocalExperienceProjectionSource implements ExperienceProjectionSource {
    private static final Instant OBSERVED_AT = Instant.parse("2025-01-01T00:00:00Z");
    private final PreparedExperienceContext prepared;

    public LocalExperienceProjectionSource(CanonicalRepository repository, EvidencePath evidencePath,
            VectorReliabilityPolicyProperties policy) {
        var area = new AreaDomain(metadata("area-platform"), "Platform", "technology platform");
        var service = new Service(metadata("service-payments"), "Payments", area.metadata().canonicalId(), "degraded");
        var event = new MonitoringEvent(metadata("event-payments-latency"), "latency", "degraded", service.metadata().canonicalId());
        var incidents = List.of(
            new Incident(metadata("incident-payments-1"), "Payment latency degradation", service.metadata().canonicalId(), null),
            new Incident(metadata("incident-payments-2"), "Payment latency recurrence", service.metadata().canonicalId(), null));
        var sourceOne = new SourceReference(metadata("source-observability-1"), "local-deterministic-dataset", "obs-001", "payment latency", "local fixture");
        var sourceTwo = new SourceReference(metadata("source-incident-1"), "local-deterministic-dataset", "inc-001", "payment incident", "local fixture");
        var evidenceOne = new Evidence(new CanonicalMetadata("evidence-latency", IdentityResolutionState.CONFIRMED,
            new TemporalSemantics(OBSERVED_AT, OBSERVED_AT, OBSERVED_AT, null, null),
            new Provenance(List.of(sourceOne.metadata().canonicalId()), "local-dataset-v1", "deterministic local observation"),
            new SourceAuthority("local-deterministic-dataset", true)), "operational-observation", "Payments latency degraded", "local deterministic fixture");
        var evidenceTwo = new Evidence(new CanonicalMetadata("evidence-incident", IdentityResolutionState.CONFIRMED,
            new TemporalSemantics(OBSERVED_AT, OBSERVED_AT, OBSERVED_AT, null, null),
            new Provenance(List.of(sourceTwo.metadata().canonicalId()), "local-dataset-v1", "deterministic local incident record"),
            new SourceAuthority("local-deterministic-dataset", true)), "incident-record", "Payments degradation recurred", "local deterministic fixture");
        evidencePath.save(evidenceOne, List.of(sourceOne));
        evidencePath.save(evidenceTwo, List.of(sourceTwo));

        var result = new DeterministicIntelligenceService(repository).evaluate(new IntelligenceInput(
            service, List.of(event), incidents, List.of(), List.of(), List.of(), List.of(evidenceOne, evidenceTwo),
            null, null, new IntelligencePolicy(policy.reference(), policy.minimumRecurringIncidents(),
                policy.minimumSupportingEvidence()), OBSERVED_AT));
        var risk = result.riskFinding().orElseThrow(() -> new IllegalStateException("local deterministic data must produce a RiskFinding"));
        var quality = new ProjectionQuality("local deterministic dataset", OBSERVED_AT.toString(),
            "CONFIRMED source references; VECTOR-derived explanation", List.of(),
            List.of("No causal attribution is asserted."), List.of("Local dataset is demonstrative."), false, false);
        prepared = new PreparedExperienceContext(
            List.of(new AreaDomainProjection(area.metadata().canonicalId(), area.name(), "ATTENTION")),
            List.of(new ServiceProjection(service.metadata().canonicalId(), service.name(), service.areaDomainId(), service.conditionContext())),
            List.of(new RiskFindingProjection(risk.metadata().canonicalId(), risk.serviceId(), risk.condition(), risk.explanation(), risk.evidenceBasis())),
            List.of(toProjection(evidenceOne, service.metadata().canonicalId(), risk.metadata().canonicalId()), toProjection(evidenceTwo, service.metadata().canonicalId(), risk.metadata().canonicalId())),
            List.of(), List.of(), List.of(), quality);
    }

    @Override
    public PreparedExperienceContext load(ProjectionRequest request) {
        return prepared;
    }

    private static EvidenceProjection toProjection(Evidence evidence, String serviceId, String riskFindingId) {
        return new EvidenceProjection(evidence.metadata().canonicalId(), serviceId, riskFindingId,
            evidence.supportedClaim(), evidence.metadata().provenance().sourceReferenceIds(), evidence.limitations(), OBSERVED_AT);
    }

    private static CanonicalMetadata metadata(String id) {
        return new CanonicalMetadata(id, IdentityResolutionState.CONFIRMED,
            new TemporalSemantics(OBSERVED_AT, OBSERVED_AT, OBSERVED_AT, null, null),
            Provenance.nativeOrUnspecified(), SourceAuthority.unknown());
    }
}
