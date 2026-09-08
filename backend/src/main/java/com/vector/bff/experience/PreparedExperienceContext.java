package com.vector.bff.experience;

import java.util.List;

public record PreparedExperienceContext(
		List<AreaDomainProjection> areas,
		List<ServiceProjection> services,
		List<RiskFindingProjection> riskFindings,
		List<EvidenceProjection> evidence,
		List<CommitmentProjection> commitments,
		List<ImprovementActionProjection> improvementActions,
		List<OutcomeVerificationProjection> outcomeVerifications,
		ProjectionQuality quality) {
	public PreparedExperienceContext {
		areas = List.copyOf(areas == null ? List.of() : areas);
		services = List.copyOf(services == null ? List.of() : services);
		riskFindings = List.copyOf(riskFindings == null ? List.of() : riskFindings);
		evidence = List.copyOf(evidence == null ? List.of() : evidence);
		commitments = List.copyOf(commitments == null ? List.of() : commitments);
		improvementActions = List.copyOf(improvementActions == null ? List.of() : improvementActions);
		outcomeVerifications = List.copyOf(outcomeVerifications == null ? List.of() : outcomeVerifications);
		quality = quality == null ? ProjectionQuality.complete() : quality;
	}
}
