package com.vector.bff.experience;

import java.util.List;

public record TechnologyOverviewProjection(AnalysisContext context, List<AreaDomainProjection> areas,
		List<ServiceProjection> services, List<RiskFindingProjection> attentionFindings, ProjectionQuality quality) {
	public TechnologyOverviewProjection {
		areas = List.copyOf(areas);
		services = List.copyOf(services);
		attentionFindings = List.copyOf(attentionFindings);
	}
}
