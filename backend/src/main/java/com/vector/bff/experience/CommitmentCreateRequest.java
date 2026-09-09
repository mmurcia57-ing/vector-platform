package com.vector.bff.experience;

import java.time.LocalDate;

public record CommitmentCreateRequest(String commitmentId, String declaration, String accountableAreaDomainId,
        String responsibleParty, LocalDate dueDate, String executionStatus, String intendedResult,
        String serviceId, String configurationItemId, String riskFindingId) {
}
