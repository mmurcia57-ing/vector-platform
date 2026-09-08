package com.vector.bff.canonical;

import java.util.List;

public record Provenance(List<String> sourceReferenceIds, String mappingMethod, String limitations) {
	public Provenance {
		sourceReferenceIds = List.copyOf(sourceReferenceIds == null ? List.of() : sourceReferenceIds);
		if (mappingMethod != null && mappingMethod.isBlank()) {
			throw new IllegalArgumentException("mappingMethod cannot be blank");
		}
	}

	public static Provenance nativeOrUnspecified() {
		return new Provenance(List.of(), null, null);
	}
}
