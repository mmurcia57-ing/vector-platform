package com.vector.bff.canonical;

import java.util.Map;

public record MonitoringEvent(CanonicalMetadata metadata, String category, String condition, String serviceId) implements CanonicalEntity {
	public MonitoringEvent {
		if (category == null || category.isBlank()) throw new IllegalArgumentException("category is required");
	}
	public String canonicalType() { return "MonitoringEvent"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("category", category, "condition", condition, "serviceId", serviceId); }
}
