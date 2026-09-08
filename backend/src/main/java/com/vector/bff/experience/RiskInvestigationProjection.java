package com.vector.bff.experience;

import java.util.List;

public record RiskInvestigationProjection(AnalysisContext context, RiskFindingProjection riskFinding,
		List<EvidenceProjection> evidence, List<CommitmentProjection> commitments,
		List<ImprovementActionProjection> improvementActions,
		List<OutcomeVerificationProjection> outcomeVerifications, ProjectionQuality quality) {
	public RiskInvestigationProjection {
		evidence = List.copyOf(evidence);
		commitments = List.copyOf(commitments);
		improvementActions = List.copyOf(improvementActions);
		outcomeVerifications = List.copyOf(outcomeVerifications);
	}
}
