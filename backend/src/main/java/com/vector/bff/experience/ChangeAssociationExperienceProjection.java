package com.vector.bff.experience;

import java.util.List;

public record ChangeAssociationExperienceProjection(
    String serviceId, String riskFindingId, String changeId, String deploymentId,
    String temporalContext, boolean contextualAssociation, boolean causalClaim,
    List<String> evidenceIds, String limitation) {
    public ChangeAssociationExperienceProjection {
        evidenceIds = List.copyOf(evidenceIds == null ? List.of() : evidenceIds);
    }
}
