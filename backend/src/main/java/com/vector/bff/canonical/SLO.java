package com.vector.bff.canonical;

import java.util.Map;

public record SLO(CanonicalMetadata metadata, String objective, String serviceId) implements CanonicalEntity {
	public SLO {
		if (objective == null || objective.isBlank()) throw new IllegalArgumentException("objective is required");
	}
	public String canonicalType() { return "SLO"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("objective", objective, "serviceId", serviceId); }
}
