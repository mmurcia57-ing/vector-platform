package com.vector.bff.canonical;

import java.util.Map;

public record RiskFinding(CanonicalMetadata metadata, String condition, String serviceId, String explanation, String evidenceBasis) implements CanonicalEntity {
	public RiskFinding {
		if (condition == null || condition.isBlank()) throw new IllegalArgumentException("condition is required");
		if (explanation == null || explanation.isBlank()) throw new IllegalArgumentException("explanation is required");
		if (evidenceBasis == null || evidenceBasis.isBlank()) throw new IllegalArgumentException("evidenceBasis is required");
	}
	public String canonicalType() { return "RiskFinding"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("condition", condition, "serviceId", serviceId, "explanation", explanation, "evidenceBasis", evidenceBasis); }
}
