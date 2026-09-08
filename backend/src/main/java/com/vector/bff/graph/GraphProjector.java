package com.vector.bff.graph;

import java.util.Objects;

/** Asynchronously consumable projector; an event is acknowledged only after store success. */
public final class GraphProjector {
    private final GraphOutbox outbox;
    private final GraphProjectionStore store;

    public GraphProjector(GraphOutbox outbox, GraphProjectionStore store) {
        this.outbox = Objects.requireNonNull(outbox, "outbox is required");
        this.store = Objects.requireNonNull(store, "store is required");
    }

    public int projectPending() {
        var projected = 0;
        for (var event : outbox.pending()) {
            store.apply(event);
            outbox.markProjected(event.eventId());
            projected++;
        }
        return projected;
    }
}
