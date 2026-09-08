package com.vector.bff.graph;

import java.util.LinkedHashSet;
import java.util.Set;

/** Replaceable local read-model adapter; it is never canonical storage or Source Authority. */
public final class InMemoryGraphProjectionStore implements GraphProjectionStore {
    private final Set<GraphNode> nodes = new LinkedHashSet<>();
    private final Set<GraphRelationship> relationships = new LinkedHashSet<>();
    private final Set<String> appliedEvents = new java.util.HashSet<>();

    @Override
    public synchronized void apply(GraphProjectionEvent event) {
        if (!appliedEvents.add(event.eventId())) return;
        nodes.addAll(event.nodes());
        relationships.addAll(event.relationships());
    }

    @Override public synchronized Set<GraphNode> nodes() { return Set.copyOf(nodes); }
    @Override public synchronized Set<GraphRelationship> relationships() { return Set.copyOf(relationships); }
}
