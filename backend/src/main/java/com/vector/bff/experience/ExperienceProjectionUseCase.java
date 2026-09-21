package com.vector.bff.experience;

public interface ExperienceProjectionUseCase {
	TechnologyOverviewProjection technologyOverview(ProjectionRequest request);

	ServiceIntelligenceProjection serviceIntelligence(ProjectionRequest request);

	RiskInvestigationProjection riskInvestigation(ProjectionRequest request);

	java.util.List<TemporalSignalProjection> temporalSignals(ProjectionRequest request);
}
