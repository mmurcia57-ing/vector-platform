package com.vector.bff.journey.J02;

import com.vector.bff.intelligence.DeterministicIntelligenceService;
import com.vector.bff.intelligence.IntelligenceInput;

import java.util.Objects;

/** Exposes only Evidence-backed temporal/contextual association; it never asserts causation. */
public final class ChangeAssociationJourneyService {
    private final DeterministicIntelligenceService intelligence;

    public ChangeAssociationJourneyService(DeterministicIntelligenceService intelligence) {
        this.intelligence = Objects.requireNonNull(intelligence, "intelligence is required");
    }

    public ChangeAssociationJourneyResult investigate(IntelligenceInput input) {
        var result = intelligence.evaluate(input);
        var context = input.correlationContext();
        var association = context != null && input.change() != null && !context.evidenceIds().isEmpty();
        return new ChangeAssociationJourneyResult(result, association, false);
    }
}
