package io.sdkman.automigration.config;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.HttpURLConnection;

@Configuration(proxyBeanMethods = false)
public class RestClientConfig {

	@Bean
	RestClient restClient(RestClient.Builder restClientBuilder) {
		return restClientBuilder.defaultStatusHandler(HttpStatusCode::is4xxClientError, (_, _) -> {
		}).build();
	}

	@Bean
	RestClientCustomizer restClientCustomizer() {
		return restClientBuilder -> restClientBuilder.requestFactory(new NoRedirectSimpleClientHttpRequestFactory());
	}

	static class NoRedirectSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

		@Override
		protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
			super.prepareConnection(connection, httpMethod);
			connection.setInstanceFollowRedirects(false);
		}

	}

}
