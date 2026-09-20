package com.vector.bff.experience;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class TemporalSignalProjectionTests {
    @Test
    void ordersEvidenceAndPreservesSemanticLimitationsWithoutInventingCausality() {
        var quality = new ProjectionQuality("test", "fixed", "confirmed", List.of(), List.of(), List.of(), false, false);
        var later = new EvidenceProjection("e2","svc","risk","incident recurred",List.of("source-incident"),"correlation only",Instant.parse("2025-01-02T00:00:00Z"));
        var earlier = new EvidenceProjection("e1","svc","risk","latency degraded",List.of("source-observability"),"local evidence",Instant.parse("2025-01-01T00:00:00Z"));
        ExperienceProjectionSource source = request -> new PreparedExperienceContext(List.of(),List.of(),List.of(),List.of(later,earlier),List.of(),List.of(),List.of(),quality);
        var useCase = new DefaultExperienceProjectionUseCase(source);
        var result = useCase.temporalSignals(new ProjectionRequest(new AnalysisContext("p",null,"svc","risk",null),20));
        assertThat(result).extracting(TemporalSignalProjection::signalId).containsExactly("e1","e2");
        assertThat(result).extracting(TemporalSignalProjection::semanticType).containsExactly("OPERATIONAL_SIGNAL","INCIDENT_EVIDENCE");
        assertThat(result.get(1).limitation()).isEqualTo("correlation only");
    }
}
