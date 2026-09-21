package com.vector.bff.experience;

import com.vector.bff.canonical.*;
import com.vector.bff.intelligence.*;
import com.vector.bff.journey.J02.ChangeAssociationJourneyService;
import com.vector.bff.persistence.CanonicalRepository;
import java.time.Instant;
import java.util.List;

/** Demo-safe local J02 adapter. Synthetic context is explicit and never a corporate claim. */
public final class LocalChangeAssociationExperience {
    private static final Instant AT = Instant.parse("2025-01-01T00:00:00Z");
    private final ChangeAssociationJourneyService journey;

    public LocalChangeAssociationExperience(CanonicalRepository repository) {
        journey = new ChangeAssociationJourneyService(new DeterministicIntelligenceService(repository));
    }

    public ChangeAssociationExperienceProjection investigate(String serviceId, String riskFindingId) {
        return investigate(serviceId, riskFindingId, "local-change-associated");
    }

    public ChangeAssociationExperienceProjection investigate(String serviceId, String riskFindingId, String period) {
        if ("local-change-predates".equals(period)) {
            return new ChangeAssociationExperienceProjection(serviceId, riskFindingId, "change-payments-local",
                "deployment-payments-local", "degradation-before-change", false, false,
                List.of("evidence-change-association"),
                "Synthetic inverse control: degradation predates the change; temporal proximity does not establish association or causation");
        }
        var service = new Service(metadata(serviceId), "Payments", "area-platform", "degraded");
        var evidence = new Evidence(metadata("evidence-change-association"), "correlation-observation",
            "Degradation evidence is temporally adjacent to the local synthetic deployment", "Local demo evidence; association only");
        var correlation = new CorrelationContext("temporal proximity", "before/during/after",
            List.of("evidence-change-association"), "Correlation is not causation");
        var change = new Change(metadata("change-payments-local"), "Local synthetic Payments deployment", "deployment-payments-local");
        var input = new IntelligenceInput(service,
            List.of(new MonitoringEvent(metadata("event-change-local"), "latency", "degraded", serviceId)),
            List.of(new Incident(metadata("incident-change-1"), "degradation", serviceId, null),
                    new Incident(metadata("incident-change-2"), "recurrence", serviceId, null)),
            List.of(), List.of(), List.of(), List.of(evidence), change, correlation,
            new IntelligencePolicy("EXT-003-J02-local", 2, 1), AT);
        var result = journey.investigate(input);
        return new ChangeAssociationExperienceProjection(serviceId, riskFindingId, "change-payments-local",
            "deployment-payments-local", "before/during/after", result.contextualAssociation(), result.causalClaim(),
            correlation.evidenceIds(), "Synthetic local scenario; temporal/contextual association only");
    }

    private static CanonicalMetadata metadata(String id) {
        return new CanonicalMetadata(id, IdentityResolutionState.CONFIRMED,
            new TemporalSemantics(AT, AT, AT, null, null), Provenance.nativeOrUnspecified(),
            new SourceAuthority("local-synthetic-j02", true));
    }
}
