package com.vector.bff.evidence;

import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.SourceReference;
import com.vector.bff.canonical.TemporalSemantics;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class EvidencePathTests {
	private static final Instant OBSERVED = Instant.parse("2025-01-01T00:00:00Z");

	@Test
	void preservesMultipleConflictingEvidenceRecordsAndTheirSourceTraceability() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var sourceA = source("source-a", "provider-a", "claim-a");
			var sourceB = source("source-b", "provider-b", "claim-b");
			var path = new SqliteEvidencePath(repository);
			path.save(evidence("evidence-a", "availability is stable", "source-a"), List.of(sourceA));
			path.save(evidence("evidence-b", "availability is degraded", "source-b"), List.of(sourceB));

			var records = path.findAll();
			assertThat(records).hasSize(2);
			assertThat(records).extracting(record -> record.evidence().canonicalId())
				.containsExactly("evidence-a", "evidence-b");
			assertThat(records.get(0).sourceReferences()).extracting(record -> record.canonicalId())
				.containsExactly("source-a");
			assertThat(records.get(1).sourceReferences()).extracting(record -> record.canonicalId())
				.containsExactly("source-b");
			assertThat(records.get(0).evidence().payload()).contains("availability is stable");
			assertThat(records.get(1).evidence().payload()).contains("availability is degraded");
		}
	}

	@Test
	void preservesUnknownAuthorityAndMissingContextWithoutCurrentTimeFallback() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var source = source("source-unknown", "unidentified-provider", "native-unknown");
			var evidence = evidence("evidence-partial", "partial observation", "source-unknown");
			var stored = new SqliteEvidencePath(repository).save(evidence, List.of(source));

			assertThat(stored.evidence().authorityReference()).isNull();
			assertThat(stored.evidence().authorityExplicit()).isFalse();
			assertThat(stored.evidence().observedAt()).isNull();
			assertThat(stored.evidence().ingestedAt()).isEqualTo(OBSERVED);
			assertThat(stored.evidence().payload()).contains("partial observation");
		}
	}

	@Test
	void rejectsEvidenceWhoseProvenanceDoesNotMatchSuppliedSourceReferences() {
		try (var repository = new SqliteCanonicalRepository("jdbc:sqlite::memory:")) {
			var path = new SqliteEvidencePath(repository);
			assertThatIllegalArgumentException().isThrownBy(() -> path.save(
				evidence("evidence-1", "claim", "source-1"), List.of(source("source-2", "provider", "native"))));
		}
	}

	private static Evidence evidence(String id, String claim, String sourceReferenceId) {
		return new Evidence(metadata(id, List.of(sourceReferenceId)), "operational-observation", claim,
			"service context unavailable; limitation retained");
	}

	private static SourceReference source(String id, String system, String nativeId) {
		return new SourceReference(metadata(id, List.of()), system, nativeId, "claim-context", null);
	}

	private static CanonicalMetadata metadata(String id, List<String> sourceReferenceIds) {
		return new CanonicalMetadata(id, IdentityResolutionState.UNRESOLVED,
			new TemporalSemantics(null, null, OBSERVED, null, null),
			new Provenance(sourceReferenceIds, "source-preserving-ingestion", "authority not established"),
			SourceAuthority.unknown());
	}
}
