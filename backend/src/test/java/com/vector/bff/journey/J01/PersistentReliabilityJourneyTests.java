package com.vector.bff.journey.J01;

import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.Incident;
import com.vector.bff.canonical.MonitoringEvent;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.Service;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.TemporalSemantics;
import com.vector.bff.intelligence.DeterministicIntelligenceService;
import com.vector.bff.intelligence.IntelligenceInput;
import com.vector.bff.intelligence.IntelligencePolicy;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersistentReliabilityJourneyTests {
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Test
    void exposesEvidenceBackedPersistentRiskWithoutRootCause() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var service = new PersistentReliabilityJourneyService(new DeterministicIntelligenceService(repository));
            var result = service.investigate(input(2, 2, 2, 1));
            assertThat(result.evidenceSufficient()).isTrue();
            assertThat(result.riskFinding()).isPresent();
            assertThat(result.riskFinding().orElseThrow().explanation()).doesNotContainIgnoringCase("root cause");
            assertThat(result.evidence()).hasSize(2);
        }
    }

    @Test
    void retainsMissingTelemetryAsAVisibleLimitation() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var result = new PersistentReliabilityJourneyService(new DeterministicIntelligenceService(repository))
                .investigate(input(2, 2, 0, 0));
            assertThat(result.riskFinding()).isEmpty();
            assertThat(result.evidenceSufficient()).isFalse();
            assertThat(result.limitations()).anyMatch(item -> item.contains("missing telemetry"));
        }
    }

    @Test
    void doesNotDeclarePersistenceWhenRecurrenceOrEvidenceIsInsufficient() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var result = new PersistentReliabilityJourneyService(new DeterministicIntelligenceService(repository))
                .investigate(input(1, 2, 1, 1));
            assertThat(result.riskFinding()).isEmpty();
            assertThat(result.limitations()).anyMatch(item -> item.contains("Recurrence threshold"));
        }
    }

    private static IntelligenceInput input(int incidents, int evidenceCount, int events, int policyEvidence) {
        var service = new Service(metadata("service-1"), "Payments", "area-1", "degraded");
        var eventList = java.util.stream.IntStream.range(0, events).mapToObj(i -> new MonitoringEvent(metadata("event-" + i), "latency", "degraded", "service-1")).toList();
        var incidentList = java.util.stream.IntStream.range(0, incidents).mapToObj(i -> new Incident(metadata("incident-" + i), "recurrence", "service-1", null)).toList();
        var evidence = java.util.stream.IntStream.range(0, evidenceCount).mapToObj(i -> new Evidence(metadata("evidence-" + i), "observation", "degradation evidence", "fixture limitation")).toList();
        return new IntelligenceInput(service, eventList, incidentList, List.of(), List.of(), List.of(), evidence, null, null,
            new IntelligencePolicy("test-policy", 2, Math.max(1, policyEvidence)), NOW);
    }

    private static CanonicalMetadata metadata(String id) {
        return new CanonicalMetadata(id, IdentityResolutionState.CONFIRMED,
            new TemporalSemantics(NOW, NOW, NOW, null, null), Provenance.nativeOrUnspecified(), new SourceAuthority("fixture", true));
    }
}
