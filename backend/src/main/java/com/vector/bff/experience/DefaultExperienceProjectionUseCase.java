package com.vector.bff.experience;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/** Composes prepared application data into bounded experience projections. */
public final class DefaultExperienceProjectionUseCase implements ExperienceProjectionUseCase {
	private final ExperienceProjectionSource source;

	public DefaultExperienceProjectionUseCase(ExperienceProjectionSource source) {
		this.source = Objects.requireNonNull(source, "source is required");
	}

	@Override
	public TechnologyOverviewProjection technologyOverview(ProjectionRequest request) {
		var prepared = source.load(request);
		return new TechnologyOverviewProjection(request.context(), limit(prepared.areas(), request.limit()),
			limit(prepared.services(), request.limit()), limit(prepared.riskFindings(), request.limit()),
            limit(prepared.commitments(), request.limit()), limit(prepared.improvementActions(), request.limit()),
            limit(prepared.outcomeVerifications(), request.limit()), prepared.quality());
	}

	@Override
	public ServiceIntelligenceProjection serviceIntelligence(ProjectionRequest request) {
		var serviceId = required(request.context().serviceId(), "serviceId");
		var prepared = source.load(request);
		var service = prepared.services().stream().filter(item -> serviceId.equals(item.serviceId())).findFirst().orElse(null);
		var quality = service == null ? prepared.quality().withMissingContext("selected Service " + serviceId) : prepared.quality();
		var riskFindings = limit(prepared.riskFindings().stream().filter(item -> serviceId.equals(item.serviceId())).toList(), request.limit());
		var riskIds = riskFindings.stream().map(RiskFindingProjection::riskFindingId).collect(java.util.stream.Collectors.toSet());
		var evidence = limit(prepared.evidence().stream().filter(item -> serviceId.equals(item.serviceId()) || riskIds.contains(item.riskFindingId())).toList(), request.limit());
		var commitments = limit(prepared.commitments().stream().filter(item -> riskIds.contains(item.riskFindingId())).toList(), request.limit());
		var commitmentIds = commitments.stream().map(CommitmentProjection::commitmentId).collect(java.util.stream.Collectors.toSet());
		var actions = limit(prepared.improvementActions().stream().filter(item -> commitmentIds.contains(item.commitmentId())).toList(), request.limit());
		var actionIds = actions.stream().map(ImprovementActionProjection::actionId).collect(java.util.stream.Collectors.toSet());
		var outcomes = limit(prepared.outcomeVerifications().stream().filter(item -> actionIds.contains(item.actionId())).toList(), request.limit());
		return new ServiceIntelligenceProjection(request.context(), service, riskFindings, evidence, commitments, actions, outcomes, quality);
	}

	@Override
	public RiskInvestigationProjection riskInvestigation(ProjectionRequest request) {
		var riskId = required(request.context().riskFindingId(), "riskFindingId");
		var prepared = source.load(request);
		var finding = prepared.riskFindings().stream().filter(item -> riskId.equals(item.riskFindingId())).findFirst().orElse(null);
		var quality = finding == null ? prepared.quality().withMissingContext("selected RiskFinding " + riskId) : prepared.quality();
		var evidence = limit(prepared.evidence().stream().filter(item -> riskId.equals(item.riskFindingId()) ||
			(finding != null && finding.evidenceBasis() != null && List.of(finding.evidenceBasis().split(",", -1)).contains(item.evidenceId()))).toList(), request.limit());
		var commitments = limit(prepared.commitments().stream().filter(item -> riskId.equals(item.riskFindingId())).toList(), request.limit());
		var commitmentIds = commitments.stream().map(CommitmentProjection::commitmentId).collect(java.util.stream.Collectors.toSet());
		var actions = limit(prepared.improvementActions().stream().filter(item -> commitmentIds.contains(item.commitmentId())).toList(), request.limit());
		var actionIds = actions.stream().map(ImprovementActionProjection::actionId).collect(java.util.stream.Collectors.toSet());
		var outcomes = limit(prepared.outcomeVerifications().stream().filter(item -> actionIds.contains(item.actionId())).toList(), request.limit());
		return new RiskInvestigationProjection(request.context(), finding, evidence, commitments, actions, outcomes, quality);
	}

	@Override
	public List<TemporalSignalProjection> temporalSignals(ProjectionRequest request) {
		var prepared = source.load(request);
		var serviceId = request.context().serviceId();
		var riskId = request.context().riskFindingId();
		return limit(prepared.evidence().stream()
			.filter(item -> serviceId == null || serviceId.equals(item.serviceId()))
			.filter(item -> riskId == null || riskId.equals(item.riskFindingId()))
			.sorted(java.util.Comparator.comparing(EvidenceProjection::observedAt))
			.map(item -> new TemporalSignalProjection(item.evidenceId(), item.serviceId(), item.riskFindingId(),
				semanticType(item), item.supportedClaim(), item.observedAt(), item.sourceReferenceIds(), item.limitations()))
			.toList(), request.limit());
	}

	private static String semanticType(EvidenceProjection item) {
		var joined = String.join(" ", item.sourceReferenceIds()).toLowerCase();
		if (joined.contains("incident")) return "INCIDENT_EVIDENCE";
		if (joined.contains("slo") || joined.contains("sli")) return "SLO_SLI_EVIDENCE";
		if (joined.contains("event") || joined.contains("observability")) return "OPERATIONAL_SIGNAL";
		return "OBSERVED_EVIDENCE";
	}

	private static String required(String value, String name) {
		if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " is required");
		return value;
	}

	private static <T> List<T> limit(List<T> values, int limit) {
		return values.size() <= limit ? List.copyOf(values) : List.copyOf(values.subList(0, limit));
	}
}
