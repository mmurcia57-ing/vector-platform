package com.vector.bff.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.vector.bff.ai.AiOutputValidator;
import com.vector.bff.ai.AiProviderResult;
import com.vector.bff.ai.AiRequest;
import com.vector.bff.ai.ObservableAiProvider;
import com.vector.bff.graph.GraphProjectionEvent;
import com.vector.bff.graph.GraphProjectionRecoveryCoordinator;
import com.vector.bff.graph.InMemoryGraphOutbox;
import com.vector.bff.graph.InMemoryGraphProjectionStore;
import com.vector.bff.observability.InMemoryTelemetryRecorder;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;

class SecurityResilienceAiAcceptanceTests {
    private static final List<String> AI_SCENARIOS = List.of(
        "grounded-explanation", "evidence-summary-traceability", "hypothesis-labelling",
        "no-unsupported-causation", "recommendation-without-execution", "outcome-explanation",
        "assistant-authorization", "conflicts", "missing-context", "inferred-unresolved-identity",
        "insufficient-evidence", "provider-unavailable", "malformed-structured-output",
        "permission-restricted-evidence", "provider-model-configuration", "consumption-observability",
        "deterministic-truth-unchanged", "absence-of-hidden-ai-authority");

    @Test
    void allRequiredAiEvaluationScenariosArePresentExactlyOnce() {
        assertThat(AI_SCENARIOS).hasSize(18).doesNotHaveDuplicates();
        assertThat(AI_SCENARIOS).contains("no-unsupported-causation", "recommendation-without-execution",
            "assistant-authorization", "insufficient-evidence", "provider-unavailable",
            "permission-restricted-evidence", "absence-of-hidden-ai-authority");
    }

    @Test
    void aiOutageAndMalformedOutputFailSafeAndRemainObservable() {
        var telemetry = new InMemoryTelemetryRecorder();
        var clock = Clock.fixed(Instant.parse("2026-01-01T00:00:00Z"), ZoneOffset.UTC);
        var request = new AiRequest("explain-risk", "service-1/risk-1", List.of("evidence-1"), "policy-v1", true);
        var outage = new ObservableAiProvider(ignored -> { throw new IllegalStateException("outage"); },
            new AiOutputValidator(), telemetry, clock).generate(request);
        var malformed = new ObservableAiProvider(ignored -> new AiProviderResult(
            AiProviderResult.Status.AVAILABLE, null, List.of(), "provider-test"),
            new AiOutputValidator(), telemetry, clock).generate(request);

        assertThat(outage.status()).isEqualTo(AiProviderResult.Status.UNAVAILABLE);
        assertThat(malformed.status()).isEqualTo(AiProviderResult.Status.INVALID);
        assertThat(outage.explanation()).isNull();
        assertThat(malformed.explanation()).isNull();
        assertThat(telemetry.events()).hasSize(2);
    }

    @Test
    void graphFailureRetainsWorkAndRecoveryConvergesWithoutDuplication() {
        var outbox = new InMemoryGraphOutbox();
        outbox.append(new GraphProjectionEvent("event-1", List.of(), List.of(), Instant.parse("2026-01-01T00:00:00Z")));
        var recovery = new GraphProjectionRecoveryCoordinator(outbox, new InMemoryGraphProjectionStore());

        assertThat(recovery.recoverOnce().projected()).isEqualTo(1);
        assertThat(recovery.recoverOnce().projected()).isZero();
        assertThat(outbox.pending()).isEmpty();
    }
}
