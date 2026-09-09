package com.vector.bff.experience;

import com.vector.bff.canonical.CanonicalEntity;
import com.vector.bff.canonical.Commitment;
import com.vector.bff.persistence.CanonicalRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DefaultCommitmentManagementUseCase implements CommitmentManagementUseCase {
    private final ExperienceProjectionSource source;
    private final CanonicalRepository repository;
    private final List<Commitment> created = new ArrayList<>();

    public DefaultCommitmentManagementUseCase(ExperienceProjectionSource source, CanonicalRepository repository) {
        this.source = Objects.requireNonNull(source, "source is required");
        this.repository = Objects.requireNonNull(repository, "repository is required");
    }

    @Override
    public CommitmentManagementProjection list(String areaDomainId, String serviceId, LocalDate asOf, int limit) {
        if (asOf == null) throw new IllegalArgumentException("asOf is required");
        var prepared = source.load(new ProjectionRequest(new AnalysisContext(null, areaDomainId, serviceId, null, null), limit));
        var values = new ArrayList<ManagementCommitmentProjection>();
        prepared.commitments().stream()
            .filter(item -> areaDomainId == null || areaDomainId.equals(areaFor(item, prepared)))
            .filter(item -> serviceId == null || serviceId.equals(serviceFor(item, prepared)))
            .map(item -> new ManagementCommitmentProjection(item.commitmentId(), item.declaration(), areaFor(item, prepared),
                null, null, item.statusContext(), false, item.riskFindingId(), serviceFor(item, prepared), null, "VECTOR-native or source provenance retained"))
            .forEach(values::add);
        created.stream()
            .filter(item -> areaDomainId == null || areaDomainId.equals(item.accountableAreaDomainId()))
            .filter(item -> serviceId == null || serviceId.equals(item.serviceId()))
            .map(item -> toProjection(item, asOf))
            .forEach(values::add);
        var bounded = values.size() <= limit ? values : values.subList(0, Math.max(0, limit));
        var active = values.stream().filter(item -> !"COMPLETED".equalsIgnoreCase(item.executionStatus())).count();
        var inProgress = values.stream().filter(item -> "IN_PROGRESS".equalsIgnoreCase(item.executionStatus())).count();
        var completed = values.stream().filter(item -> "COMPLETED".equalsIgnoreCase(item.executionStatus())).count();
        var overdue = values.stream().filter(ManagementCommitmentProjection::overdue).count();
        return new CommitmentManagementProjection(asOf, areaDomainId, serviceId, bounded, active, inProgress, completed, overdue, prepared.quality());
    }

    @Override
    public ManagementCommitmentProjection create(CommitmentCreateRequest request) {
        require(request.commitmentId(), "commitmentId");
        require(request.declaration(), "declaration");
        require(request.accountableAreaDomainId(), "accountableAreaDomainId");
        var commitment = new Commitment(newLocalMetadata(request.commitmentId()), request.declaration(), request.accountableAreaDomainId(),
            request.responsibleParty(), request.dueDate(), request.executionStatus(), request.intendedResult(), request.serviceId(),
            request.configurationItemId(), request.riskFindingId());
        repository.save(commitment);
        created.add(commitment);
        return toProjection(commitment, request.dueDate() == null ? LocalDate.of(9999, 12, 31) : request.dueDate());
    }

    private ManagementCommitmentProjection toProjection(Commitment item, LocalDate asOf) {
        return new ManagementCommitmentProjection(item.metadata().canonicalId(), item.declaration(), item.accountableAreaDomainId(),
            item.responsibleParty(), item.dueDate(), item.executionStatus(), item.overdueOn(asOf), item.riskFindingId(),
            item.serviceId(), item.configurationItemId(), String.join(",", item.metadata().provenance().sourceReferenceIds()));
    }

    private static String areaFor(CommitmentProjection item, PreparedExperienceContext prepared) {
        return prepared.areas().isEmpty() ? null : prepared.areas().get(0).areaDomainId();
    }

    private static String serviceFor(CommitmentProjection item, PreparedExperienceContext prepared) {
        return prepared.riskFindings().stream().filter(risk -> item.riskFindingId().equals(risk.riskFindingId()))
            .map(RiskFindingProjection::serviceId).findFirst().orElse(null);
    }

    private static void require(String value, String name) { if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " is required"); }

    private static com.vector.bff.canonical.CanonicalMetadata newLocalMetadata(String id) {
        return new com.vector.bff.canonical.CanonicalMetadata(id, com.vector.bff.canonical.IdentityResolutionState.CONFIRMED,
            com.vector.bff.canonical.TemporalSemantics.empty(), com.vector.bff.canonical.Provenance.nativeOrUnspecified(),
            new com.vector.bff.canonical.SourceAuthority("VECTOR-native", true));
    }
}


