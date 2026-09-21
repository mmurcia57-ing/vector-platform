package com.vector.bff.experience;
import java.time.Instant;
public record CommitmentLifecycleUpdateRequest(String executionStatus, String reason, Instant occurredAt) { }
