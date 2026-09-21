package com.vector.bff.security;

import java.util.Set;

/**
 * Local HTTP security-context adapter for the deterministic VECTOR runtime.
 * It deliberately does not claim corporate authentication. A production adapter
 * must replace header resolution with the approved IdP/security context.
 */
public final class LocalHttpSecurityContextResolver {
    public SecurityPrincipal resolve(String subject, String role) {
        if (subject == null || subject.isBlank() || role == null || role.isBlank()) return null;
        try {
            return new SecurityPrincipal(subject, Set.of(SecurityRole.valueOf(role.trim().toUpperCase())));
        } catch (IllegalArgumentException invalidRole) {
            return null;
        }
    }
}
