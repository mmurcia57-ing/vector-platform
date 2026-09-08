package com.vector.bff.security;
import java.util.ArrayList;
import java.util.List;
public final class InMemorySecurityAuditRecorder implements SecurityAuditRecorder {
    private final List<SecurityAuditRecord> records = new ArrayList<>();
    public void record(SecurityAuditRecord record) { records.add(record); }
    public List<SecurityAuditRecord> records() { return List.copyOf(records); }
}
