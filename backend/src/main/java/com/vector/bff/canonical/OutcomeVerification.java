package com.vector.bff.canonical;

import java.util.Map;

public record OutcomeVerification(CanonicalMetadata metadata, String outcome, String evidenceBasis, String beforeAfterContext) implements CanonicalEntity {
	public OutcomeVerification {
		if (outcome == null || outcome.isBlank()) throw new IllegalArgumentException("outcome is required");
		if (evidenceBasis == null || evidenceBasis.isBlank()) throw new IllegalArgumentException("evidenceBasis is required");
	}
	public String canonicalType() { return "OutcomeVerification"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("outcome", outcome, "evidenceBasis", evidenceBasis, "beforeAfterContext", beforeAfterContext); }
}
