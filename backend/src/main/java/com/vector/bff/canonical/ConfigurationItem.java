package com.vector.bff.canonical;

import java.util.Map;

public record ConfigurationItem(CanonicalMetadata metadata, String role, String serviceId) implements CanonicalEntity {
	public ConfigurationItem {
		if (role == null || role.isBlank()) throw new IllegalArgumentException("role is required");
	}
	public String canonicalType() { return "ConfigurationItem"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("role", role, "serviceId", serviceId); }
}
