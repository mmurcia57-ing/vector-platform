package com.vector.bff.experience;

import java.time.Instant;
import java.util.List;

/** Evidence-backed temporal signal. Semantic type is descriptive, never causal. */
public record TemporalSignalProjection(String signalId, String serviceId, String riskFindingId, String semanticType,
        String statement, Instant observedAt, List<String> sourceReferenceIds, String limitation) {
    public TemporalSignalProjection {
        sourceReferenceIds = List.copyOf(sourceReferenceIds == null ? List.of() : sourceReferenceIds);
    }
}
