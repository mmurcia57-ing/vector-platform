package com.vector.bff.canonical;

import java.util.Map;

public record AreaDomain(CanonicalMetadata metadata, String name, String domainContext) implements CanonicalEntity {
	public AreaDomain {
		if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
	}
	public String canonicalType() { return "AreaDomain"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("name", name, "domainContext", domainContext); }
}
