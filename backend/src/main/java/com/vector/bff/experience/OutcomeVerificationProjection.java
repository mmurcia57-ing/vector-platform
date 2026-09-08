package com.vector.bff.experience;

import java.util.List;

public record OutcomeVerificationProjection(String verificationId, String actionId, String outcome,
		List<String> evidenceIds) {
	public OutcomeVerificationProjection {
		evidenceIds = List.copyOf(evidenceIds == null ? List.of() : evidenceIds);
	}
}
