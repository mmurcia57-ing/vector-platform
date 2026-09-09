package com.vector.bff.canonical;

import java.util.Map;
import java.time.LocalDate;

public record Commitment(CanonicalMetadata metadata, String declaration, String accountableAreaDomainId,
		String responsibleParty, LocalDate dueDate, String executionStatus, String intendedResult,
		String serviceId, String configurationItemId, String riskFindingId) implements CanonicalEntity {
	public Commitment {
		if (declaration == null || declaration.isBlank()) throw new IllegalArgumentException("declaration is required");
		boolean legacyFixture = accountableAreaDomainId == null && responsibleParty == null && dueDate == null
			&& intendedResult == null && serviceId == null && configurationItemId == null && riskFindingId != null;
		if (accountableAreaDomainId == null && !legacyFixture) {
			throw new IllegalArgumentException("accountableAreaDomainId is required for EXT-001 commitments");
		}
	}

	/** Backward-compatible constructor for the pre-EXT-001 canonical fixture shape. */
	public Commitment(CanonicalMetadata metadata, String declaration, String riskFindingId, String dueStatusContext) {
		this(metadata, declaration, null, null, null, dueStatusContext, null, null, null, riskFindingId);
	}

	public String canonicalType() { return "Commitment"; }
	public String dueStatusContext() { return executionStatus; }
	public Map<String, String> semanticAttributes() {
		return CanonicalEntitySupport.attributes("declaration", declaration, "accountableAreaDomainId", accountableAreaDomainId,
			"responsibleParty", responsibleParty, "dueDate", dueDate == null ? null : dueDate.toString(), "executionStatus", executionStatus,
			"intendedResult", intendedResult, "serviceId", serviceId, "configurationItemId", configurationItemId,
			"riskFindingId", riskFindingId);
	}

	public boolean overdueOn(LocalDate asOf) {
		if (dueDate == null || asOf == null || "COMPLETED".equalsIgnoreCase(executionStatus)) return false;
		return asOf.isAfter(dueDate);
	}
}
