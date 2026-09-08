package com.vector.bff.canonical;

import java.util.Map;

public record Problem(CanonicalMetadata metadata, String condition, String incidentId, String serviceId) implements CanonicalEntity {
	public Problem {
		if (condition == null || condition.isBlank()) throw new IllegalArgumentException("condition is required");
	}
	public String canonicalType() { return "Problem"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("condition", condition, "incidentId", incidentId, "serviceId", serviceId); }
}
