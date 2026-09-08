package com.vector.bff.seed;

/**
 * Entry point for later tests, demos, and slices to obtain deterministic
 * synthetic fixtures and the non-evaluated Golden oracle manifest.
 */
public final class SeedHarness {
	private final DatasetGenerator generator = new DatasetGenerator();

	public GeneratedDataset generate(DatasetRequest request) {
		return generator.generate(request);
	}
}
