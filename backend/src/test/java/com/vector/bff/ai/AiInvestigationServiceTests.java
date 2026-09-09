package com.vector.bff.ai;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class AiInvestigationServiceTests {
    @Test
    void investigationDelegatesOnlyTheBoundedAuthorizedRequest() {
        var provider = new RecordingProvider();
        var request = new AiRequest("explain-outcome", "service-1/risk-1", List.of("evidence-1"), "policy-v1", true);

        var result = new AiInvestigationService(provider).investigate(request);

        assertThat(provider.request).isSameAs(request);
        assertThat(result.status()).isEqualTo(AiProviderResult.Status.AVAILABLE);
        assertThat(result.provenance()).isEqualTo("provider-test");
    }

    private static final class RecordingProvider implements AiProvider {
        private AiRequest request;

        @Override
        public AiProviderResult generate(AiRequest request) {
            this.request = request;
            return new AiProviderResult(AiProviderResult.Status.AVAILABLE, "bounded explanation",
                List.of("AI output is not canonical fact"), "provider-test");
        }
    }
}
