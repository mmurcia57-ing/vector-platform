package com.vector.bff.canonical;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

/** Stable text representation for persistence and comparison; not a public API schema. */
public final class CanonicalSerializer {
	private CanonicalSerializer() {
	}

	public static String serialize(CanonicalEntity entity) {
		var metadata = entity.metadata();
		var values = new TreeMap<String, String>();
		values.put("canonicalId", metadata.canonicalId());
		values.put("identityState", metadata.identityState().name());
		put(values, "occurredAt", metadata.temporal().occurredAt());
		put(values, "observedAt", metadata.temporal().observedAt());
		put(values, "ingestedAt", metadata.temporal().ingestedAt());
		put(values, "effectiveFrom", metadata.temporal().effectiveFrom());
		put(values, "effectiveTo", metadata.temporal().effectiveTo());
		put(values, "authorityReference", metadata.sourceAuthority().authorityReference());
		values.put("authorityExplicit", Boolean.toString(metadata.sourceAuthority().explicitlyDeclared()));
		values.put("sourceReferenceIds", String.join(",", metadata.provenance().sourceReferenceIds()));
		put(values, "mappingMethod", metadata.provenance().mappingMethod());
		put(values, "limitations", metadata.provenance().limitations());
		entity.semanticAttributes().forEach((key, value) -> put(values, "attribute." + key, value));
		return values.entrySet().stream()
			.map(entry -> escape(entry.getKey()) + "=" + escape(entry.getValue()))
			.collect(java.util.stream.Collectors.joining("&", entity.canonicalType() + "{", "}"));
	}

	private static void put(Map<String, String> values, String key, Object value) {
		if (value != null) values.put(key, value.toString());
	}

	private static String escape(String value) {
		return value.replace("%", "%25").replace("&", "%26").replace("=", "%3D");
	}
}
