package com.vector.bff.graph;

import java.util.List;

public record BoundedGraphProjection(GraphNode seed, List<GraphNode> nodes,
        List<GraphRelationship> relationships, boolean truncated, String freshness) {
    public BoundedGraphProjection {
        nodes = List.copyOf(nodes);
        relationships = List.copyOf(relationships);
    }
}
