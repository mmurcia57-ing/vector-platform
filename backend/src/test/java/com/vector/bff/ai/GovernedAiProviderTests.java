package com.vector.bff.ai;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class GovernedAiProviderTests {
    @Test
    void unavailableAIIsExplicitAndDoesNotFabricateAnAnswer() {
        var request = new AiRequest("explain-risk", "service-1/risk-1", List.of("evidence-1"), "policy-v1", true);

        var result = new GovernedAiProvider().generate(request);

        assertThat(result.status()).isEqualTo(AiProviderResult.Status.UNAVAILABLE);
        assertThat(result.explanation()).isNull();
        assertThat(result.provenance()).isEqualTo("governed-provider-boundary");
    }

    @Test
    void unauthorizedContextIsRejectedWithoutProviderInvocation() {
        var result = new GovernedAiProvider().generate(
            new AiRequest("summarize-evidence", "service-1", List.of(), "policy-v1", false));

        assertThat(result.status()).isEqualTo(AiProviderResult.Status.UNAVAILABLE);
        assertThat(result.limitations()).contains("AI context is not authorized");
    }
}
