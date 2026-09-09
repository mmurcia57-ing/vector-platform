package com.vector.bff.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.vector.bff.seed.GoldenScenarioManifest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GoldenSemanticAcceptanceTests {
    @Test
    void everyGoldenScenarioIsTraceableToExecutableSemanticEvidence() throws Exception {
        var traceability = new LinkedHashMap<String, List<String>>();
        traceability.put("GS-01", List.of("com.vector.bff.intelligence.DeterministicIntelligenceServiceTests"));
        traceability.put("GS-02", List.of("com.vector.bff.journey.J01.PersistentReliabilityJourneyTests"));
        traceability.put("GS-03", List.of("com.vector.bff.journey.J02.ChangeAssociationJourneyTests"));
        traceability.put("GS-04", List.of("com.vector.bff.journey.J03.StructuralImprovementJourneyTests"));
        traceability.put("GS-05", List.of("com.vector.bff.journey.J03.StructuralImprovementJourneyTests"));
        traceability.put("GS-06", List.of("com.vector.bff.journey.J03.StructuralImprovementJourneyTests"));
        traceability.put("GS-07", List.of("com.vector.bff.intelligence.DeterministicIntelligenceServiceTests"));
        traceability.put("GS-08", List.of("com.vector.bff.evidence.EvidencePathTests"));
        traceability.put("GS-09", List.of("com.vector.bff.journey.J02.ChangeAssociationJourneyTests"));
        traceability.put("GS-10", List.of("com.vector.bff.journey.J02.ChangeAssociationJourneyTests"));
        traceability.put("GS-11", List.of("com.vector.bff.graph.GraphProjectionRecoveryTests"));
        traceability.put("GS-12", List.of("com.vector.bff.experience.ExperienceProjectionUseCaseTests"));

        var manifest = GoldenScenarioManifest.approved();
        assertThat(traceability.keySet()).containsExactlyElementsOf(
            manifest.scenarios().stream().map(GoldenScenarioManifest.ScenarioDescriptor::id).toList());
        for (Map.Entry<String, List<String>> entry : traceability.entrySet()) {
            assertThat(entry.getValue()).as("executable evidence for %s", entry.getKey()).isNotEmpty();
            for (String testClass : entry.getValue()) assertThat(Class.forName(testClass)).isNotNull();
        }
    }

    @Test
    void goldenLimitationsRetainTheRequiredSemanticNegatives() {
        var limitations = GoldenScenarioManifest.approved().scenarios().stream()
            .collect(java.util.stream.Collectors.toMap(GoldenScenarioManifest.ScenarioDescriptor::id,
                GoldenScenarioManifest.ScenarioDescriptor::limitation));

        assertThat(limitations.get("GS-03")).contains("Correlation != Causation");
        assertThat(limitations.get("GS-04")).contains("execution != outcome");
        assertThat(limitations.get("GS-05")).contains("completion is not improvement");
        assertThat(limitations.get("GS-07")).contains("missing telemetry != healthy");
        assertThat(limitations.get("GS-08")).contains("no silent authority selection");
        assertThat(limitations.get("GS-09")).contains("Identity != Correlation");
        assertThat(limitations.get("GS-12")).contains("no individual ranking or causal claim");
    }
}
