package com.vector.bff.observability;
public record ComponentHealth(String component, Status status, String detail) {
    public enum Status { UP, DEGRADED, DOWN }
    public ComponentHealth { if (component == null || component.isBlank() || status == null) throw new IllegalArgumentException("health is required"); }
}
