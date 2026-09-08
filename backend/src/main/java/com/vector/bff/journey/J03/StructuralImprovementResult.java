package com.vector.bff.journey.J03;

import com.vector.bff.canonical.Commitment;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.ImprovementAction;
import com.vector.bff.canonical.OutcomeVerification;

import java.util.List;

public record StructuralImprovementResult(Commitment commitment, ImprovementAction action,
        OutcomeVerification outcomeVerification, List<Evidence> evidence) {
    public StructuralImprovementResult {
        if (commitment == null || action == null || outcomeVerification == null) throw new IllegalArgumentException("J03 chain is required");
        evidence = List.copyOf(evidence == null ? List.of() : evidence);
    }
}
