package com.vector.bff.journey.J01;

import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.RiskFinding;

import java.util.List;
import java.util.Optional;

public record PersistentReliabilityJourneyResult(Optional<RiskFinding> riskFinding,
        List<Evidence> evidence, List<String> limitations, boolean evidenceSufficient) {
    public PersistentReliabilityJourneyResult {
        riskFinding = riskFinding == null ? Optional.empty() : riskFinding;
        evidence = List.copyOf(evidence == null ? List.of() : evidence);
        limitations = List.copyOf(limitations == null ? List.of() : limitations);
    }
}
