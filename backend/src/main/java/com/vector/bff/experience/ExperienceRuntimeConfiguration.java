package com.vector.bff.experience;

import com.vector.bff.evidence.EvidencePath;
import com.vector.bff.evidence.SqliteEvidencePath;
import com.vector.bff.persistence.CanonicalRepository;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import com.vector.bff.configuration.VectorReliabilityPolicyProperties;
import com.vector.bff.graph.BoundedGraphQueryService;
import com.vector.bff.graph.GraphProjectionStore;
import com.vector.bff.graph.GraphNode;
import com.vector.bff.graph.GraphProjectionEvent;
import com.vector.bff.graph.GraphRelationship;
import com.vector.bff.graph.SqliteGraphProjectionStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;

@Configuration
public class ExperienceRuntimeConfiguration {
    @Bean
    CanonicalRepository canonicalRepository() {
        return new SqliteCanonicalRepository("jdbc:sqlite:file:vector-experience?mode=memory&cache=shared");
    }

    @Bean
    EvidencePath evidencePath(CanonicalRepository repository) {
        return new SqliteEvidencePath(repository);
    }

    @Bean
    ExperienceProjectionSource experienceProjectionSource(CanonicalRepository repository, EvidencePath evidencePath,
            VectorReliabilityPolicyProperties policy) {
        return new LocalExperienceProjectionSource(repository, evidencePath, policy);
    }

    @Bean
    ExperienceProjectionUseCase experienceProjectionUseCase(ExperienceProjectionSource source) {
        return new DefaultExperienceProjectionUseCase(source);
    }

    @Bean
    CommitmentLifecycleStore commitmentLifecycleStore() {
        return new SqliteCommitmentLifecycleStore("jdbc:sqlite:vector-commitments.db");
    }

    @Bean
    CommitmentManagementUseCase commitmentManagementUseCase(ExperienceProjectionSource source, CanonicalRepository repository,
            CommitmentLifecycleStore lifecycle) {
        return new DefaultCommitmentManagementUseCase(source, repository, lifecycle);
    }




    @Bean
    LocalChangeAssociationExperience localChangeAssociationExperience(CanonicalRepository repository) {
        return new LocalChangeAssociationExperience(repository);
    }

    @Bean
    com.vector.bff.ai.AiProvider aiProvider() {
        return request -> com.vector.bff.ai.AiProviderResult.unavailable("No corporate AI provider is configured in the local runtime");
    }

    @Bean
    com.vector.bff.ai.AiInvestigationService aiInvestigationService(com.vector.bff.ai.AiProvider provider) {
        return new com.vector.bff.ai.AiInvestigationService(provider);
    }

    @Bean
    com.vector.bff.security.AuthorizationService authorizationService() {
        return new com.vector.bff.security.AuthorizationService();
    }

    @Bean
    com.vector.bff.security.SecurityAuditRecorder securityAuditRecorder() {
        return new com.vector.bff.security.InMemorySecurityAuditRecorder();
    }

    @Bean
    com.vector.bff.security.LocalHttpSecurityContextResolver localHttpSecurityContextResolver() {
        return new com.vector.bff.security.LocalHttpSecurityContextResolver();
    }

    @Bean
    GraphProjectionStore graphProjectionStore() {
        var store = new SqliteGraphProjectionStore("jdbc:sqlite:vector-graph.db");
        var area = new GraphNode("AreaDomain", "area-platform");
        var service = new GraphNode("Service", "service-payments");
        var finding = new GraphNode("RiskFinding", "risk-finding:service-payments:2025-01-01T00:00:00Z");
        var evidenceOne = new GraphNode("Evidence", "evidence-latency");
        var evidenceTwo = new GraphNode("Evidence", "evidence-incident");
        var commitment = new GraphNode("Commitment", "commitment-payments");
        var action = new GraphNode("ImprovementAction", "action-payments");
        store.apply(new GraphProjectionEvent("local-dataset-v1", List.of(area, service, finding, evidenceOne,
                evidenceTwo, commitment, action), List.of(
                relation(area, "CONTEXTUALIZES_SERVICE", service),
                relation(finding, "CONCERNS", service, List.of("evidence-latency", "evidence-incident")),
                relation(evidenceOne, "SUPPORTS", finding, List.of("evidence-latency")),
                relation(evidenceTwo, "SUPPORTS", finding, List.of("evidence-incident")),
                relation(finding, "IS_ADDRESSED_BY", commitment),
                relation(commitment, "IS_ADVANCED_BY", action)), Instant.parse("2025-01-01T00:00:00Z")));
        return store;
    }

    private static GraphRelationship relation(GraphNode source, String predicate, GraphNode target) {
        return relation(source, predicate, target, List.of());
    }

    private static GraphRelationship relation(GraphNode source, String predicate, GraphNode target, List<String> evidenceIds) {
        return new GraphRelationship(source, predicate, target, evidenceIds, List.of("local-dataset-v1"));
    }

    @Bean
    BoundedGraphQueryService boundedGraphQueryService(GraphProjectionStore store) {
        return new BoundedGraphQueryService(store, "local projection");
    }
}
