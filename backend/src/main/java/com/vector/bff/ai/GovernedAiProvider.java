package com.vector.bff.ai;

/** Provider boundary with no vendor, model, or deterministic-domain authority. */
public final class GovernedAiProvider implements AiProvider {
    @Override
    public AiProviderResult generate(AiRequest request) {
        if (!request.authorized()) return AiProviderResult.unavailable("AI context is not authorized");
        return AiProviderResult.unavailable("No AI provider is configured");
    }
}
