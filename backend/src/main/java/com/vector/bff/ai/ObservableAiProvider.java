package com.vector.bff.ai;

import com.vector.bff.observability.TelemetryEvent;
import com.vector.bff.observability.TelemetryRecorder;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/** Adds safe output validation and consumption telemetry to an AI provider. */
public final class ObservableAiProvider implements AiProvider {
    private final AiProvider delegate;
    private final AiOutputValidator validator;
    private final TelemetryRecorder telemetry;
    private final Clock clock;

    public ObservableAiProvider(AiProvider delegate, AiOutputValidator validator,
            TelemetryRecorder telemetry, Clock clock) {
        this.delegate = Objects.requireNonNull(delegate, "delegate is required");
        this.validator = Objects.requireNonNull(validator, "validator is required");
        this.telemetry = Objects.requireNonNull(telemetry, "telemetry is required");
        this.clock = Objects.requireNonNull(clock, "clock is required");
    }

    @Override
    public AiProviderResult generate(AiRequest request) {
        var started = Instant.now(clock);
        AiProviderResult result;
        try {
            result = validator.validate(delegate.generate(request));
        } catch (RuntimeException failure) {
            result = AiProviderResult.unavailable("AI provider request failed");
        }
        var elapsed = Duration.between(started, Instant.now(clock)).toMillis();
        telemetry.record(new TelemetryEvent("ai-provider", Instant.now(clock), "AI_REQUEST",
            "capability=" + request.capability() + ",status=" + result.status() + ",latencyMs=" + elapsed));
        return result;
    }
}
