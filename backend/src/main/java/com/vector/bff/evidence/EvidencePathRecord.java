package com.vector.bff.evidence;

import com.vector.bff.persistence.CanonicalRecord;

import java.util.List;

public record EvidencePathRecord(CanonicalRecord evidence, List<CanonicalRecord> sourceReferences) {
	public EvidencePathRecord {
		sourceReferences = List.copyOf(sourceReferences);
	}
}
