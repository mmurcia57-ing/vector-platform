package com.vector.bff.intelligence;

import com.vector.bff.canonical.RiskFinding;

import java.util.List;
import java.util.Optional;

public record IntelligenceResult(
		Optional<RiskFinding> riskFinding,
		int relevantIncidentCount,
		int relevantMonitoringEventCount,
		int metricObservationCount,
		int sloObservationCount,
		CorrelationContext correlationContext,
		List<String> limitations) {
	public IntelligenceResult {
		riskFinding = riskFinding == null ? Optional.empty() : riskFinding;
		limitations = List.copyOf(limitations == null ? List.of() : limitations);
	}
}
