package com.vector.bff.canonical;

import java.util.LinkedHashMap;
import java.util.Map;

final class CanonicalEntitySupport {
	private CanonicalEntitySupport() {
	}

	static Map<String, String> attributes(String... values) {
		if (values.length % 2 != 0) {
			throw new IllegalArgumentException("attributes require name/value pairs");
		}
		var result = new LinkedHashMap<String, String>();
		for (int index = 0; index < values.length; index += 2) {
			if (values[index] == null || values[index].isBlank() || values[index + 1] == null) {
				throw new IllegalArgumentException("attribute names and values are required");
			}
			result.put(values[index], values[index + 1]);
		}
		return Map.copyOf(result);
	}
}
