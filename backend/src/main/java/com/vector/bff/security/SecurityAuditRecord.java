package com.vector.bff.security;
import java.time.Instant;
public record SecurityAuditRecord(String operation, Instant occurredAt, String subject, boolean permitted, String state) { }
