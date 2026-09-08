package com.vector.bff.canonical;

import java.util.Map;

public record ImprovementAction(CanonicalMetadata metadata, String action, String commitmentId, String executionStatusContext) implements CanonicalEntity {
	public ImprovementAction {
		if (action == null || action.isBlank()) throw new IllegalArgumentException("action is required");
	}
	public String canonicalType() { return "ImprovementAction"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("action", action, "commitmentId", commitmentId, "executionStatusContext", executionStatusContext); }
}
