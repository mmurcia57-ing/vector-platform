package com.vector.bff.canonical;

import java.util.Map;

public record SLOObservation(CanonicalMetadata metadata, String observation, String sloId, String valueContext) implements CanonicalEntity {
	public SLOObservation {
		if (observation == null || observation.isBlank()) throw new IllegalArgumentException("observation is required");
	}
	public String canonicalType() { return "SLOObservation"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("observation", observation, "sloId", sloId, "valueContext", valueContext); }
}
