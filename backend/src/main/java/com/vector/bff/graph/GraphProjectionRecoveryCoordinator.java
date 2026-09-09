package com.vector.bff.graph;

import java.util.Objects;

/**
 * Bounded local recovery around the rebuildable graph projection. Canonical
 * records remain authoritative; failed events stay pending for a later retry.
 */
public final class GraphProjectionRecoveryCoordinator {
    private final GraphOutbox outbox;
    private final GraphProjector projector;

    public GraphProjectionRecoveryCoordinator(GraphOutbox outbox, GraphProjectionStore store) {
        this.outbox = Objects.requireNonNull(outbox, "outbox is required");
        this.projector = new GraphProjector(outbox, Objects.requireNonNull(store, "store is required"));
    }

    public ProjectionRecoveryResult recoverOnce() {
        try {
            var projected = projector.projectPending();
            var pending = outbox.pending().size();
            return new ProjectionRecoveryResult(projected, pending, false,
                pending == 0 ? "projection converged" : "projection backlog remains");
        } catch (RuntimeException failure) {
            return new ProjectionRecoveryResult(0, outbox.pending().size(), true,
                "projection degraded; pending events were retained");
        }
    }
}
