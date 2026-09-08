package com.vector.bff.graph;

import java.time.Instant;
import java.util.List;

public record GraphProjectionEvent(String eventId, List<GraphNode> nodes,
        List<GraphRelationship> relationships, Instant occurredAt) {
    public GraphProjectionEvent {
        if (eventId == null || eventId.isBlank()) throw new IllegalArgumentException("eventId is required");
        nodes = List.copyOf(nodes == null ? List.of() : nodes);
        relationships = List.copyOf(relationships == null ? List.of() : relationships);
        if (occurredAt == null) throw new IllegalArgumentException("occurredAt is required");
    }
}
