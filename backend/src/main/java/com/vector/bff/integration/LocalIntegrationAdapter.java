package com.vector.bff.integration;
public interface LocalIntegrationAdapter<T> { LocalIntegrationContract contract(); LocalIntegrationResult<T> read(T sourceRecord); }
