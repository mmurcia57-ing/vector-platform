package com.vector.bff.graph;

import java.util.Set;

public interface GraphProjectionStore {
    void apply(GraphProjectionEvent event);
    Set<GraphNode> nodes();
    Set<GraphRelationship> relationships();
}
