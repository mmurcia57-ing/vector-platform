package com.vector.bff.experience;

import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CommitmentManagementUseCaseTests {
    @Test
    void createsNativeCommitmentWithAccountabilityAndDeterministicOverdueProjection() {
        ExperienceProjectionSource source = request -> new PreparedExperienceContext(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("test", "fixed", "native", List.of(), List.of(), List.of(), false, false));
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var useCase = new DefaultCommitmentManagementUseCase(source, repository);
            var projection = useCase.create(new CommitmentCreateRequest("commitment-ext", "address risk", "area-1", null,
                LocalDate.of(2025, 1, 10), "OPEN", "restore service", "service-1", null, "risk-1"));
            assertThat(projection.accountableAreaDomainId()).isEqualTo("area-1");
            assertThat(useCase.list("area-1", "service-1", LocalDate.of(2025, 1, 11), 20).overdueCount()).isEqualTo(1);
        }
    }

    @Test
    void administrativeCommitmentNeedsOnlyAccountabilityAndPreservesNativeProvenance() {
        ExperienceProjectionSource source = request -> new PreparedExperienceContext(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("test", "fixed", "native", List.of(), List.of(), List.of(), false, false));
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var useCase = new DefaultCommitmentManagementUseCase(source, repository);
            var projection = useCase.create(new CommitmentCreateRequest("admin-1", "renew policy", "area-1", "reference-owner",
                LocalDate.of(2025, 1, 10), "COMPLETED", "policy renewed", null, null, null));
            assertThat(projection.serviceId()).isNull();
            assertThat(projection.riskFindingId()).isNull();
            assertThat(projection.sourceReferenceSummary()).isEmpty();
            assertThat(useCase.list("area-1", null, LocalDate.of(2025, 1, 11), 20).completedCount()).isEqualTo(1);
        }
    }

    @Test
    void completedExecutionDoesNotBecomeOutcomeImprovement() {
        ExperienceProjectionSource source = request -> new PreparedExperienceContext(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("test", "fixed", "native", List.of(), List.of(), List.of(), false, false));
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
            var useCase = new DefaultCommitmentManagementUseCase(source, repository);
            var projection = useCase.create(new CommitmentCreateRequest("completed-1", "address risk", "area-1", null,
                LocalDate.of(2025, 1, 10), "COMPLETED", "improvement expected", "service-1", "ci-1", "risk-1"));
            assertThat(projection.executionStatus()).isEqualTo("COMPLETED");
            assertThat(projection.overdue()).isFalse();
        }
    }
}
