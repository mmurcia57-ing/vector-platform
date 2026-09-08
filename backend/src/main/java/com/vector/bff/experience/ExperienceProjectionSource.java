package com.vector.bff.experience;

public interface ExperienceProjectionSource {
	PreparedExperienceContext load(ProjectionRequest request);
}
