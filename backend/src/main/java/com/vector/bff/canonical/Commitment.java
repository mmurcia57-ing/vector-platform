package com.vector.bff.canonical;

import java.util.Map;

public record Commitment(CanonicalMetadata metadata, String declaration, String riskFindingId, String dueStatusContext) implements CanonicalEntity {
	public Commitment {
		if (declaration == null || declaration.isBlank()) throw new IllegalArgumentException("declaration is required");
	}
	public String canonicalType() { return "Commitment"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("declaration", declaration, "riskFindingId", riskFindingId, "dueStatusContext", dueStatusContext); }
}
