package com.vector.bff.graph;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class SqliteGraphProjectionStoreTests {
    @Test
    void projectionSurvivesProcessLikeStoreRestartAndRemainsIdempotent() throws Exception {
        var file = Files.createTempFile("vector-graph-", ".db");
        var url = "jdbc:sqlite:" + file;
        var service = new GraphNode("Service","service-1");
        var evidence = new GraphNode("Evidence","evidence-1");
        var relation = new GraphRelationship(evidence,"SUPPORTS",service,List.of("evidence-1"),List.of("source-1"));
        var event = new GraphProjectionEvent("evt-1",List.of(service,evidence),List.of(relation),Instant.parse("2025-01-01T00:00:00Z"));
        try (var first = new SqliteGraphProjectionStore(url)) { first.apply(event); first.apply(event); assertThat(first.relationships()).hasSize(1); }
        try (var restarted = new SqliteGraphProjectionStore(url)) {
            assertThat(restarted.nodes()).contains(service,evidence);
            assertThat(restarted.relationships()).contains(relation);
            restarted.apply(event);
            assertThat(restarted.relationships()).hasSize(1);
        } finally { Files.deleteIfExists(file); }
    }
}
