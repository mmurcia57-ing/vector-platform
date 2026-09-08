package com.vector.bff.canonical;

import java.util.Map;

public record Incident(CanonicalMetadata metadata, String summary, String serviceId, String problemId) implements CanonicalEntity {
	public Incident {
		if (summary == null || summary.isBlank()) throw new IllegalArgumentException("summary is required");
	}
	public String canonicalType() { return "Incident"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("summary", summary, "serviceId", serviceId, "problemId", problemId); }
}
