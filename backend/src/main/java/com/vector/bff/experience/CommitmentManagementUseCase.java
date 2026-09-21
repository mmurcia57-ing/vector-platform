package com.vector.bff.experience;

public interface CommitmentManagementUseCase {
    CommitmentManagementProjection list(String areaDomainId, String serviceId, java.time.LocalDate asOf, int limit);
    ManagementCommitmentProjection create(CommitmentCreateRequest request);
    ManagementCommitmentProjection updateLifecycle(String commitmentId, CommitmentLifecycleUpdateRequest request);
    ManagementCommitmentProjection renegotiate(String commitmentId, CommitmentRenegotiationRequest request);
    java.util.List<CommitmentLifecycleEvent> history(String commitmentId);
}
