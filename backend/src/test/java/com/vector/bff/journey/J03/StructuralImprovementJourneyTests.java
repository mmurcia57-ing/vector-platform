package com.vector.bff.journey.J03;

import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Commitment;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.ImprovementAction;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.TemporalSemantics;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StructuralImprovementJourneyTests {
    @Test
    void preservesCommitmentActionOutcomeChainAndDoesNotInferImprovement() {
        var commitment = new Commitment(metadata("commitment-1"), "address recurring risk", "risk-1", "OPEN");
        var action = new ImprovementAction(metadata("action-1"), "tune service configuration", "commitment-1", "COMPLETED");
        var result = new StructuralImprovementJourneyService().verify(commitment, action,
            List.of(evidence("before"), evidence("after")));
        assertThat(result.commitment()).isEqualTo(commitment);
        assertThat(result.action()).isEqualTo(action);
        assertThat(result.outcomeVerification().outcome()).isEqualTo("PERSISTENT");
        assertThat(result.outcomeVerification().outcome()).isNotEqualTo("IMPROVED");
    }

    @Test
    void completedActionWithInsufficientEvidenceRemainsNotYetVerifiable() {
        var result = new StructuralImprovementJourneyService().verify(
            new Commitment(metadata("commitment-2"), "address risk", "risk-2", "COMPLETED"),
            new ImprovementAction(metadata("action-2"), "execute action", "commitment-2", "COMPLETED"),
            List.of(evidence("after")));
        assertThat(result.outcomeVerification().outcome()).isEqualTo("not-yet-verifiable");
    }

    private static Evidence evidence(String id) { return new Evidence(metadata("evidence-" + id), "operational", id + " observation", "fixture"); }
    private static CanonicalMetadata metadata(String id) { return new CanonicalMetadata(id, IdentityResolutionState.CONFIRMED,
        TemporalSemantics.empty(), Provenance.nativeOrUnspecified(), SourceAuthority.unknown()); }
}
