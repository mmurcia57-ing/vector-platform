package com.vector.bff.canonical;

import java.util.Map;

public record Evidence(CanonicalMetadata metadata, String evidenceType, String supportedClaim, String limitations) implements CanonicalEntity {
	public Evidence {
		if (evidenceType == null || evidenceType.isBlank()) throw new IllegalArgumentException("evidenceType is required");
		if (supportedClaim == null || supportedClaim.isBlank()) throw new IllegalArgumentException("supportedClaim is required");
	}
	public String canonicalType() { return "Evidence"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("evidenceType", evidenceType, "supportedClaim", supportedClaim, "limitations", limitations); }
}
