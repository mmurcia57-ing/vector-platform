package com.vector.bff.integration;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.SourceAuthority;
import com.vector.bff.canonical.CanonicalMetadata;
import com.vector.bff.canonical.IdentityResolutionState;
import com.vector.bff.canonical.MonitoringEvent;
import com.vector.bff.canonical.Provenance;
import com.vector.bff.canonical.TemporalSemantics;
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

    @Test void routesAriaEventsWithoutChangingTheCanonicalBoundary() {
        var contract = new LocalIntegrationContract("INT-ARIA-LOCAL", "ARIA", "event", "MonitoringEvent",
            SourceAuthority.unknown(), List.of("source-aria"), IntegrationFreshness.CURRENT,
            IdentityResolutionState.UNRESOLVED, List.of("corporate mapping TBD"), IntegrationMaturity.L1_MOCK, true);
        var event = new MonitoringEvent(new CanonicalMetadata("event-1", IdentityResolutionState.INFERRED,
            TemporalSemantics.empty(), new Provenance(List.of("source-aria"), "test", "fixture"), SourceAuthority.unknown()),
            "latency", "degraded", "service-1");
        assertThat(new AriaMonitoringEventRoute(contract).read(event).value()).isSameAs(event);
        assertThat(new AriaMonitoringEventRoute(contract).read(event).contract()).isEqualTo(contract);
    }

    @Test void keepsSreSkillRouteDirectAndReadOnly() {
        var contract = new LocalIntegrationContract("INT-SRE-LOCAL", "SRE Skill", "evaluation", "SRESkillEvidence",
            SourceAuthority.unknown(), List.of("source-sre"), IntegrationFreshness.UNKNOWN,
            IdentityResolutionState.UNRESOLVED, List.of("provider and authority TBD"), IntegrationMaturity.L1_MOCK, true);
        assertThat(new DirectSreSkillRoute<String>(contract).read("fixture-evidence").value()).isEqualTo("fixture-evidence");
        assertThat(contract.readOnly()).isTrue();
    }
}
