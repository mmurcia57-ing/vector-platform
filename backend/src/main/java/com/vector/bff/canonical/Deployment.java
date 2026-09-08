package com.vector.bff.canonical;

import java.util.Map;

public record Deployment(CanonicalMetadata metadata, String description, String changeId, String serviceId) implements CanonicalEntity {
	public Deployment {
		if (description == null || description.isBlank()) throw new IllegalArgumentException("description is required");
	}
	public String canonicalType() { return "Deployment"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("description", description, "changeId", changeId, "serviceId", serviceId); }
}
