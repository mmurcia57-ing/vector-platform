package com.vector.bff.experience;

import java.util.ArrayList;
import java.util.List;

public record ProjectionQuality(
		String sourceCoverage,
		String freshness,
		String confidenceContext,
		List<String> missingContext,
		List<String> uncertainty,
		List<String> limitations,
		boolean stale,
		boolean conflicting) {
	public ProjectionQuality {
		missingContext = List.copyOf(missingContext == null ? List.of() : missingContext);
		uncertainty = List.copyOf(uncertainty == null ? List.of() : uncertainty);
		limitations = List.copyOf(limitations == null ? List.of() : limitations);
	}

	public static ProjectionQuality complete() {
		return new ProjectionQuality("available", "current", "contextual", List.of(), List.of(), List.of(), false, false);
	}

	public ProjectionQuality withMissingContext(String missing) {
		var updated = new ArrayList<>(missingContext);
		if (missing != null && !missing.isBlank() && !updated.contains(missing)) updated.add(missing);
		return new ProjectionQuality(sourceCoverage, freshness, confidenceContext, updated, uncertainty,
			limitations, stale, conflicting);
	}
}
