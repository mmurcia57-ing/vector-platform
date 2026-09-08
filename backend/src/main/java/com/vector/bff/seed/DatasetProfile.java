package com.vector.bff.seed;

import java.util.Map;

/** Local synthetic profiles from the approved acceptance strategy. */
public enum DatasetProfile {
	GOLDEN(Map.of()),
	SMALL(counts(3, 25, 100, 1_000, 100, 20, 50, 50, 25, 1_000, 1_000, 500, 25, 15, 15, 10)),
	BASELINE(counts(20, 250, 2_000, 50_000, 5_000, 800, 2_500, 2_500, 250, 100_000, 100_000, 25_000, 1_500, 800, 800, 600)),
	STRESS(counts(80, 1_000, 10_000, 250_000, 25_000, 4_000, 12_500, 12_500, 1_000, 500_000, 500_000, 125_000, 7_500, 4_000, 4_000, 3_000));

	private final Map<DatasetDimension, Integer> counts;

	DatasetProfile(Map<DatasetDimension, Integer> counts) {
		this.counts = Map.copyOf(counts);
	}

	public Map<DatasetDimension, Integer> counts() {
		return counts;
	}

	private static Map<DatasetDimension, Integer> counts(
			int areaDomains, int services, int configurationItems, int monitoringEvents, int incidents,
			int problems, int changes, int deployments, int slos, int sloObservations,
			int metricObservations, int evidence, int riskFindings, int commitments,
			int improvementActions, int outcomeVerifications) {
		return Map.ofEntries(
			Map.entry(DatasetDimension.AREA_DOMAINS, areaDomains),
			Map.entry(DatasetDimension.SERVICES, services),
			Map.entry(DatasetDimension.CONFIGURATION_ITEMS, configurationItems),
			Map.entry(DatasetDimension.MONITORING_EVENTS, monitoringEvents),
			Map.entry(DatasetDimension.INCIDENTS, incidents),
			Map.entry(DatasetDimension.PROBLEMS, problems),
			Map.entry(DatasetDimension.CHANGES, changes),
			Map.entry(DatasetDimension.DEPLOYMENTS, deployments),
			Map.entry(DatasetDimension.SLOS, slos),
			Map.entry(DatasetDimension.SLO_OBSERVATIONS, sloObservations),
			Map.entry(DatasetDimension.METRIC_OBSERVATIONS, metricObservations),
			Map.entry(DatasetDimension.EVIDENCE, evidence),
			Map.entry(DatasetDimension.RISK_FINDINGS, riskFindings),
			Map.entry(DatasetDimension.COMMITMENTS, commitments),
			Map.entry(DatasetDimension.IMPROVEMENT_ACTIONS, improvementActions),
			Map.entry(DatasetDimension.OUTCOME_VERIFICATIONS, outcomeVerifications));
	}
}
