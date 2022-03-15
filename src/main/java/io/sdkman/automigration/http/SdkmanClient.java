package io.sdkman.automigration.http;

import io.sdkman.automigration.wire.out.VersionRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class SdkmanClient {

	private final RestTemplate restTemplate;

	private final Environment environment;

	public SdkmanClient(RestTemplate restTemplate, Environment environment) {
		this.restTemplate = restTemplate;
		this.environment = environment;
	}

	public boolean findVersion(String url, String version, String platform) {
		var response = this.restTemplate.getForEntity(url, Object.class, version, platform);
		return response.getStatusCode() == HttpStatus.FOUND;
	}

	public Optional<String> newVersion(String url, VersionRequest versionRequest) {
		var headers = new LinkedMultiValueMap<String, String>();
		headers.put(HttpHeaders.ACCEPT, List.of(MediaType.APPLICATION_JSON_VALUE));
		headers.put(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE));
		headers.put("Consumer-Key", List.of(this.environment.getProperty("sdkman.release.consumer-key")));
		headers.put("Consumer-Token", List.of(this.environment.getProperty("sdkman.release.consumer-token")));
		var request = new HttpEntity<>(versionRequest, headers);
		var response = this.restTemplate.postForEntity(url, request, String.class);
		if (response.getStatusCode() == HttpStatus.CREATED && response.hasBody()) {
			return Optional.of(response.getBody());
		}
		return Optional.empty();
	}

}
