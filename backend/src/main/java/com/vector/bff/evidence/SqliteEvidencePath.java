package com.vector.bff.evidence;

import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.SourceReference;
import com.vector.bff.persistence.CanonicalRecord;
import com.vector.bff.persistence.CanonicalRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Evidence/source-reference path over the established canonical repository boundary. */
public final class SqliteEvidencePath implements EvidencePath {
	private final CanonicalRepository repository;

	public SqliteEvidencePath(CanonicalRepository repository) {
		this.repository = repository;
	}

	@Override
	public EvidencePathRecord save(Evidence evidence, List<SourceReference> sourceReferences) {
		var references = List.copyOf(sourceReferences == null ? List.of() : sourceReferences);
		var expectedIds = Set.copyOf(evidence.metadata().provenance().sourceReferenceIds());
		var suppliedIds = new HashSet<String>();
		var storedReferences = new ArrayList<CanonicalRecord>();
		for (var reference : references) {
			if (!suppliedIds.add(reference.metadata().canonicalId())) {
				throw new IllegalArgumentException("duplicate SourceReference is not allowed");
			}
			storedReferences.add(repository.save(reference));
		}
		if (!expectedIds.equals(suppliedIds)) {
			throw new IllegalArgumentException("Evidence provenance must match supplied SourceReferences");
		}
		return new EvidencePathRecord(repository.save(evidence), storedReferences);
	}

	@Override
	public List<EvidencePathRecord> findAll() {
		var result = new ArrayList<EvidencePathRecord>();
		for (var evidence : repository.findAll("Evidence")) {
			var sourceReferences = new ArrayList<CanonicalRecord>();
			var ids = evidence.sourceReferenceIds() == null || evidence.sourceReferenceIds().isBlank()
				? List.<String>of() : List.of(evidence.sourceReferenceIds().split(",", -1));
			for (var id : ids) {
				repository.find("SourceReference", id).ifPresent(sourceReferences::add);
			}
			result.add(new EvidencePathRecord(evidence, sourceReferences));
		}
		return List.copyOf(result);
	}
}
