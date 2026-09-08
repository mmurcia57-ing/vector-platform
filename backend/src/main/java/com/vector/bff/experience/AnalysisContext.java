package com.vector.bff.experience;

public record AnalysisContext(
		String period,
		String areaDomainId,
		String serviceId,
		String riskFindingId,
		String comparisonContext) {
	public static AnalysisContext overview() {
		return new AnalysisContext(null, null, null, null, null);
	}
}
