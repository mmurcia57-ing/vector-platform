package com.vector.bff.experience;

import com.vector.bff.evidence.EvidencePath;
import com.vector.bff.evidence.SqliteEvidencePath;
import com.vector.bff.persistence.CanonicalRepository;
import com.vector.bff.persistence.SqliteCanonicalRepository;
import com.vector.bff.graph.BoundedGraphQueryService;
import com.vector.bff.graph.GraphProjectionStore;
import com.vector.bff.graph.InMemoryGraphProjectionStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    ExperienceProjectionSource experienceProjectionSource(CanonicalRepository repository, EvidencePath evidencePath) {
        return new LocalExperienceProjectionSource(repository, evidencePath);
    }

    @Bean
    ExperienceProjectionUseCase experienceProjectionUseCase(ExperienceProjectionSource source) {
        return new DefaultExperienceProjectionUseCase(source);
    }

    @Bean
    GraphProjectionStore graphProjectionStore() { return new InMemoryGraphProjectionStore(); }

    @Bean
    BoundedGraphQueryService boundedGraphQueryService(GraphProjectionStore store) {
        return new BoundedGraphQueryService(store, "local projection");
    }
}
