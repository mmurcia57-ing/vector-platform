package com.vector.bff.configuration;

import jakarta.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("vector.runtime")
public record VectorRuntimeProperties(@DefaultValue("local") @NotBlank String environment) {
}
