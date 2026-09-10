package com.vector.bff.graph;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class GraphProjectionRecoveryTests {
    @Test
    void failedProjectionRemainsDegradedAndPendingForRecovery() {
        var outbox = new InMemoryGraphOutbox();
        var event = new GraphProjectionEvent("event-1", List.of(), List.of(), Instant.parse("2026-01-01T00:00:00Z"));
        outbox.append(event);
        var recovery = new GraphProjectionRecoveryCoordinator(outbox, new GraphProjectionStore() {
            @Override public void apply(GraphProjectionEvent ignored) { throw new IllegalStateException("graph unavailable"); }
            @Override public java.util.Set<GraphNode> nodes() { return java.util.Set.of(); }
            @Override public java.util.Set<GraphRelationship> relationships() { return java.util.Set.of(); }
        });

        var result = recovery.recoverOnce();

        assertThat(result.degraded()).isTrue();
        assertThat(result.pending()).isEqualTo(1);
        assertThat(outbox.pending()).containsExactly(event);
    }

    @Test
    void retryConvergesWithoutDuplicatingEvents() {
        var outbox = new InMemoryGraphOutbox();
        var store = new InMemoryGraphProjectionStore();
        var event = new GraphProjectionEvent("event-1", List.of(), List.of(), Instant.parse("2026-01-01T00:00:00Z"));
        outbox.append(event);
        var recovery = new GraphProjectionRecoveryCoordinator(outbox, store);

        var first = recovery.recoverOnce();
        var second = recovery.recoverOnce();

        assertThat(first.degraded()).isFalse();
        assertThat(first.projected()).isEqualTo(1);
        assertThat(second.projected()).isZero();
        assertThat(second.pending()).isZero();
    }

    @Test
    void recoveryConvergesAfterCoordinatorRestart() {
        var outbox = new InMemoryGraphOutbox();
        var store = new InMemoryGraphProjectionStore();
        var event = new GraphProjectionEvent("event-1", List.of(), List.of(), Instant.parse("2026-01-01T00:00:00Z"));
        outbox.append(event);

        var firstCoordinator = new GraphProjectionRecoveryCoordinator(outbox, store);
        var first = firstCoordinator.recoverOnce();

        var restartedCoordinator = new GraphProjectionRecoveryCoordinator(outbox, store);
        var afterRestart = restartedCoordinator.recoverOnce();

        assertThat(first.degraded()).isFalse();
        assertThat(first.projected()).isEqualTo(1);
        assertThat(afterRestart.degraded()).isFalse();
        assertThat(afterRestart.projected()).isZero();
        assertThat(afterRestart.pending()).isZero();
    }
}
