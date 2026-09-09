package com.vector.bff.performance;

public record BaselineWorkloadResult(int iterations, long p95Millis, long targetMillis,
        boolean semanticallyComplete, String profile) {
    public BaselineWorkloadResult {
        if (iterations <= 0 || p95Millis < 0 || targetMillis <= 0 || profile == null || profile.isBlank()) {
            throw new IllegalArgumentException("baseline result is required");
        }
    }

    public boolean withinTarget() {
        return p95Millis <= targetMillis;
    }
}
