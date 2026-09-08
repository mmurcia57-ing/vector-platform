package com.vector.bff.configuration;

import com.vector.bff.VectorBffApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class VectorRuntimePropertiesTests {
	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
		.withUserConfiguration(VectorBffApplication.class);

	@Test
	void usesTheSafeLocalDefaultWhenNoOverrideIsProvided() {
		contextRunner.run(context -> assertThat(context.getBean(VectorRuntimeProperties.class).environment())
			.isEqualTo("local"));
	}

	@Test
	void acceptsAnExternalConfigurationOverride() {
		contextRunner.withPropertyValues("vector.runtime.environment=validation")
			.run(context -> assertThat(context.getBean(VectorRuntimeProperties.class).environment())
				.isEqualTo("validation"));
	}

	@Test
	void rejectsAnExplicitBlankEnvironmentValue() {
		contextRunner.withPropertyValues("vector.runtime.environment=")
			.run(context -> assertThat(context).hasFailed());
	}

	@Test
	void acceptsExternalReliabilityPolicyConfiguration() {
		contextRunner.withPropertyValues(
			"vector.reliability.policy.reference=validated-policy",
			"vector.reliability.policy.minimum-recurring-incidents=2",
			"vector.reliability.policy.minimum-supporting-evidence=2")
			.run(context -> {
				var policy = context.getBean(VectorReliabilityPolicyProperties.class);
				assertThat(policy.reference()).isEqualTo("validated-policy");
				assertThat(policy.minimumRecurringIncidents()).isEqualTo(2);
				assertThat(policy.minimumSupportingEvidence()).isEqualTo(2);
			});
	}

	@Test
	void rejectsUnsafeNonPositiveReliabilityPolicyValues() {
		contextRunner.withPropertyValues("vector.reliability.policy.minimum-recurring-incidents=0")
			.run(context -> assertThat(context).hasFailed());
	}

	@Test
	void configurationObjectDoesNotContainSecretFieldsOrValues() {
		contextRunner.withPropertyValues("vector.runtime.environment=local")
			.run(context -> assertThat(context.getBean(VectorRuntimeProperties.class).toString())
				.doesNotContainIgnoringCase("password", "secret", "token", "credential"));
	}
}
