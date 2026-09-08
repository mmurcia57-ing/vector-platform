package com.vector.bff.persistence;

import java.time.Instant;

public record CanonicalRecord(
		String canonicalType,
		String canonicalId,
		String payload,
		String identityState,
		Instant occurredAt,
		Instant observedAt,
		Instant ingestedAt,
		Instant effectiveFrom,
		Instant effectiveTo,
		String sourceReferenceIds,
		String authorityReference,
		boolean authorityExplicit) {
}
