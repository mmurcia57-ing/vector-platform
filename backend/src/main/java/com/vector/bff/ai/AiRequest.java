package com.vector.bff.ai;

import java.util.List;

public record AiRequest(String capability, String contextReference, List<String> evidenceReferences,
        String policyVersion, boolean authorized) {
    public AiRequest {
        if (capability == null || capability.isBlank() || contextReference == null || contextReference.isBlank()
                || policyVersion == null || policyVersion.isBlank()) throw new IllegalArgumentException("bounded AI request is required");
        evidenceReferences = List.copyOf(evidenceReferences == null ? List.of() : evidenceReferences);
    }
}
