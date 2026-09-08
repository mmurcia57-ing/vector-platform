package com.vector.bff.intelligence;

import com.vector.bff.canonical.Change;
import com.vector.bff.canonical.Evidence;
import com.vector.bff.canonical.Incident;
import com.vector.bff.canonical.MetricObservation;
import com.vector.bff.canonical.MonitoringEvent;
import com.vector.bff.canonical.Problem;
import com.vector.bff.canonical.SLOObservation;
import com.vector.bff.canonical.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public record IntelligenceInput(
		Service service,
		List<MonitoringEvent> monitoringEvents,
		List<Incident> incidents,
		List<Problem> problems,
		List<SLOObservation> sloObservations,
		List<MetricObservation> metricObservations,
		List<Evidence> supportingEvidence,
		Change change,
		CorrelationContext correlationContext,
		IntelligencePolicy policy,
		Instant evaluatedAt) {
	public IntelligenceInput {
		Objects.requireNonNull(service, "service is required");
		monitoringEvents = List.copyOf(monitoringEvents == null ? List.of() : monitoringEvents);
		incidents = List.copyOf(incidents == null ? List.of() : incidents);
		problems = List.copyOf(problems == null ? List.of() : problems);
		sloObservations = List.copyOf(sloObservations == null ? List.of() : sloObservations);
		metricObservations = List.copyOf(metricObservations == null ? List.of() : metricObservations);
		supportingEvidence = List.copyOf(supportingEvidence == null ? List.of() : supportingEvidence);
		Objects.requireNonNull(policy, "policy is required");
		Objects.requireNonNull(evaluatedAt, "evaluatedAt is required");
	}
}
