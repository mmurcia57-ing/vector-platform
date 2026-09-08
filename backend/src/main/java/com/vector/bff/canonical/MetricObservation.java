package com.vector.bff.canonical;

import java.util.Map;

public record MetricObservation(CanonicalMetadata metadata, String metric, String valueContext, String serviceId, String sloId) implements CanonicalEntity {
	public MetricObservation {
		if (metric == null || metric.isBlank()) throw new IllegalArgumentException("metric is required");
		if (valueContext == null || valueContext.isBlank()) throw new IllegalArgumentException("valueContext is required");
	}
	public String canonicalType() { return "MetricObservation"; }
	public Map<String, String> semanticAttributes() { return CanonicalEntitySupport.attributes("metric", metric, "valueContext", valueContext, "serviceId", serviceId, "sloId", sloId); }
}
