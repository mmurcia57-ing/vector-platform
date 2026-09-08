package com.vector.bff.canonical;

import java.util.Map;

public record Service(CanonicalMetadata metadata, String name, String areaDomainId, String conditionContext) implements CanonicalEntity {
	public Service {
		if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
	}
	public String canonicalType() { return "Service"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("name", name, "areaDomainId", areaDomainId, "conditionContext", conditionContext); }
}
