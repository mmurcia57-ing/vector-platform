package com.vector.bff.observability;
import java.time.Instant;
public record TelemetryEvent(String component, Instant occurredAt, String eventType, String detail) { }
