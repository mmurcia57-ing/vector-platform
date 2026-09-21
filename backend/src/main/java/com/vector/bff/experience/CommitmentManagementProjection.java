package com.vector.bff.experience;

import java.time.LocalDate;
import java.util.List;

public record CommitmentManagementProjection(LocalDate asOf, String areaDomainId, String serviceId,
        List<ManagementCommitmentProjection> commitments, long activeCount, long inProgressCount,
        long completedCount, long overdueCount, long renegotiatedCount, long outcomePendingCount,
        long reliabilityNumerator, long reliabilityDenominator, Double commitmentReliabilityRate,
        ProjectionQuality quality) {
    public CommitmentManagementProjection { commitments = List.copyOf(commitments); }
}
