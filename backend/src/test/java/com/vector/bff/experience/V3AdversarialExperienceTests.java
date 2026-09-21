package com.vector.bff.experience;

import com.vector.bff.configuration.VectorReliabilityPolicyProperties;
import com.vector.bff.evidence.SqliteEvidencePath;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class V3AdversarialExperienceTests {
    private LocalExperienceProjectionSource source(SqliteCanonicalRepository repository, SqliteEvidencePath evidencePath) {
        return new LocalExperienceProjectionSource(repository, evidencePath,
            new VectorReliabilityPolicyProperties("local-dataset-v1", 2, 2));
    }

    @Test
    void healthyNegativeControlDoesNotManufactureAttention() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var evidence = new SqliteEvidencePath("jdbc:sqlite::memory:")) {
            var result = source(repository, evidence).load(new ProjectionRequest(
                new AnalysisContext("local-healthy-control", null, null, null, null), 20));
            assertThat(result.riskFindings()).isEmpty();
            assertThat(result.areas()).allSatisfy(area -> assertThat(area.attentionState()).isEqualTo("STABLE"));
        }
    }

    @Test
    void conflictingEvidenceRemainsVisibleWithoutSelectingAuthority() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var evidence = new SqliteEvidencePath("jdbc:sqlite::memory:")) {
            var result = source(repository, evidence).load(new ProjectionRequest(
                new AnalysisContext("local-conflicting-evidence", null, null, null, null), 20));
            assertThat(result.quality().conflicting()).isTrue();
            assertThat(result.evidence()).extracting(EvidenceProjection::evidenceId).contains("evidence-conflict");
            assertThat(result.outcomeVerifications()).isEmpty();
        }
    }

    @Test
    void verifiedImprovementRequiresDistinctPostActionEvidence() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var evidence = new SqliteEvidencePath("jdbc:sqlite::memory:")) {
            var result = source(repository, evidence).load(new ProjectionRequest(
                new AnalysisContext("local-outcome-improved", null, null, null, null), 20));
            assertThat(result.evidence()).extracting(EvidenceProjection::evidenceId).contains("evidence-outcome-improved");
            assertThat(result.outcomeVerifications()).singleElement().satisfies(outcome -> {
                assertThat(outcome.outcome()).isEqualTo("IMPROVED");
                assertThat(outcome.evidenceIds()).contains("evidence-outcome-improved");
            });
        }
    }

    @Test
    void completedActionCanRemainOutcomePendingOrUnverifiable() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var evidence = new SqliteEvidencePath("jdbc:sqlite::memory:")) {
            var local = source(repository, evidence);
            var pending = local.load(new ProjectionRequest(new AnalysisContext("local-outcome-pending", null, null, null, null), 20));
            assertThat(pending.improvementActions()).isNotEmpty();
            assertThat(pending.outcomeVerifications()).isEmpty();
            var insufficient = local.load(new ProjectionRequest(new AnalysisContext("local-insufficient-evidence", null, null, null, null), 20));
            assertThat(insufficient.outcomeVerifications()).isEmpty();
            assertThat(insufficient.quality().missingContext()).isNotEmpty();
        }
    }

    @Test
    void partialStaleEvidenceNeverBecomesHealthyByAbsence() {
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var evidence = new SqliteEvidencePath("jdbc:sqlite::memory:")) {
            var result = source(repository, evidence).load(new ProjectionRequest(
                new AnalysisContext("local-partial-stale", null, null, null, null), 20));
            assertThat(result.quality().stale()).isTrue();
            assertThat(result.quality().missingContext()).isNotEmpty();
            assertThat(result.riskFindings()).isNotEmpty();
        }
    }
}
