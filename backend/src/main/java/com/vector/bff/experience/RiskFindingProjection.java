package com.vector.bff.experience;

public record RiskFindingProjection(String riskFindingId, String serviceId, String condition, String explanation,
		String evidenceBasis) {
}
