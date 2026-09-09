package com.vector.bff.experience;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {
    private final ExperienceProjectionUseCase useCase;
    private final CommitmentManagementUseCase commitmentManagement;

    public ExperienceController(ExperienceProjectionUseCase useCase, CommitmentManagementUseCase commitmentManagement) {
        this.useCase = useCase;
        this.commitmentManagement = commitmentManagement;
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

    @GetMapping("/commitments")
    CommitmentManagementProjection commitments(@RequestParam(required = false) String areaDomainId,
            @RequestParam(required = false) String serviceId, @RequestParam java.time.LocalDate asOf,
            @RequestParam(defaultValue = "20") int limit) {
        return commitmentManagement.list(areaDomainId, serviceId, asOf, limit);
    }

    @PostMapping("/commitments")
    ManagementCommitmentProjection createCommitment(@RequestBody CommitmentCreateRequest request) {
        return commitmentManagement.create(request);
    }
}
