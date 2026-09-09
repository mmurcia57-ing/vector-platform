package com.vector.bff.ai;

import java.util.List;

public record AiProviderResult(Status status, String explanation, List<String> limitations, String provenance) {
    public enum Status { AVAILABLE, UNAVAILABLE, INVALID }

    public AiProviderResult {
        if (status == null || limitations == null || provenance == null || provenance.isBlank()) {
            throw new IllegalArgumentException("AI result provenance is required");
        }
        limitations = List.copyOf(limitations);
    }

    public static AiProviderResult unavailable(String reason) {
        return new AiProviderResult(Status.UNAVAILABLE, null, List.of(reason), "governed-provider-boundary");
    }
}
