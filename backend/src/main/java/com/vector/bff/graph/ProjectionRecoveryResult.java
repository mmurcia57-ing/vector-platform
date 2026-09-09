package com.vector.bff.graph;

public record ProjectionRecoveryResult(int projected, int pending, boolean degraded, String detail) {
    public ProjectionRecoveryResult {
        if (projected < 0 || pending < 0 || detail == null || detail.isBlank()) {
            throw new IllegalArgumentException("recovery result is required");
        }
    }
}
