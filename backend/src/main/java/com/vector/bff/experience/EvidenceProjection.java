package com.vector.bff.experience;

import java.time.Instant;
import java.util.List;

public record EvidenceProjection(String evidenceId, String serviceId, String riskFindingId, String supportedClaim,
		List<String> sourceReferenceIds, String limitations, Instant observedAt) {
	public EvidenceProjection {
		sourceReferenceIds = List.copyOf(sourceReferenceIds == null ? List.of() : sourceReferenceIds);
	}
}
