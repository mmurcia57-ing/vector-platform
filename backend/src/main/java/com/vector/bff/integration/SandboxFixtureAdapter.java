package com.vector.bff.integration;

import java.util.Objects;

/** Contract-compatible L1/L2 adapter; it contains no corporate endpoint or authority decision. */
public final class SandboxFixtureAdapter<T> implements LocalIntegrationAdapter<T> {
    private final LocalIntegrationContract contract;

    public SandboxFixtureAdapter(LocalIntegrationContract contract) {
        this.contract = Objects.requireNonNull(contract, "contract is required");
        if (contract.maturity() != IntegrationMaturity.L1_MOCK
                && contract.maturity() != IntegrationMaturity.L2_SANDBOX) {
            throw new IllegalArgumentException("fixture adapter supports only L1 mock or L2 sandbox contracts");
        }
    }

    @Override
    public LocalIntegrationContract contract() {
        return contract;
    }

    @Override
    public LocalIntegrationResult<T> read(T sourceRecord) {
        return new LocalIntegrationResult<>(Objects.requireNonNull(sourceRecord, "sourceRecord is required"), contract);
    }
}
