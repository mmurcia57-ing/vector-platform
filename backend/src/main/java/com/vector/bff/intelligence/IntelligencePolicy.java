package com.vector.bff.intelligence;

/** Explicit local evaluation inputs; formulas, thresholds, and prioritization remain outside this contract. */
public record IntelligencePolicy(String policyReference, int minimumRecurringIncidents, int minimumSupportingEvidence) {
	public IntelligencePolicy {
		if (policyReference == null || policyReference.isBlank()) {
			throw new IllegalArgumentException("policyReference is required");
		}
		if (minimumRecurringIncidents < 1 || minimumSupportingEvidence < 1) {
			throw new IllegalArgumentException("minimum values must be positive");
		}
	}
}
