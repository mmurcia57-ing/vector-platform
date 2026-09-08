package com.vector.bff.integration;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.SourceAuthority;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class LocalIntegrationContractTests {
    @Test void preservesUnknownAuthorityAndReadOnlyBoundary() {
        var contract = new LocalIntegrationContract("INT-LOCAL-01", "local-fixture", "operational event", "MonitoringEvent", SourceAuthority.unknown(), List.of("source-1"), IntegrationFreshness.CURRENT, IdentityResolutionState.UNRESOLVED, List.of("synthetic fixture"), IntegrationMaturity.L1_MOCK, true);
        assertThat(contract.authority()).isEqualTo(SourceAuthority.unknown());
        assertThat(contract.readOnly()).isTrue();
        assertThat(contract.maturity()).isEqualTo(IntegrationMaturity.L1_MOCK);
    }
    @Test void rejectsExternalWriteBack() {
        assertThatThrownBy(() -> new LocalIntegrationContract("INT-LOCAL-01", "fixture", "event", "MonitoringEvent", null, List.of(), IntegrationFreshness.UNKNOWN, IdentityResolutionState.UNRESOLVED, List.of(), IntegrationMaturity.L1_MOCK, false)).isInstanceOf(IllegalArgumentException.class);
    }
}
