package com.vector.bff.graph;

import com.vector.bff.canonical.AreaDomain;
import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.RiskFinding;
import com.vector.bff.canonical.Service;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.SourceReference;
import com.vector.bff.canonical.TemporalSemantics;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GraphProjectionTests {
    private static final Instant NOW = Instant.parse("2025-01-01T00:00:00Z");

    @Test
    void mapsApprovedPredicateDirectionsAndTraceability() {
        var source = new SourceReference(metadata("source-1", List.of()), "fixture", "native-1", "claim", "fixture authority");
        var evidence = new Evidence(metadata("evidence-1", List.of("source-1")), "observation", "degraded", "fixture limitation");
        var area = new AreaDomain(metadata("area-1", List.of()), "Platform", "technology");
        var service = new Service(metadata("service-1", List.of()), "Payments", "area-1", "degraded");
        var finding = new RiskFinding(metadata("risk-1", List.of("evidence-1")), "condition", "service-1", "explanation", "evidence-1");
        Map<String, com.vector.bff.canonical.CanonicalEntity> known = new java.util.HashMap<>();
        known.put("AreaDomain:area-1", area);
        known.put("Service:service-1", service);
        known.put("SourceReference:source-1", source);
        known.put("Evidence:evidence-1", evidence);
        var mapper = new GraphRelationshipMapper();

        var areaRelation = mapper.map(service, known).relationships().getFirst();
        var findingRelations = mapper.map(finding, known).relationships();
        var evidenceRelations = mapper.map(evidence, known).relationships();

        assertThat(areaRelation).isEqualTo(new GraphRelationship(new GraphNode("AreaDomain", "area-1"), "CONTEXTUALIZES_SERVICE",
            new GraphNode("Service", "service-1"), List.of(), List.of()));
        assertThat(findingRelations).extracting(GraphRelationship::predicate).containsExactlyInAnyOrder("CONCERNS", "SUPPORTS");
        assertThat(findingRelations).anySatisfy(relation -> assertThat(relation.source()).isEqualTo(new GraphNode("Evidence", "evidence-1")));
        assertThat(evidenceRelations.getFirst().predicate()).isEqualTo("PRESERVES_PROVENANCE_FOR");
    }

    @Test
    void outboxProjectionIsIdempotentAndAcknowledgesOnlyAfterApply() {
        var event = new GraphProjectionEvent("event-1", List.of(new GraphNode("Service", "service-1")), List.of(), NOW);
        var outbox = new InMemoryGraphOutbox();
        var store = new InMemoryGraphProjectionStore();
        outbox.append(event); outbox.append(event);
        var projector = new GraphProjector(outbox, store);

        assertThat(projector.projectPending()).isEqualTo(1);
        assertThat(projector.projectPending()).isZero();
        assertThat(store.nodes()).containsExactly(new GraphNode("Service", "service-1"));
        assertThat(outbox.pending()).isEmpty();
    }

    @Test
    void queryReturnsOnlyAConnectedBoundedSubgraph() {
        var store = new InMemoryGraphProjectionStore();
        var service = new GraphNode("Service", "service-1");
        var finding = new GraphNode("RiskFinding", "risk-1");
        var evidence = new GraphNode("Evidence", "evidence-1");
        store.apply(new GraphProjectionEvent("event-1", List.of(service, finding),
            List.of(new GraphRelationship(finding, "CONCERNS", service, List.of(), List.of())), NOW));
        store.apply(new GraphProjectionEvent("event-2", List.of(finding, evidence),
            List.of(new GraphRelationship(evidence, "SUPPORTS", finding, List.of("evidence-1"), List.of())), NOW));

        var projection = new BoundedGraphQueryService(store, "current")
            .query(new BoundedGraphQuery(finding, 2, 1));

        assertThat(projection.nodes()).contains(finding);
        assertThat(projection.nodes()).anySatisfy(node -> assertThat(node).isIn(service, evidence));
        assertThat(projection.relationships()).hasSize(1);
        assertThat(projection.relationships().getFirst().predicate()).isIn("CONCERNS", "SUPPORTS");
        assertThat(projection.truncated()).isTrue();
    }

    private static CanonicalMetadata metadata(String id, List<String> references) {
        return new CanonicalMetadata(id, IdentityResolutionState.CONFIRMED,
            new TemporalSemantics(NOW, NOW, NOW, null, null), new Provenance(references, "fixture", "test"),
            new SourceAuthority("fixture", true));
    }
}
