package com.vector.bff.canonical;

import java.time.Instant;

public record TemporalSemantics(
		Instant occurredAt,
		Instant observedAt,
		Instant ingestedAt,
		Instant effectiveFrom,
		Instant effectiveTo) {
	public TemporalSemantics {
		if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
			throw new IllegalArgumentException("effectiveTo cannot precede effectiveFrom");
		}
	}

	public static TemporalSemantics empty() {
		return new TemporalSemantics(null, null, null, null, null);
	}
}
