package com.vector.bff.experience;

import java.util.Objects;

public record ProjectionRequest(AnalysisContext context, int limit) {
	public ProjectionRequest {
		Objects.requireNonNull(context, "context is required");
		if (limit < 1) throw new IllegalArgumentException("limit must be positive");
	}
}
