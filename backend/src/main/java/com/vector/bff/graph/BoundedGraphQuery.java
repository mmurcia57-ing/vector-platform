package com.vector.bff.graph;

import java.util.List;

public record BoundedGraphQuery(GraphNode seed, int maxNodes, int maxRelationships) {
    public BoundedGraphQuery {
        if (seed == null) throw new IllegalArgumentException("seed is required");
        if (maxNodes < 1 || maxRelationships < 1) throw new IllegalArgumentException("bounds must be positive");
    }
}
