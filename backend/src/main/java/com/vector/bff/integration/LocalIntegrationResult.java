package com.vector.bff.integration;
public record LocalIntegrationResult<T>(T value, LocalIntegrationContract contract) { }
