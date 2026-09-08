package com.vector.bff.graph;

import java.util.List;

/** A typed, directional GRC projection; graph traversal never changes predicate semantics. */
public record GraphRelationship(GraphNode source, String predicate, GraphNode target,
        List<String> evidenceIds, List<String> sourceReferenceIds) {
    public GraphRelationship {
        if (source == null || target == null) throw new IllegalArgumentException("relationship endpoints are required");
        if (predicate == null || predicate.isBlank()) throw new IllegalArgumentException("predicate is required");
        evidenceIds = List.copyOf(evidenceIds == null ? List.of() : evidenceIds);
        sourceReferenceIds = List.copyOf(sourceReferenceIds == null ? List.of() : sourceReferenceIds);
    }
}
