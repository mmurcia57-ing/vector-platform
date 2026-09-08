package com.vector.bff.canonical;

import java.util.Objects;

public record CanonicalMetadata(
		String canonicalId,
		IdentityResolutionState identityState,
		TemporalSemantics temporal,
		Provenance provenance,
		SourceAuthority sourceAuthority) {
	public CanonicalMetadata {
		canonicalId = requireId(canonicalId);
		Objects.requireNonNull(identityState, "identityState is required");
		temporal = Objects.requireNonNullElse(temporal, TemporalSemantics.empty());
		provenance = Objects.requireNonNullElse(provenance, Provenance.nativeOrUnspecified());
		sourceAuthority = Objects.requireNonNullElse(sourceAuthority, SourceAuthority.unknown());
	}

	private static String requireId(String value) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("canonicalId is required");
		}
		return value;
	}
}
