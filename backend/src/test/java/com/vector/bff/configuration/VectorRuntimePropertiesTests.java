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
}
