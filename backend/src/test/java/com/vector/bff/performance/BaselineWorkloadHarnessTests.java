package com.vector.bff.performance;

import static org.assertj.core.api.Assertions.assertThat;

import com.vector.bff.experience.AnalysisContext;
import com.vector.bff.experience.DefaultExperienceProjectionUseCase;
import com.vector.bff.experience.ExperienceProjectionSource;
import com.vector.bff.experience.PreparedExperienceContext;
import com.vector.bff.experience.ProjectionQuality;
import com.vector.bff.experience.ServiceProjection;
import java.util.List;
import com.vector.bff.experience.ProjectionRequest;
import org.junit.jupiter.api.Test;

class BaselineWorkloadHarnessTests {
    @Test
    void baselineUsesBffProjectionAndReportsLocalMetadata() {
        var request = new ProjectionRequest(new AnalysisContext("2025-Q1", "area-platform", "service-payments", null, null), 10);
        ExperienceProjectionSource source = ignored -> new PreparedExperienceContext(
            List.of(), List.of(new ServiceProjection("service-payments", "Payments", "area-platform", "degraded")),
            List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("complete", "current", "confirmed", List.of(), List.of(), List.of(), false, false));
        var result = new BaselineWorkloadHarness(new DefaultExperienceProjectionUseCase(source))
            .run(request, 20, 2_000);

        assertThat(result.profile()).isEqualTo("LOCAL_BASELINE");
        assertThat(result.iterations()).isEqualTo(20);
        assertThat(result.semanticallyComplete()).isTrue();
        assertThat(result.p95Millis()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void boundedLocalStressProfileUsesTheSameBffSemantics() {
        var request = new ProjectionRequest(new AnalysisContext("2025-Q1", "area-platform", "service-payments", null, null), 10);
        ExperienceProjectionSource source = ignored -> new PreparedExperienceContext(
            List.of(), List.of(new ServiceProjection("service-payments", "Payments", "area-platform", "degraded")),
            List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("complete", "current", "confirmed", List.of(), List.of(), List.of(), false, false));

        var result = new BaselineWorkloadHarness(new DefaultExperienceProjectionUseCase(source))
            .run(request, 100, 2_000, LocalWorkloadProfile.STRESS);

        assertThat(result.profile()).isEqualTo("LOCAL_STRESS");
        assertThat(result.iterations()).isEqualTo(100);
        assertThat(result.semanticallyComplete()).isTrue();
    }
    @Test
    void mixedProfileIsExplicitlyLocalAndDoesNotClaimCorporateCapacity() {
        var request = new ProjectionRequest(new AnalysisContext("2025-Q1", "area-platform", "service-payments", null, null), 10);
        ExperienceProjectionSource source = ignored -> new PreparedExperienceContext(
            List.of(), List.of(new ServiceProjection("service-payments", "Payments", "area-platform", "degraded")),
            List.of(), List.of(), List.of(), List.of(), List.of(),
            new ProjectionQuality("complete", "current", "confirmed", List.of(), List.of(), List.of(), false, false));
        var result = new BaselineWorkloadHarness(new DefaultExperienceProjectionUseCase(source))
            .run(request, 50, 2_000, LocalWorkloadProfile.MIXED);
        assertThat(result.profile()).isEqualTo("LOCAL_MIXED");
        assertThat(result.semanticallyComplete()).isTrue();
    }

}
