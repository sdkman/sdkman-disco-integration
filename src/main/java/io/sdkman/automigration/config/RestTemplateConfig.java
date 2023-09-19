package io.sdkman.automigration.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.requestFactory(NoRedirectSimpleClientHttpRequestFactory.class)
			.errorHandler(new ServerResponseErrorHandler())
			.build();
	}

	static class NoRedirectSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

		@Override
		protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
			super.prepareConnection(connection, httpMethod);
			connection.setInstanceFollowRedirects(false);
		}

	}

	static class ServerResponseErrorHandler extends DefaultResponseErrorHandler {

		@Override
		protected boolean hasError(int unknownStatusCode) {
			HttpStatus.Series series = HttpStatus.Series.resolve(unknownStatusCode);
			return series == HttpStatus.Series.SERVER_ERROR;
		}

		@Override
		protected boolean hasError(HttpStatusCode statusCode) {
			return statusCode.is5xxServerError();
		}

	}

}
