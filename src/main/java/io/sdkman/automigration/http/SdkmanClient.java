package io.sdkman.automigration.http;

import io.sdkman.automigration.properties.SdkmanApiProperties;
import io.sdkman.automigration.wire.out.VersionRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Optional;

@Component
public class SdkmanClient {

	private final RestClient restClient;

	private final SdkmanApiProperties sdkmanProperties;

	private final Environment environment;

	public SdkmanClient(RestClient restClient, SdkmanApiProperties sdkmanProperties, Environment environment) {
		this.restClient = restClient;
		this.sdkmanProperties = sdkmanProperties;
		this.environment = environment;
	}

	public boolean findVersion(String version, String platform) {
		var response = this.restClient.get()
			.uri(this.sdkmanProperties.broker().url(), Map.of("version", version, "platform", platform))
			.retrieve()
			.toBodilessEntity();
		return response.getStatusCode() == HttpStatus.FOUND;
	}

	public Optional<String> newVersion(VersionRequest versionRequest) {
		var response = this.restClient.post()
			.uri(this.sdkmanProperties.release().url())
			.body(versionRequest)
			.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.header("Consumer-Key", this.environment.getProperty("sdkman.release.consumer-key"))
			.header("Consumer-Token", this.environment.getProperty("sdkman.release.consumer-token"))
			.retrieve()
			.toEntity(String.class);
		if (response.getStatusCode() == HttpStatus.CREATED && response.hasBody()) {
			return Optional.of(response.getBody());
		}
		return Optional.empty();
	}

}
