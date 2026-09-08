package com.vector.bff.journey.J02;

import com.vector.bff.intelligence.IntelligenceResult;

public record ChangeAssociationJourneyResult(IntelligenceResult intelligence,
        boolean contextualAssociation, boolean causalClaim) {
    public ChangeAssociationJourneyResult {
        if (intelligence == null) throw new IllegalArgumentException("intelligence is required");
        if (causalClaim) throw new IllegalArgumentException("causal claims are not permitted");
    }
}
