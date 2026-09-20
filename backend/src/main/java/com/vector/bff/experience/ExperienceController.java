package com.vector.bff.experience;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vector.bff.security.AuthorizationService;
import com.vector.bff.security.LocalHttpSecurityContextResolver;
import com.vector.bff.security.SecurityAuditRecord;
import com.vector.bff.security.SecurityAuditRecorder;
import com.vector.bff.security.SecurityPermission;

import java.time.Instant;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {
    private final ExperienceProjectionUseCase useCase;
    private final CommitmentManagementUseCase commitmentManagement;
    private final AuthorizationService authorization;
    private final SecurityAuditRecorder audit;
    private final LocalHttpSecurityContextResolver securityContext;
    private final com.vector.bff.ai.AiInvestigationService aiInvestigation;
    private final LocalChangeAssociationExperience changeAssociation;

    public ExperienceController(ExperienceProjectionUseCase useCase, CommitmentManagementUseCase commitmentManagement,
            AuthorizationService authorization, SecurityAuditRecorder audit, LocalHttpSecurityContextResolver securityContext,
            com.vector.bff.ai.AiInvestigationService aiInvestigation, LocalChangeAssociationExperience changeAssociation) {
        this.useCase = useCase;
        this.commitmentManagement = commitmentManagement;
        this.authorization = authorization;
        this.audit = audit;
        this.securityContext = securityContext;
        this.aiInvestigation = aiInvestigation;
        this.changeAssociation = changeAssociation;
    }

    @GetMapping("/overview")
    TechnologyOverviewProjection overview(@RequestParam(required = false) String period,
            @RequestParam(required = false) String areaDomainId, @RequestParam(defaultValue = "20") int limit) {
        return useCase.technologyOverview(new ProjectionRequest(new AnalysisContext(period, areaDomainId, null, null, null), limit));
    }

    @GetMapping("/services/{serviceId}")
    ServiceIntelligenceProjection service(@PathVariable String serviceId, @RequestParam(required = false) String period,
            @RequestParam(required = false) String areaDomainId, @RequestParam(defaultValue = "20") int limit) {
        return useCase.serviceIntelligence(new ProjectionRequest(new AnalysisContext(period, areaDomainId, serviceId, null, null), limit));
    }

    @GetMapping("/risks/{riskFindingId}")
    RiskInvestigationProjection risk(@PathVariable String riskFindingId, @RequestParam(required = false) String period,
            @RequestParam(required = false) String areaDomainId, @RequestParam(required = false) String serviceId,
            @RequestParam(defaultValue = "20") int limit) {
        return useCase.riskInvestigation(new ProjectionRequest(new AnalysisContext(period, areaDomainId, serviceId, riskFindingId, null), limit));
    }




    @GetMapping("/risks/{riskFindingId}/change-association")
    ChangeAssociationExperienceProjection changeAssociation(@PathVariable String riskFindingId,
            @RequestParam String serviceId) {
        return changeAssociation.investigate(serviceId, riskFindingId);
    }

    @GetMapping("/risks/{riskFindingId}/assist")
    com.vector.bff.ai.AiProviderResult assist(@PathVariable String riskFindingId,
            @RequestParam(required = false) String period, @RequestParam(required = false) String areaDomainId,
            @RequestParam(required = false) String serviceId) {
        var request = new ProjectionRequest(new AnalysisContext(period, areaDomainId, serviceId, riskFindingId, null), 20);
        var risk = useCase.riskInvestigation(request);
        var evidenceIds = risk.evidence().stream().map(EvidenceProjection::evidenceId).toList();
        return aiInvestigation.investigate(new com.vector.bff.ai.AiRequest(
            "RISK_INVESTIGATION_ASSISTANCE", riskFindingId, evidenceIds, "EXT-003", true));
    }

    @GetMapping("/signals")
    java.util.List<TemporalSignalProjection> signals(@RequestParam(required = false) String period,
            @RequestParam(required = false) String areaDomainId, @RequestParam(required = false) String serviceId,
            @RequestParam(required = false) String riskFindingId, @RequestParam(defaultValue = "50") int limit) {
        return useCase.temporalSignals(new ProjectionRequest(
            new AnalysisContext(period, areaDomainId, serviceId, riskFindingId, null), limit));
    }

    @GetMapping("/commitments")
    CommitmentManagementProjection commitments(@RequestParam(required = false) String areaDomainId,
            @RequestParam(required = false) String serviceId, @RequestParam java.time.LocalDate asOf,
            @RequestParam(defaultValue = "20") int limit) {
        return commitmentManagement.list(areaDomainId, serviceId, asOf, limit);
    }

    @PostMapping("/commitments")
    ManagementCommitmentProjection createCommitment(@RequestBody CommitmentCreateRequest request,
            @RequestHeader(value = "X-Vector-Subject", required = false) String subject,
            @RequestHeader(value = "X-Vector-Role", required = false) String role) {
        var principal = securityContext.resolve(subject, role);
        try {
            authorization.require(principal, SecurityPermission.MUTATE_VECTOR);
            var result = commitmentManagement.create(request);
            audit.record(new SecurityAuditRecord("CREATE_COMMITMENT", Instant.now(), principal.subject(), true, "COMPLETED"));
            return result;
        } catch (RuntimeException deniedOrFailed) {
            audit.record(new SecurityAuditRecord("CREATE_COMMITMENT", Instant.now(),
                principal == null ? "anonymous" : principal.subject(), false, deniedOrFailed.getClass().getSimpleName()));
            throw deniedOrFailed;
        }
    }
}
