package com.vector.bff.ai;

import static org.assertj.core.api.Assertions.assertThat;

import com.vector.bff.observability.InMemoryTelemetryRecorder;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;

class ObservableAiProviderTests {
    private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);
    private static final AiRequest REQUEST = new AiRequest("explain-risk", "service-1/risk-1",
        List.of("evidence-1"), "policy-v1", true);

    @Test
    void providerFailureIsExplicitAndObservable() {
        var telemetry = new InMemoryTelemetryRecorder();
        var provider = new ObservableAiProvider(ignored -> { throw new IllegalStateException("outage"); },
            new AiOutputValidator(), telemetry, CLOCK);

        var result = provider.generate(REQUEST);

        assertThat(result.status()).isEqualTo(AiProviderResult.Status.UNAVAILABLE);
        assertThat(result.explanation()).isNull();
        assertThat(telemetry.events()).singleElement().satisfies(event -> {
            assertThat(event.detail()).contains("capability=explain-risk", "status=UNAVAILABLE", "latencyMs=0");
        });
    }

    @Test
    void malformedAvailableOutputFailsSafe() {
        var telemetry = new InMemoryTelemetryRecorder();
        var provider = new ObservableAiProvider(ignored -> new AiProviderResult(
            AiProviderResult.Status.AVAILABLE, " ", List.of(), "provider-test"),
            new AiOutputValidator(), telemetry, CLOCK);

        var result = provider.generate(REQUEST);

        assertThat(result.status()).isEqualTo(AiProviderResult.Status.INVALID);
        assertThat(result.limitations()).contains("AI provider output omitted its explanation");
        assertThat(telemetry.events()).singleElement().satisfies(event ->
            assertThat(event.detail()).contains("status=INVALID"));
    }
}
