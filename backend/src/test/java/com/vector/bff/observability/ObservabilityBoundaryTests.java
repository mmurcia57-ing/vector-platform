package com.vector.bff.observability;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.assertj.core.api.Assertions.assertThat;
class ObservabilityBoundaryTests {
    @Test void representsDegradationWithoutCallingItEvidence() {
        var health = new ComponentHealth("graph-projector", ComponentHealth.Status.DEGRADED, "projection unavailable");
        var recorder = new InMemoryTelemetryRecorder();
        recorder.record(new TelemetryEvent(health.component(), Instant.parse("2026-01-01T00:00:00Z"), "DEPENDENCY_DEGRADED", health.detail()));
        assertThat(health.status()).isEqualTo(ComponentHealth.Status.DEGRADED);
        assertThat(recorder.events()).singleElement().satisfies(event -> assertThat(event.eventType()).isEqualTo("DEPENDENCY_DEGRADED"));
    }
}
