package com.vector.bff.integration;

/** Direct SRE Skill boundary; ARIA is not used as an intermediary. */
public final class DirectSreSkillRoute<S> implements ProviderRoute<S, S> {
    private final LocalIntegrationContract contract;

    public DirectSreSkillRoute(LocalIntegrationContract contract) {
        if (contract == null || !"SRESkillEvidence".equals(contract.canonicalMapping())) throw new IllegalArgumentException("SRE Skill mapping is required");
        this.contract = contract;
    }

    @Override
    public LocalIntegrationResult<S> read(S sourceRecord) {
        if (sourceRecord == null) throw new IllegalArgumentException("sourceRecord is required");
        return new LocalIntegrationResult<>(sourceRecord, contract);
    }
}
