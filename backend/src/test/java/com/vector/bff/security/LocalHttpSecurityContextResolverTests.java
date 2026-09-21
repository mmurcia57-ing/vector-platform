package com.vector.bff.security;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalHttpSecurityContextResolverTests {
    private final LocalHttpSecurityContextResolver resolver = new LocalHttpSecurityContextResolver();
    private final AuthorizationService authorization = new AuthorizationService();

    @Test
    void missingHttpIdentityCannotBecomeAPrincipal() {
        assertThat(resolver.resolve(null, null)).isNull();
        assertThatThrownBy(() -> authorization.require(null, SecurityPermission.MUTATE_VECTOR))
            .isInstanceOf(AuthorizationDeniedException.class);
    }

    @Test
    void viewerCannotMutateVector() {
        var principal = resolver.resolve("viewer-1", "VIEWER");
        assertThatThrownBy(() -> authorization.require(principal, SecurityPermission.MUTATE_VECTOR))
            .isInstanceOf(AuthorizationDeniedException.class);
    }

    @Test
    void localOperatorCanCrossTheExplicitMutationBoundary() {
        var principal = resolver.resolve("operator-1", "ANALYST_OPERATOR");
        authorization.require(principal, SecurityPermission.MUTATE_VECTOR);
        assertThat(principal.subject()).isEqualTo("operator-1");
    }

    @Test
    void invalidRoleFailsClosed() {
        assertThat(resolver.resolve("subject", "NOT_A_ROLE")).isNull();
    }
}
