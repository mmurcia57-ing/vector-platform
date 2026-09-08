package com.vector.bff.configuration;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("vector.reliability.policy")
public record VectorReliabilityPolicyProperties(
        @DefaultValue("local-dataset-v1") @NotBlank String reference,
        @DefaultValue("2") @Min(1) int minimumRecurringIncidents,
        @DefaultValue("2") @Min(1) int minimumSupportingEvidence) {
}
