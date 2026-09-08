package com.vector.bff.intelligence;

import java.util.List;

/** Explainable, evidence-backed temporal/contextual association; it is never a causal claim or identity mapping. */
public record CorrelationContext(String method, String temporalContext, List<String> evidenceIds, String limitations) {
	public CorrelationContext {
		if (method == null || method.isBlank() || temporalContext == null || temporalContext.isBlank()) {
			throw new IllegalArgumentException("method and temporalContext are required");
		}
		evidenceIds = List.copyOf(evidenceIds == null ? List.of() : evidenceIds);
		if (evidenceIds.isEmpty() || evidenceIds.stream().anyMatch(id -> id == null || id.isBlank())) {
			throw new IllegalArgumentException("evidenceIds are required");
		}
	}
}
