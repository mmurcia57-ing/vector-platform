package com.vector.bff.ai;

/** Validates provider output before it is exposed as AI-generated intelligence. */
public final class AiOutputValidator {
    public AiProviderResult validate(AiProviderResult result) {
        if (result == null) {
            return invalid("AI provider returned no structured output");
        }
        if (result.status() == AiProviderResult.Status.AVAILABLE
                && (result.explanation() == null || result.explanation().isBlank())) {
            return invalid("AI provider output omitted its explanation");
        }
        return result;
    }

    private AiProviderResult invalid(String limitation) {
        return new AiProviderResult(AiProviderResult.Status.INVALID, null,
            java.util.List.of(limitation), "governed-output-validation");
    }
}
