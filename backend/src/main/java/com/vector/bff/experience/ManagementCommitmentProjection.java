package com.vector.bff.experience;

import java.time.LocalDate;

public record ManagementCommitmentProjection(String commitmentId, String declaration, String accountableAreaDomainId,
        String responsibleParty, LocalDate dueDate, String executionStatus, boolean overdue, String riskFindingId,
        String serviceId, String configurationItemId, String sourceReferenceSummary) {
}
