package com.vector.bff.experience;

import java.util.List;

public record TechnologyOverviewProjection(AnalysisContext context, List<AreaDomainProjection> areas,
		List<ServiceProjection> services, List<RiskFindingProjection> attentionFindings,
        List<CommitmentProjection> commitments, List<ImprovementActionProjection> improvementActions,
        List<OutcomeVerificationProjection> outcomeVerifications, ProjectionQuality quality) {
	public TechnologyOverviewProjection {
		areas = List.copyOf(areas);
		services = List.copyOf(services);
		attentionFindings = List.copyOf(attentionFindings);
        commitments = List.copyOf(commitments);
        improvementActions = List.copyOf(improvementActions);
        outcomeVerifications = List.copyOf(outcomeVerifications);
	}
}
