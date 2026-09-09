package com.vector.bff.experience;

public interface CommitmentManagementUseCase {
    CommitmentManagementProjection list(String areaDomainId, String serviceId, java.time.LocalDate asOf, int limit);
    ManagementCommitmentProjection create(CommitmentCreateRequest request);
}
