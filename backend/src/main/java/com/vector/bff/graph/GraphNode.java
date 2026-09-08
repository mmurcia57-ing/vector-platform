package com.vector.bff.graph;

import java.util.Objects;

public record GraphNode(String canonicalType, String canonicalId) {
    public GraphNode {
        if (canonicalType == null || canonicalType.isBlank()) throw new IllegalArgumentException("canonicalType is required");
        if (canonicalId == null || canonicalId.isBlank()) throw new IllegalArgumentException("canonicalId is required");
    }
}
