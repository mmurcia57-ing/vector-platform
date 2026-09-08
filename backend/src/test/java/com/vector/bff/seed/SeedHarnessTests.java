package com.vector.bff.seed;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class SeedHarnessTests {
	private static final Instant FIXED_GENERATED_AT = Instant.parse("2025-01-01T00:00:00Z");
	private final SeedHarness harness = new SeedHarness();

	@Test
	void sameExplicitInputProducesTheSameLogicalDatasetInStableOrder() {
		var first = generate(DatasetProfile.SMALL, 42L);
		var second = generate(DatasetProfile.SMALL, 42L);

		assertThat(first.logicalFingerprint()).isEqualTo(second.logicalFingerprint());
		assertThat(first.records()).isEqualTo(second.records());
		assertThat(first.metadata().generatedAt()).isEqualTo(FIXED_GENERATED_AT);
		assertThat(first.records().get(DatasetDimension.MONITORING_EVENTS).getFirst().fixtureId())
			.isEqualTo("small-monitoring_events-0");
	}

	@Test
	void aDifferentControlledSeedProducesADifferentDeterministicDataset() {
		assertThat(generate(DatasetProfile.SMALL, 42L).logicalFingerprint())
			.isNotEqualTo(generate(DatasetProfile.SMALL, 43L).logicalFingerprint());
	}

	@Test
	void smallAndBaselineProfilesUseTheExactApprovedCounts() {
		assertThat(generate(DatasetProfile.SMALL, 7L).recordCounts())
			.isEqualTo(DatasetProfile.SMALL.counts());
		assertThat(generate(DatasetProfile.BASELINE, 7L).recordCounts())
			.isEqualTo(DatasetProfile.BASELINE.counts());
	}

	@Test
	void generatedTimestampsAreFixedAndNeverReadTheWallClock() {
		var items = generate(DatasetProfile.SMALL, 9L).records().values().stream().flatMap(List::stream).toList();

		assertThat(items).allSatisfy(item -> assertThat(item.occurredAt()).isAfterOrEqualTo(FIXED_GENERATED_AT));
		assertThat(items.getLast().occurredAt()).isBefore(FIXED_GENERATED_AT.plusSeconds(items.size()));
	}

	@Test
	void goldenManifestContainsAllApprovedScenariosAndLeavesOutcomesUnevaluated() {
		var manifest = generate(DatasetProfile.GOLDEN, 11L).goldenScenarioManifest();
		var expectedIds = List.of("GS-01", "GS-02", "GS-03", "GS-04", "GS-05", "GS-06", "GS-07", "GS-08", "GS-09", "GS-10", "GS-11", "GS-12");

		assertThat(manifest.scenarios()).extracting(GoldenScenarioManifest.ScenarioDescriptor::id)
			.containsExactlyElementsOf(expectedIds);
		assertThat(manifest.scenarios()).allSatisfy(scenario -> {
			assertThat(scenario.oracle().status()).isEqualTo(GoldenScenarioManifest.OracleStatus.NOT_EVALUATED);
			assertThat(scenario.oracle().actual()).isEqualTo("NOT_EVALUATED");
			assertThat(scenario.fixtureConcepts()).isNotEmpty();
		});
		assertThat(manifest.scenarios().stream().flatMap(scenario -> scenario.fixtureConcepts().stream()).distinct())
			.contains("AreaDomain", "Service", "ConfigurationItem", "MonitoringEvent", "Incident", "Problem", "Change", "Deployment", "SLO", "SLOObservation", "Commitment", "ImprovementAction", "OutcomeVerification", "Evidence", "RiskFinding", "MetricObservation", "SourceReference");
	}

	@Test
	void invalidGeneratorInputsAreRejected() {
		assertThatIllegalArgumentException().isThrownBy(() -> new DatasetRequest(DatasetProfile.SMALL, 1L,
			"", "generator-v1", "vector-v1", Map.of()));
		assertThatNullPointerException().isThrownBy(() -> new DatasetRequest(null, 1L,
			"dataset-v1", "generator-v1", "vector-v1", Map.of()));
	}

	@Test
	void syntheticFixturesDoNotContainSecretsOrCorporateProviderValues() {
		var dataset = generate(DatasetProfile.SMALL, 13L);
		var serialized = dataset.records().toString() + dataset.goldenScenarioManifest();

		assertThat(serialized).doesNotContainIgnoringCase("password", "secret", "credential", "servicenow",
			"dynatrace", "new relic");
	}

	private GeneratedDataset generate(DatasetProfile profile, long seed) {
		return harness.generate(new DatasetRequest(profile, seed, "dataset-v1", "generator-v1", "vector-v1",
			Map.of("fixture-origin", "synthetic")));
	}
}
