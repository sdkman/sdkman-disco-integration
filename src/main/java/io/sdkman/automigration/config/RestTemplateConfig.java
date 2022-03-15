package io.sdkman.automigration.config;

import io.sdkman.automigration.infrastructure.NoRedirectSimpleClientHttpRequestFactory;
import io.sdkman.automigration.infrastructure.ServerResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.requestFactory(NoRedirectSimpleClientHttpRequestFactory.class)
				.errorHandler(new ServerResponseErrorHandler()).build();
	}

}
