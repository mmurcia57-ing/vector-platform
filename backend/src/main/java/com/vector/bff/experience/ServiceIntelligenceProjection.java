package com.vector.bff.experience;

import java.util.List;

public record ServiceIntelligenceProjection(AnalysisContext context, ServiceProjection service,
		List<RiskFindingProjection> riskFindings, List<EvidenceProjection> evidence,
		List<CommitmentProjection> commitments, List<ImprovementActionProjection> improvementActions,
		List<OutcomeVerificationProjection> outcomeVerifications, ProjectionQuality quality) {
	public ServiceIntelligenceProjection {
		riskFindings = List.copyOf(riskFindings);
		evidence = List.copyOf(evidence);
		commitments = List.copyOf(commitments);
		improvementActions = List.copyOf(improvementActions);
		outcomeVerifications = List.copyOf(outcomeVerifications);
	}
}
