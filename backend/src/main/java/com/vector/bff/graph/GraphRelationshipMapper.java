package com.vector.bff.graph;

import com.vector.bff.canonical.AreaDomain;
import com.vector.bff.canonical.CanonicalEntity;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.Incident;
import com.vector.bff.canonical.MonitoringEvent;
import com.vector.bff.canonical.RiskFinding;
import com.vector.bff.canonical.Service;
import com.vector.bff.canonical.SourceReference;

import java.util.List;
import java.util.Map;

/** Maps only approved closed GRC predicates; it does not infer unsupported relationships. */
public final class GraphRelationshipMapper {
    public GraphProjectionEvent map(CanonicalEntity entity, Map<String, CanonicalEntity> knownEntities) {
        var node = node(entity);
        var nodes = new java.util.LinkedHashSet<GraphNode>();
        nodes.add(node);
        var relationships = new java.util.LinkedHashSet<GraphRelationship>();
        if (entity instanceof Service service) {
            related(knownEntities, "AreaDomain", service.areaDomainId()).ifPresent(area -> {
                nodes.add(node(area)); relationships.add(relation(node(area), "CONTEXTUALIZES_SERVICE", node, List.of(), refs(entity)));
            });
        } else if (entity instanceof MonitoringEvent event) {
            related(knownEntities, "Service", event.serviceId()).ifPresent(service -> {
                nodes.add(node(service)); relationships.add(relation(node, "OBSERVES_CONDITION_OF", node(service), List.of(), refs(entity)));
            });
        } else if (entity instanceof Incident incident) {
            related(knownEntities, "Service", incident.serviceId()).ifPresent(service -> {
                nodes.add(node(service)); relationships.add(relation(node, "AFFECTS_SERVICE", node(service), List.of(), refs(entity)));
            });
            if (incident.problemId() != null) related(knownEntities, "Problem", incident.problemId()).ifPresent(problem -> {
                nodes.add(node(problem)); relationships.add(relation(node, "PROVIDES_CONTEXT_FOR", node(problem), List.of(), refs(entity)));
            });
        } else if (entity instanceof Evidence evidence) {
            evidence.metadata().provenance().sourceReferenceIds().forEach(id -> related(knownEntities, "SourceReference", id).ifPresent(reference -> {
                nodes.add(node(reference)); relationships.add(relation(node(reference), "PRESERVES_PROVENANCE_FOR", node, List.of(evidence.metadata().canonicalId()), refs(reference)));
            }));
        } else if (entity instanceof RiskFinding finding) {
            related(knownEntities, "Service", finding.serviceId()).ifPresent(service -> {
                nodes.add(node(service)); relationships.add(relation(node, "CONCERNS", node(service), basis(finding.evidenceBasis()), refs(entity)));
            });
            for (var evidenceId : basis(finding.evidenceBasis())) related(knownEntities, "Evidence", evidenceId).ifPresent(evidence -> {
                nodes.add(node(evidence)); relationships.add(relation(node(evidence), "SUPPORTS", node, List.of(evidenceId), refs(evidence)));
            });
        }
        return new GraphProjectionEvent("canonical:" + entity.canonicalType() + ":" + entity.metadata().canonicalId(), List.copyOf(nodes), List.copyOf(relationships),
            entity.metadata().temporal().ingestedAt() == null ? java.time.Instant.EPOCH : entity.metadata().temporal().ingestedAt());
    }

    private static GraphRelationship relation(GraphNode source, String predicate, GraphNode target, List<String> evidence, List<String> refs) { return new GraphRelationship(source, predicate, target, evidence, refs); }
    private static GraphNode node(CanonicalEntity entity) { return new GraphNode(entity.canonicalType(), entity.metadata().canonicalId()); }
    private static List<String> refs(CanonicalEntity entity) { return entity.metadata().provenance().sourceReferenceIds(); }
    private static List<String> basis(String value) { return value == null || value.isBlank() ? List.of() : List.of(value.split(",", -1)); }
    private static java.util.Optional<CanonicalEntity> related(Map<String, CanonicalEntity> known, String type, String id) { return id == null ? java.util.Optional.empty() : java.util.Optional.ofNullable(known.get(type + ":" + id)); }
}
