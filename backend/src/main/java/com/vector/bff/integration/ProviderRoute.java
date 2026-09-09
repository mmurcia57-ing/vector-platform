package com.vector.bff.integration;

public interface ProviderRoute<S, T> {
    LocalIntegrationResult<T> read(S sourceRecord);
}
