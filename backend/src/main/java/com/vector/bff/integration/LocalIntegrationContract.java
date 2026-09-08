package com.vector.bff.integration;

import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.SourceAuthority;
import java.util.List;

/** Local L1 contract; source details remain outside the canonical boundary. */
public record LocalIntegrationContract(String integrationId, String sourceSystem, String externalObjectClass,
        String canonicalMapping, SourceAuthority authority, List<String> sourceReferenceIds,
        IntegrationFreshness freshness, IdentityResolutionState identityState, List<String> limitations,
        IntegrationMaturity maturity, boolean readOnly) {
    public LocalIntegrationContract {
        if (integrationId == null || integrationId.isBlank() || sourceSystem == null || sourceSystem.isBlank()) throw new IllegalArgumentException("integration identity is required");
        if (externalObjectClass == null || externalObjectClass.isBlank() || canonicalMapping == null || canonicalMapping.isBlank()) throw new IllegalArgumentException("mapping is required");
        authority = authority == null ? SourceAuthority.unknown() : authority;
        sourceReferenceIds = List.copyOf(sourceReferenceIds == null ? List.of() : sourceReferenceIds);
        limitations = List.copyOf(limitations == null ? List.of() : limitations);
        if (freshness == null || identityState == null || maturity == null) throw new IllegalArgumentException("integration semantics are required");
        if (!readOnly) throw new IllegalArgumentException("external integration must be read-only");
    }
}
