package com.vector.bff.experience;
import java.time.Instant;
import java.time.LocalDate;
public record CommitmentRenegotiationRequest(LocalDate newDueDate, String reason, Instant occurredAt) { }
