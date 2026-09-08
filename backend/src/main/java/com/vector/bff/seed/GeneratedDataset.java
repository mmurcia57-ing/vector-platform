package com.vector.bff.seed;

import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/** A generated synthetic fixture set; it is not canonical persistence. */
public record GeneratedDataset(
	DatasetMetadata metadata,
	Map<DatasetDimension, List<SyntheticSeedItem>> records,
	GoldenScenarioManifest goldenScenarioManifest,
	String logicalFingerprint) {

	public int totalRecordCount() {
		return records.values().stream().mapToInt(List::size).sum();
	}

	public Map<DatasetDimension, Integer> recordCounts() {
		var counts = new EnumMap<DatasetDimension, Integer>(DatasetDimension.class);
		records.forEach((dimension, items) -> counts.put(dimension, items.size()));
		return Map.copyOf(counts);
	}

	public record DatasetMetadata(
		String datasetId,
		DatasetProfile profile,
		long seed,
		String datasetVersion,
		String generatorVersion,
		String vectorVersion,
		Map<String, String> effectiveConfiguration,
		Instant generatedAt) {
	}

	public record SyntheticSeedItem(String fixtureId, Instant occurredAt, String syntheticMarker) {
	}
}
