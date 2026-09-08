package com.vector.bff.canonical;

import java.util.Map;

public interface CanonicalEntity {
	CanonicalMetadata metadata();

	String canonicalType();

	Map<String, String> semanticAttributes();
}
