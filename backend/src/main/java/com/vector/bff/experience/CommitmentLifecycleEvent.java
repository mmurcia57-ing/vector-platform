package com.vector.bff.experience;

import java.time.Instant;
import java.time.LocalDate;

public record CommitmentLifecycleEvent(String eventId, String commitmentId, String eventType,
        String fromStatus, String toStatus, LocalDate priorDueDate, LocalDate newDueDate,
        String reason, Instant occurredAt, boolean beforeDueDate) { }
