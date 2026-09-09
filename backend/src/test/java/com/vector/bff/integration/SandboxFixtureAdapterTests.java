package com.vector.bff.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.SourceAuthority;
import java.util.List;
import org.junit.jupiter.api.Test;

class SandboxFixtureAdapterTests {
    @Test
    void serviceNowPdiFixturePreservesUnknownAuthorityAndUnresolvedIdentity() {
        var contract = contract("INT-02", "ServiceNow PDI fixture", "Incident/Problem/Change context",
            "Incident,Problem,Change,ConfigurationItem", IntegrationMaturity.L2_SANDBOX);

        var result = new SandboxFixtureAdapter<String>(contract).read("synthetic-pdi-record");

        assertThat(result.contract().authority()).isEqualTo(SourceAuthority.unknown());
        assertThat(result.contract().identityState()).isEqualTo(IdentityResolutionState.UNRESOLVED);
        assertThat(result.contract().readOnly()).isTrue();
    }

    @Test
    void scmFixtureDoesNotSelectACorporateProviderOrAddCanonicalEntities() {
        var contract = contract("INT-06", "SCM fixture", "change/deployment context",
            "Change,Deployment,Service,Evidence,SourceReference", IntegrationMaturity.L1_MOCK);

        var result = new SandboxFixtureAdapter<String>(contract).read("synthetic-scm-record");

        assertThat(result.value()).isEqualTo("synthetic-scm-record");
        assertThat(result.contract().limitations()).contains("corporate source, mapping and authority remain TBD");
        assertThat(result.contract().canonicalMapping()).doesNotContain("Repository", "PullRequest", "Branch");
    }

    @Test
    void fixtureAdapterRejectsCorporateMaturityClaims() {
        assertThatThrownBy(() -> new SandboxFixtureAdapter<>(contract("INT-06", "SCM fixture", "change",
            "Change", IntegrationMaturity.L3_CORPORATE_READ_ONLY))).isInstanceOf(IllegalArgumentException.class);
    }

    private static LocalIntegrationContract contract(String id, String source, String externalClass,
            String mapping, IntegrationMaturity maturity) {
        return new LocalIntegrationContract(id, source, externalClass, mapping, SourceAuthority.unknown(),
            List.of("synthetic-source-reference"), IntegrationFreshness.UNKNOWN,
            IdentityResolutionState.UNRESOLVED,
            List.of("corporate source, mapping and authority remain TBD"), maturity, true);
    }
}
