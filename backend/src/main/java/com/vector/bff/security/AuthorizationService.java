package com.vector.bff.security;
public final class AuthorizationService {
    public void require(SecurityPrincipal principal, SecurityPermission permission) {
        if (principal == null || permission == null || !allowed(principal, permission)) throw new AuthorizationDeniedException("authorization denied");
    }
    private boolean allowed(SecurityPrincipal principal, SecurityPermission permission) { return principal.roles().stream().anyMatch(role -> switch (permission) {
        case INVESTIGATE -> true;
        case MUTATE_POLICY, MUTATE_VECTOR -> role == SecurityRole.ANALYST_OPERATOR || role == SecurityRole.ADMINISTRATOR;
    }); }
}
