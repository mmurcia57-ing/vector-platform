package com.vector.bff.seed;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/** Explicit, versioned input for a reproducible synthetic dataset. */
public record DatasetRequest(
	DatasetProfile profile,
	long seed,
	String datasetVersion,
	String generatorVersion,
	String vectorVersion,
	Map<String, String> effectiveConfiguration) {

	public DatasetRequest {
		Objects.requireNonNull(profile, "profile is required");
		datasetVersion = requireNonBlank(datasetVersion, "datasetVersion");
		generatorVersion = requireNonBlank(generatorVersion, "generatorVersion");
		vectorVersion = requireNonBlank(vectorVersion, "vectorVersion");
		effectiveConfiguration = Collections.unmodifiableMap(new TreeMap<>(Objects.requireNonNull(
			effectiveConfiguration, "effectiveConfiguration is required")));
	}

	private static String requireNonBlank(String value, String name) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(name + " is required");
		}
		return value;
	}
}
