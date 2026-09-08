package com.vector.bff.canonical;

import java.util.Map;

public record Change(CanonicalMetadata metadata, String description, String deploymentId) implements CanonicalEntity {
	public Change {
		if (description == null || description.isBlank()) throw new IllegalArgumentException("description is required");
	}
	public String canonicalType() { return "Change"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("description", description, "deploymentId", deploymentId); }
}
