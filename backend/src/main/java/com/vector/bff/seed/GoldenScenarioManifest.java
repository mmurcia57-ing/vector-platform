package com.vector.bff.seed;

import java.util.List;

/**
 * Approved Golden scenario fixture/oracle descriptors. They preserve expected
 * inputs and boundaries without implementing the later canonical or journey
 * evaluation logic.
 */
public record GoldenScenarioManifest(List<ScenarioDescriptor> scenarios) {
	public static GoldenScenarioManifest approved() {
		return new GoldenScenarioManifest(List.of(
			scenario("GS-01", "Healthy / Stable", List.of("Service", "SLOObservation", "MetricObservation", "Evidence", "SourceReference"),
				"stable synthetic observations with provenance", "no fabricated RiskFinding", "stable is not a health guarantee"),
			scenario("GS-02", "Persistent Reliability Risk", List.of("Service", "MonitoringEvent", "Incident", "Problem", "SLO", "SLOObservation", "MetricObservation", "Evidence", "RiskFinding", "SourceReference"),
				"repeated incidents and degradation", "Evidence-backed persistent finding and path", "no root-cause claim"),
			scenario("GS-03", "Change-Associated Degradation", List.of("Change", "Deployment", "Service", "ConfigurationItem", "MonitoringEvent", "SLOObservation", "Incident", "Evidence", "RiskFinding", "SourceReference"),
				"temporal/contextual degradation window", "association only when Evidence supports it", "Correlation != Causation"),
			scenario("GS-04", "Structural Improvement", List.of("RiskFinding", "Commitment", "ImprovementAction", "OutcomeVerification", "Evidence", "MetricObservation", "SLOObservation", "SourceReference"),
				"completed action with comparable before/after Evidence", "IMPROVED only with sufficient comparable Evidence", "execution != outcome"),
			scenario("GS-05", "Persistent After Action", List.of("RiskFinding", "Commitment", "ImprovementAction", "OutcomeVerification", "Evidence", "MetricObservation", "SLOObservation", "SourceReference"),
				"completed action with continued degradation", "PERSISTENT outcome with Evidence path", "completion is not improvement"),
			scenario("GS-06", "Action Not Yet Verifiable", List.of("RiskFinding", "Commitment", "ImprovementAction", "OutcomeVerification", "Evidence", "SourceReference"),
				"completed action with insufficient observations", "no verified improvement", "insufficient Evidence remains explicit"),
			scenario("GS-07", "Missing Telemetry", List.of("Service", "MonitoringEvent", "Evidence", "SourceReference"),
				"unavailable or stale telemetry", "missing/stale limitation is retained", "missing telemetry != healthy"),
			scenario("GS-08", "Conflicting Claims", List.of("Service", "Evidence", "SourceReference"),
				"competing synthetic claims with provenance", "both claims and conflict remain inspectable", "no silent authority selection"),
			scenario("GS-09", "Inferred Identity", List.of("Service", "Change", "Deployment", "Evidence", "SourceReference"),
				"mapping Evidence supports inference", "identity remains INFERRED", "Identity != Correlation"),
			scenario("GS-10", "Unresolved Identity", List.of("Service", "Evidence", "SourceReference"),
				"insufficient mapping Evidence", "source context remains UNRESOLVED", "no confirmed Service relationship"),
			scenario("GS-11", "Partial Integration / Stale Projection", List.of("Service", "Evidence", "SourceReference"),
				"controlled stale or partial projection fixture", "freshness, coverage, and degradation are explicit", "graph availability is not canonical truth"),
			scenario("GS-12", "Leadership Decision", List.of("AreaDomain", "Service", "RiskFinding", "Evidence", "Commitment", "ImprovementAction", "OutcomeVerification", "SourceReference"),
				"retained AreaDomain to outcome context", "approved drill-down context and limitations", "no individual ranking or causal claim")));
	}

	private static ScenarioDescriptor scenario(String id, String title, List<String> fixtureConcepts,
			String input, String expected, String limitation) {
		return new ScenarioDescriptor(id, title, fixtureConcepts,
			new OracleDescriptor(input, expected, "NOT_EVALUATED", OracleStatus.NOT_EVALUATED), limitation);
	}

	public record ScenarioDescriptor(String id, String title, List<String> fixtureConcepts,
		OracleDescriptor oracle, String limitation) {
	}

	public record OracleDescriptor(String input, String expected, String actual, OracleStatus status) {
	}

	public enum OracleStatus {
		NOT_EVALUATED,
		PASS,
		FAIL
	}
}
