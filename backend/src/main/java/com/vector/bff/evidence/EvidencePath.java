package com.vector.bff.evidence;

import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.SourceReference;

import java.util.List;

public interface EvidencePath {
	EvidencePathRecord save(Evidence evidence, List<SourceReference> sourceReferences);

	List<EvidencePathRecord> findAll();
}
