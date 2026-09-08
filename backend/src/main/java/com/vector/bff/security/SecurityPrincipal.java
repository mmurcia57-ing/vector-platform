package com.vector.bff.security;
import java.util.Set;
public record SecurityPrincipal(String subject, Set<SecurityRole> roles) {
    public SecurityPrincipal { if (subject == null || subject.isBlank()) throw new IllegalArgumentException("subject is required"); roles = roles == null ? Set.of() : Set.copyOf(roles); }
}
