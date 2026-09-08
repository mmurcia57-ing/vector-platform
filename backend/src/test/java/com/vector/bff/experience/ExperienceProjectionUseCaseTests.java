package com.vector.bff.experience;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ExperienceProjectionUseCaseTests {
	private final RecordingSource source = new RecordingSource();
	private final ExperienceProjectionUseCase useCase = new DefaultExperienceProjectionUseCase(source);

	@Test
	void technologyOverviewIsBoundedAndPreservesSharedAnalysisContextAndQuality() {
		var context = new AnalysisContext("2025-Q1", "area-1", null, null, "before/after");
		var projection = useCase.technologyOverview(new ProjectionRequest(context, 1));

		assertThat(projection.context()).isEqualTo(context);
		assertThat(projection.areas()).hasSize(1);
		assertThat(projection.services()).hasSize(1);
		assertThat(projection.attentionFindings()).hasSize(1);
		assertThat(projection.quality().stale()).isTrue();
		assertThat(projection.quality().conflicting()).isTrue();
		assertThat(source.lastRequest.context()).isEqualTo(context);
	}

	@Test
	void serviceProjectionComposesOnlySelectedServiceContextWithoutDomainRecalculation() {
		var context = new AnalysisContext(null, "area-1", "service-1", null, null);
		var projection = useCase.serviceIntelligence(new ProjectionRequest(context, 10));

		assertThat(projection.service().serviceId()).isEqualTo("service-1");
		assertThat(projection.riskFindings()).extracting(RiskFindingProjection::riskFindingId).containsExactly("risk-1");
		assertThat(projection.evidence()).extracting(EvidenceProjection::evidenceId).containsExactly("evidence-1");
		assertThat(projection.commitments()).extracting(CommitmentProjection::commitmentId).containsExactly("commitment-1");
		assertThat(projection.improvementActions()).extracting(ImprovementActionProjection::actionId).containsExactly("action-1");
		assertThat(projection.outcomeVerifications()).extracting(OutcomeVerificationProjection::verificationId).containsExactly("outcome-1");
	}

	@Test
	void riskInvestigationRetainsEvidenceAndActionOutcomeContinuity() {
		var context = new AnalysisContext(null, "area-1", "service-1", "risk-1", "comparison");
		var projection = useCase.riskInvestigation(new ProjectionRequest(context, 10));

		assertThat(projection.riskFinding()).isNotNull();
		assertThat(projection.evidence()).extracting(EvidenceProjection::evidenceId).containsExactly("evidence-1");
		assertThat(projection.commitments()).extracting(CommitmentProjection::commitmentId).containsExactly("commitment-1");
		assertThat(projection.outcomeVerifications()).extracting(OutcomeVerificationProjection::outcome).containsExactly("PERSISTENT");
		assertThat(projection.quality().missingContext()).containsExactly("optional source");
	}

	@Test
	void missingSelectionIsVisibleAndDoesNotFabricateContext() {
		var context = new AnalysisContext(null, "area-1", "unknown-service", null, null);
		var projection = useCase.serviceIntelligence(new ProjectionRequest(context, 10));

		assertThat(projection.service()).isNull();
		assertThat(projection.riskFindings()).isEmpty();
		assertThat(projection.quality().missingContext()).contains("selected Service unknown-service");
	}

	@Test
	void serviceAndRiskQueriesRequireTheirSelectedContext() {
		assertThatIllegalArgumentException().isThrownBy(() -> useCase.serviceIntelligence(
			new ProjectionRequest(AnalysisContext.overview(), 10)));
		assertThatIllegalArgumentException().isThrownBy(() -> useCase.riskInvestigation(
			new ProjectionRequest(AnalysisContext.overview(), 10)));
	}

	private static final class RecordingSource implements ExperienceProjectionSource {
		private final PreparedExperienceContext prepared = new PreparedExperienceContext(
			List.of(new AreaDomainProjection("area-1", "Platform", "ATTENTION"), new AreaDomainProjection("area-2", "Data", "STABLE")),
			List.of(new ServiceProjection("service-1", "Payments", "area-1", "degraded"), new ServiceProjection("service-2", "Catalog", "area-2", "stable")),
			List.of(new RiskFindingProjection("risk-1", "service-1", "persistent degradation", "Evidence-backed explanation", "evidence-1"),
				new RiskFindingProjection("risk-2", "service-2", "stale context", "Limited explanation", "evidence-2")),
			List.of(new EvidenceProjection("evidence-1", "service-1", "risk-1", "degradation", List.of("source-1"), "partial", null),
				new EvidenceProjection("evidence-2", "service-2", "risk-2", "stable", List.of("source-2"), null, null)),
			List.of(new CommitmentProjection("commitment-1", "risk-1", "address degradation", "OPEN")),
			List.of(new ImprovementActionProjection("action-1", "commitment-1", "repair dependency", "COMPLETED")),
			List.of(new OutcomeVerificationProjection("outcome-1", "action-1", "PERSISTENT", List.of("evidence-1"))),
			new ProjectionQuality("partial", "stale", "contextual", List.of("optional source"), List.of("INFERRED context"), List.of("projection lag"), true, true));
		private ProjectionRequest lastRequest;

		@Override
		public PreparedExperienceContext load(ProjectionRequest request) {
			lastRequest = request;
			return prepared;
		}
	}
}
