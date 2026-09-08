package com.vector.bff.canonical;

import java.util.List;

public final class CanonicalEntityCatalog {
	private CanonicalEntityCatalog() {
	}

	public static List<String> v1Types() {
		return List.of("AreaDomain", "Service", "ConfigurationItem", "MonitoringEvent", "Incident", "Problem",
			"Change", "Deployment", "SLO", "SLOObservation", "Commitment", "ImprovementAction",
			"OutcomeVerification", "Evidence", "RiskFinding", "MetricObservation", "SourceReference");
	}
}
