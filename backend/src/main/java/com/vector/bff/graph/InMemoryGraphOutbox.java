package com.vector.bff.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/** Local deterministic outbox adapter; event identity makes publication idempotent. */
public final class InMemoryGraphOutbox implements GraphOutbox {
    private final LinkedHashMap<String, GraphProjectionEvent> events = new LinkedHashMap<>();
    private final java.util.Set<String> projected = new java.util.HashSet<>();

    @Override
    public synchronized void append(GraphProjectionEvent event) {
        events.putIfAbsent(event.eventId(), event);
    }

    @Override
    public synchronized List<GraphProjectionEvent> pending() {
        return events.values().stream().filter(event -> !projected.contains(event.eventId())).toList();
    }

    @Override
    public synchronized void markProjected(String eventId) {
        if (events.containsKey(eventId)) projected.add(eventId);
    }
}
