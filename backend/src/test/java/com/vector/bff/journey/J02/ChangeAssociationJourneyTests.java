package com.vector.bff.journey.J02;

import com.vector.bff.canonical.Change;
import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.Incident;
import com.vector.bff.canonical.MonitoringEvent;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.Service;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.TemporalSemantics;
import com.vector.bff.intelligence.CorrelationContext;
import com.vector.bff.intelligence.DeterministicIntelligenceService;
import com.vector.bff.intelligence.IntelligenceInput;
import com.vector.bff.intelligence.IntelligencePolicy;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChangeAssociationJourneyTests {
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Test
    void exposesEvidenceBackedAssociationWithoutCausation() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var result = new ChangeAssociationJourneyService(new DeterministicIntelligenceService(repository)).investigate(input(true));
            assertThat(result.contextualAssociation()).isTrue();
            assertThat(result.causalClaim()).isFalse();
            assertThat(result.intelligence().correlationContext().temporalContext()).isEqualTo("before/during/after");
        }
    }

    @Test
    void missingCorrelationEvidenceDoesNotBecomeAnAssociation() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var result = new ChangeAssociationJourneyService(new DeterministicIntelligenceService(repository)).investigate(input(false));
            assertThat(result.contextualAssociation()).isFalse();
            assertThat(result.causalClaim()).isFalse();
        }
    }

    private static IntelligenceInput input(boolean withCorrelation) {
        var service = new Service(metadata("service-1"), "Payments", "area-1", "degraded");
        var evidence = new Evidence(metadata("evidence-1"), "correlation-observation", "degradation near change", "association only");
        var correlation = withCorrelation ? new CorrelationContext("temporal proximity", "before/during/after", List.of("evidence-1"), "correlation is not causation") : null;
        return new IntelligenceInput(service, List.of(new MonitoringEvent(metadata("event-1"), "latency", "degraded", "service-1")),
            List.of(new Incident(metadata("incident-1"), "degradation", "service-1", null), new Incident(metadata("incident-2"), "recurrence", "service-1", null)),
            List.of(), List.of(), List.of(), List.of(evidence), new Change(metadata("change-1"), "payment deployment", "deployment-1"), correlation,
            new IntelligencePolicy("j02-test", 2, 1), NOW);
    }

    private static CanonicalMetadata metadata(String id) {
        return new CanonicalMetadata(id, IdentityResolutionState.CONFIRMED, new TemporalSemantics(NOW, NOW, NOW, null, null),
            Provenance.nativeOrUnspecified(), new SourceAuthority("fixture", true));
    }
}
