package com.vector.bff.journey.J01;

import com.vector.bff.intelligence.DeterministicIntelligenceService;
import com.vector.bff.intelligence.IntelligenceInput;

import java.util.Objects;

/** Orchestrates J01 without replacing deterministic intelligence or asserting root cause. */
public final class PersistentReliabilityJourneyService {
    private final DeterministicIntelligenceService intelligence;

    public PersistentReliabilityJourneyService(DeterministicIntelligenceService intelligence) {
        this.intelligence = Objects.requireNonNull(intelligence, "intelligence is required");
    }

    public PersistentReliabilityJourneyResult investigate(IntelligenceInput input) {
        var result = intelligence.evaluate(input);
        var evidence = input.supportingEvidence();
        var sufficient = result.riskFinding().isPresent();
        return new PersistentReliabilityJourneyResult(result.riskFinding(), evidence, result.limitations(), sufficient);
    }
}
