package com.vector.bff.graph;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;

/** Bounded connected-subgraph query; traversal never changes normative predicate direction. */
public final class BoundedGraphQueryService {
    private final GraphProjectionStore store;
    private final String freshness;

    public BoundedGraphQueryService(GraphProjectionStore store, String freshness) {
        this.store = java.util.Objects.requireNonNull(store, "store is required");
        this.freshness = freshness == null || freshness.isBlank() ? "unknown" : freshness;
    }

    public BoundedGraphProjection query(BoundedGraphQuery query) {
        var selectedNodes = new LinkedHashSet<GraphNode>();
        var selectedRelationships = new LinkedHashSet<GraphRelationship>();
        var pending = new ArrayDeque<GraphNode>();
        selectedNodes.add(query.seed()); pending.add(query.seed());
        while (!pending.isEmpty() && selectedNodes.size() < query.maxNodes() && selectedRelationships.size() < query.maxRelationships()) {
            var current = pending.removeFirst();
            for (var relationship : store.relationships().stream().sorted(java.util.Comparator
                    .comparing((GraphRelationship item) -> item.source().canonicalType())
                    .thenComparing(item -> item.source().canonicalId())
                    .thenComparing(GraphRelationship::predicate)
                    .thenComparing(item -> item.target().canonicalType())
                    .thenComparing(item -> item.target().canonicalId())).toList()) {
                if (!relationship.source().equals(current) && !relationship.target().equals(current)) continue;
                if (selectedRelationships.size() >= query.maxRelationships()) break;
                var other = relationship.source().equals(current) ? relationship.target() : relationship.source();
                if (selectedNodes.size() >= query.maxNodes() && !selectedNodes.contains(other)) continue;
                selectedRelationships.add(relationship);
                if (selectedNodes.add(other)) pending.addLast(other);
            }
        }
        var truncated = selectedNodes.size() >= query.maxNodes() || selectedRelationships.size() >= query.maxRelationships();
        return new BoundedGraphProjection(query.seed(), new java.util.ArrayList<>(selectedNodes),
            new java.util.ArrayList<>(selectedRelationships), truncated, freshness);
    }
}
