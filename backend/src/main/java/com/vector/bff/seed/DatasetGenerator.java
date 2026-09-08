package com.vector.bff.seed;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.SplittableRandom;
import java.util.TreeMap;

/** Deterministic, in-memory generator for synthetic seed fixtures. */
public final class DatasetGenerator {
	private static final Instant FIXED_GENERATED_AT = Instant.parse("2025-01-01T00:00:00Z");

	public GeneratedDataset generate(DatasetRequest request) {
		var random = new SplittableRandom(request.seed());
		var records = new EnumMap<DatasetDimension, List<GeneratedDataset.SyntheticSeedItem>>(DatasetDimension.class);
		long timestampOffset = 0;

		for (DatasetDimension dimension : DatasetDimension.values()) {
			int count = request.profile().counts().getOrDefault(dimension, 0);
			var items = new ArrayList<GeneratedDataset.SyntheticSeedItem>(count);
			for (int index = 0; index < count; index++) {
				items.add(new GeneratedDataset.SyntheticSeedItem(
					request.profile().name().toLowerCase(Locale.ROOT) + "-" + dimension.name().toLowerCase(Locale.ROOT) + "-" + index,
					FIXED_GENERATED_AT.plusSeconds(timestampOffset++),
					Long.toUnsignedString(random.nextLong(), 16)));
			}
			records.put(dimension, List.copyOf(items));
		}

		var metadata = new GeneratedDataset.DatasetMetadata(
			"synthetic-" + request.profile().name().toLowerCase(Locale.ROOT) + "-" + Long.toUnsignedString(request.seed()),
			request.profile(), request.seed(), request.datasetVersion(), request.generatorVersion(),
			request.vectorVersion(), Collections.unmodifiableMap(new TreeMap<>(request.effectiveConfiguration())), FIXED_GENERATED_AT);
		var manifest = GoldenScenarioManifest.approved();
		return new GeneratedDataset(metadata, Map.copyOf(records), manifest, fingerprint(metadata, records, manifest));
	}

	private String fingerprint(GeneratedDataset.DatasetMetadata metadata,
			Map<DatasetDimension, List<GeneratedDataset.SyntheticSeedItem>> records,
			GoldenScenarioManifest manifest) {
		var content = new StringBuilder(metadata.toString());
		for (DatasetDimension dimension : DatasetDimension.values()) {
			for (GeneratedDataset.SyntheticSeedItem item : records.get(dimension)) {
				content.append('|').append(dimension).append('|').append(item);
			}
		}
		for (GoldenScenarioManifest.ScenarioDescriptor scenario : manifest.scenarios()) {
			content.append('|').append(scenario);
		}
		try {
			return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
				.digest(content.toString().getBytes(StandardCharsets.UTF_8)));
		}
		catch (NoSuchAlgorithmException exception) {
			throw new IllegalStateException("SHA-256 must be available", exception);
		}
	}
}
