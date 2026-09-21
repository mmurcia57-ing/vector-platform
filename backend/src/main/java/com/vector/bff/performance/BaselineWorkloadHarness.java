package com.vector.bff.performance;

import com.vector.bff.experience.ExperienceProjectionUseCase;
import com.vector.bff.experience.ProjectionRequest;
import java.util.Objects;

/** Local correctness-first BASELINE harness over the BFF experience boundary. */
public final class BaselineWorkloadHarness {
    private final ExperienceProjectionUseCase useCase;

    public BaselineWorkloadHarness(ExperienceProjectionUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase, "useCase is required");
    }

    public BaselineWorkloadResult run(ProjectionRequest request, int iterations, long targetMillis) {
        return run(request, iterations, targetMillis, LocalWorkloadProfile.BASELINE);
    }

    public BaselineWorkloadResult run(ProjectionRequest request, int iterations, long targetMillis, LocalWorkloadProfile profile) {
        if (iterations <= 0 || targetMillis <= 0 || profile == null) throw new IllegalArgumentException("local workload configuration is required");
        var samples = new long[iterations];
        for (int index = 0; index < iterations; index++) {
            var started = System.nanoTime();
            var projection = useCase.serviceIntelligence(request);
            if (projection == null || projection.service() == null) {
                throw new IllegalStateException("baseline projection is incomplete");
            }
            samples[index] = (System.nanoTime() - started) / 1_000_000;
        }
        java.util.Arrays.sort(samples);
        int p95Index = Math.min(samples.length - 1, (int) Math.ceil(samples.length * 0.95) - 1);
        return new BaselineWorkloadResult(iterations, samples[p95Index], targetMillis, true, "LOCAL_" + profile.name());
    }
}
