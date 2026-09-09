package com.vector.bff.ai;

import java.util.Objects;

/** Delegates bounded investigation assistance without making AI authoritative. */
public final class AiInvestigationService {
    private final AiProvider provider;

    public AiInvestigationService(AiProvider provider) {
        this.provider = Objects.requireNonNull(provider, "provider is required");
    }

    public AiProviderResult investigate(AiRequest request) {
        return provider.generate(Objects.requireNonNull(request, "request is required"));
    }
}
