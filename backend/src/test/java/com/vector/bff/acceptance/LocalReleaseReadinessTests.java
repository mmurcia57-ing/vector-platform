package com.vector.bff.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.vector.bff.canonical.CanonicalEntityCatalog;
import com.vector.bff.configuration.VectorReliabilityPolicyProperties;
import com.vector.bff.evidence.SqliteEvidencePath;
import com.vector.bff.experience.AnalysisContext;
import com.vector.bff.experience.DefaultExperienceProjectionUseCase;
import com.vector.bff.experience.LocalExperienceProjectionSource;
import com.vector.bff.experience.ProjectionRequest;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

class LocalReleaseReadinessTests {
    @Test
    void localProductPathReachesEvidenceActionAndPersistentOutcome() {
        var repository = new SqliteCanonicalRepository("jdbc:sqlite:file:release-readiness?mode=memory&cache=shared");
        var source = new LocalExperienceProjectionSource(repository, new SqliteEvidencePath(repository),
            new VectorReliabilityPolicyProperties("local-dataset-v1", 2, 2));
        var useCase = new DefaultExperienceProjectionUseCase(source);

        var overview = useCase.technologyOverview(new ProjectionRequest(AnalysisContext.overview(), 20));
        var risk = overview.attentionFindings().getFirst();
        var investigation = useCase.riskInvestigation(new ProjectionRequest(new AnalysisContext(
            "local-dataset-v1", "area-platform", "service-payments", risk.riskFindingId(), null), 20));

        assertThat(overview.areas()).singleElement().satisfies(area -> assertThat(area.attentionState()).isEqualTo("ATTENTION"));
        assertThat(investigation.evidence()).hasSize(2);
        assertThat(investigation.commitments()).singleElement().satisfies(commitment ->
            assertThat(commitment.statusContext()).isEqualTo("COMPLETED"));
        assertThat(investigation.improvementActions()).singleElement().satisfies(action ->
            assertThat(action.executionStatusContext()).isEqualTo("COMPLETED"));
        assertThat(investigation.outcomeVerifications()).singleElement().satisfies(outcome -> {
            assertThat(outcome.outcome()).isEqualTo("PERSISTENT");
            assertThat(outcome.evidenceIds()).containsExactly("evidence-incident", "evidence-latency");
        });
        assertThat(CanonicalEntityCatalog.v1Types()).hasSize(17);
    }
}
