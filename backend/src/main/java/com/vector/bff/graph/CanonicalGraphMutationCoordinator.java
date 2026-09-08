package com.vector.bff.graph;

import com.vector.bff.canonical.CanonicalEntity;
import com.vector.bff.persistence.CanonicalRepository;

import java.util.Map;
import java.util.Objects;

/** Coordinates a canonical write with its graph publication event; the graph remains rebuildable. */
public final class CanonicalGraphMutationCoordinator {
    private final CanonicalRepository canonicalRepository;
    private final GraphOutbox outbox;
    private final GraphRelationshipMapper mapper;
    private final Map<String, CanonicalEntity> knownEntities;

    public CanonicalGraphMutationCoordinator(CanonicalRepository canonicalRepository, GraphOutbox outbox,
            GraphRelationshipMapper mapper, Map<String, CanonicalEntity> knownEntities) {
        this.canonicalRepository = Objects.requireNonNull(canonicalRepository, "canonicalRepository is required");
        this.outbox = Objects.requireNonNull(outbox, "outbox is required");
        this.mapper = Objects.requireNonNull(mapper, "mapper is required");
        this.knownEntities = Objects.requireNonNull(knownEntities, "knownEntities is required");
    }

    public void save(CanonicalEntity entity) {
        canonicalRepository.save(entity);
        outbox.append(mapper.map(entity, knownEntities));
    }
}
