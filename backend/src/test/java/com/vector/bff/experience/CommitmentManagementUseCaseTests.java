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
    @Test
    void preservesRenegotiationHistoryAndDistinguishesPreDueDateChange() {
        ExperienceProjectionSource source = request -> new PreparedExperienceContext(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("test","fixed","native",List.of(),List.of(),List.of(),false,false));
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var lifecycle = new SqliteCommitmentLifecycleStore("jdbc:sqlite::memory:")) {
            var useCase = new DefaultCommitmentManagementUseCase(source, repository, lifecycle);
            useCase.create(new CommitmentCreateRequest("reneg-1","stabilize service","area-1",null,LocalDate.of(2025,1,10),"IN_PROGRESS","risk removed","service-1",null,"risk-1"));
            useCase.renegotiate("reneg-1", new CommitmentRenegotiationRequest(LocalDate.of(2025,1,20),"dependency changed",java.time.Instant.parse("2025-01-05T10:00:00Z")));
            var history = useCase.history("reneg-1");
            assertThat(history).anySatisfy(event -> {
                assertThat(event.eventType()).isEqualTo("RENEGOTIATED");
                assertThat(event.priorDueDate()).isEqualTo(LocalDate.of(2025,1,10));
                assertThat(event.newDueDate()).isEqualTo(LocalDate.of(2025,1,20));
                assertThat(event.beforeDueDate()).isTrue();
                assertThat(event.reason()).isEqualTo("dependency changed");
            });
            assertThat(useCase.list("area-1","service-1",LocalDate.of(2025,1,6),20).renegotiatedCount()).isEqualTo(1);
        }
    }

    @Test
    void rejectsRenegotiationWithoutReason() {
        ExperienceProjectionSource source = request -> new PreparedExperienceContext(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("test","fixed","native",List.of(),List.of(),List.of(),false,false));
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var lifecycle = new SqliteCommitmentLifecycleStore("jdbc:sqlite::memory:")) {
            var useCase = new DefaultCommitmentManagementUseCase(source, repository, lifecycle);
            useCase.create(new CommitmentCreateRequest("reneg-2","stabilize","area-1",null,LocalDate.of(2025,1,10),"OPEN","stable","service-1",null,"risk-1"));
            assertThatIllegalArgumentException().isThrownBy(() -> useCase.renegotiate("reneg-2",
                new CommitmentRenegotiationRequest(LocalDate.of(2025,1,20),"",java.time.Instant.parse("2025-01-05T10:00:00Z"))));
        }
    }

    @Test
    void reliabilityUsesExplicitDueDenominatorAndCompletionEventDate() {
        ExperienceProjectionSource source = request -> new PreparedExperienceContext(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("test","fixed","native",List.of(),List.of(),List.of(),false,false));
        try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:");
             var lifecycle = new SqliteCommitmentLifecycleStore("jdbc:sqlite::memory:")) {
            var useCase = new DefaultCommitmentManagementUseCase(source, repository, lifecycle);
            useCase.create(new CommitmentCreateRequest("rel-1","deliver fix","area-1",null,LocalDate.of(2025,1,10),"IN_PROGRESS","stable","service-1",null,"risk-1"));
            useCase.updateLifecycle("rel-1",new CommitmentLifecycleUpdateRequest("COMPLETED","delivered",java.time.Instant.parse("2025-01-09T10:00:00Z")));
            var result=useCase.list("area-1","service-1",LocalDate.of(2025,1,11),20);
            assertThat(result.reliabilityDenominator()).isEqualTo(1);
            assertThat(result.reliabilityNumerator()).isEqualTo(1);
            assertThat(result.commitmentReliabilityRate()).isEqualTo(1.0);
            assertThat(result.outcomePendingCount()).isEqualTo(1);
        }
    }

}
