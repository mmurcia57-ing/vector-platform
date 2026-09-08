package com.vector.bff.security;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class SecurityBoundaryTests {
    private final AuthorizationService authorization = new AuthorizationService();
    @Test void deniesMissingOrInsufficientContext() {
        assertThatThrownBy(() -> authorization.require(null, SecurityPermission.INVESTIGATE)).isInstanceOf(AuthorizationDeniedException.class);
        assertThatThrownBy(() -> authorization.require(new SecurityPrincipal("viewer", Set.of(SecurityRole.VIEWER)), SecurityPermission.MUTATE_VECTOR)).isInstanceOf(AuthorizationDeniedException.class);
    }
    @Test void permitsInvestigationAndAuthorizedMutation() {
        authorization.require(new SecurityPrincipal("analyst", Set.of(SecurityRole.ANALYST_OPERATOR)), SecurityPermission.INVESTIGATE);
        authorization.require(new SecurityPrincipal("admin", Set.of(SecurityRole.ADMINISTRATOR)), SecurityPermission.MUTATE_POLICY);
    }
    @Test void recordsMutationAuditWithoutCredentialMaterial() {
        var recorder = new InMemorySecurityAuditRecorder();
        recorder.record(new SecurityAuditRecord("VECTOR_NATIVE_MUTATION", Instant.parse("2026-01-01T00:00:00Z"), "admin", true, "effective"));
        assertThat(recorder.records()).singleElement().satisfies(record -> assertThat(record.toString()).doesNotContainIgnoringCase("password", "token", "secret", "credential"));
    }
}
