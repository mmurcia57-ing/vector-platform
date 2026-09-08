package com.vector.bff.journey.J03;

import com.vector.bff.canonical.Commitment;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.ImprovementAction;
import com.vector.bff.canonical.OutcomeVerification;

import java.util.List;
import java.util.Objects;

/** Keeps execution and structural outcome distinct; no ownership or performance semantics are added. */
public final class StructuralImprovementJourneyService {
    public StructuralImprovementResult verify(Commitment commitment, ImprovementAction action,
            List<Evidence> beforeAfterEvidence) {
        Objects.requireNonNull(commitment, "commitment is required");
        Objects.requireNonNull(action, "action is required");
        var evidence = List.copyOf(beforeAfterEvidence == null ? List.of() : beforeAfterEvidence);
        var sufficient = evidence.size() >= 2;
        var outcome = sufficient ? "PERSISTENT" : "not-yet-verifiable";
        var basis = evidence.stream().map(item -> item.metadata().canonicalId()).sorted().toList();
        var verification = new OutcomeVerification(metadata(commitment.metadata().canonicalId() + ":outcome", basis), outcome,
            String.join(",", basis), sufficient ? "before/after operational Evidence available" : "post-action operational Evidence is insufficient");
        return new StructuralImprovementResult(commitment, action, verification, evidence);
    }

    private static com.vector.bff.canonical.CanonicalMetadata metadata(String id, List<String> evidence) {
        return new com.vector.bff.canonical.CanonicalMetadata(id, com.vector.bff.canonical.IdentityResolutionState.CONFIRMED,
            com.vector.bff.canonical.TemporalSemantics.empty(), new com.vector.bff.canonical.Provenance(evidence, "J03", "deterministic verification"),
            com.vector.bff.canonical.SourceAuthority.unknown());
    }
}
