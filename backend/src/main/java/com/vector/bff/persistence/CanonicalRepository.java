package com.vector.bff.persistence;

import com.vector.bff.canonical.CanonicalEntity;

import java.util.List;
import java.util.Optional;

public interface CanonicalRepository extends AutoCloseable {
	CanonicalRecord save(CanonicalEntity entity);

	Optional<CanonicalRecord> find(String canonicalType, String canonicalId);

	List<CanonicalRecord> findAll(String canonicalType);

	@Override
	void close();
}
