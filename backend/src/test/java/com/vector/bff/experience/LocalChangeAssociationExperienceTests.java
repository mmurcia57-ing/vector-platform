package com.vector.bff.experience;

import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LocalChangeAssociationExperienceTests {
    @Test
    void exposesBeforeDuringAfterAssociationWithoutPromotingItToCausation() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var result = new LocalChangeAssociationExperience(repository).investigate("service-payments","risk-local");
            assertThat(result.temporalContext()).isEqualTo("before/during/after");
            assertThat(result.contextualAssociation()).isTrue();
            assertThat(result.causalClaim()).isFalse();
            assertThat(result.limitation()).contains("Synthetic local").contains("association only");
        }
    }
    @Test
    void degradationPredatingChangeIsAnInverseControlAndNeverCausal() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var result = new LocalChangeAssociationExperience(repository).investigate("service-payments","risk-local","local-change-predates");
            assertThat(result.temporalContext()).isEqualTo("degradation-before-change");
            assertThat(result.contextualAssociation()).isFalse();
            assertThat(result.causalClaim()).isFalse();
            assertThat(result.limitation()).contains("predates");
        }
    }
}
