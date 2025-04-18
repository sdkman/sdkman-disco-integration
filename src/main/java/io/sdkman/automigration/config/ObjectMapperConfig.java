package io.sdkman.automigration.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Configuration(proxyBeanMethods = false)
public class ObjectMapperConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer objectMapperBuilderCustomizer() {
		return builder -> builder.serializationInclusion(Include.NON_NULL);
	}

}
