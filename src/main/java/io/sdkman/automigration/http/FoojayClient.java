package io.sdkman.automigration.http;

import io.sdkman.automigration.properties.FoojayApiProperties;
import io.sdkman.automigration.wire.in.ResultIdsResponse;
import io.sdkman.automigration.wire.in.ResultPackageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FoojayClient {

	// @formatter:off
    public static final Map<String, List<String>> defaultQueryParams =
            Map.of("package_type", List.of("jdk"),
                    "libc_type", List.of("glibc", "c_std_lib", "libc"),
                    "directly_downloadable", List.of("true"),
                    "latest", List.of("available"));
	// @formatter:on

	private final RestClient restClient;

	private final FoojayApiProperties properties;

	public FoojayClient(RestClient restClient, FoojayApiProperties properties) {
		this.restClient = restClient;
		this.properties = properties;
	}

	public Optional<ResultPackageResponse> queryPackages(Map<String, List<String>> queryParams) {
		var newUrl = UriComponentsBuilder.fromUriString("/packages")
			.queryParams(CollectionUtils.toMultiValueMap(queryParams))
			.encode()
			.toUriString();
		var response = this.restClient.mutate()
			.baseUrl(properties.url())
			.build()
			.get()
			.uri(newUrl)
			.retrieve()
			.toEntity(ResultPackageResponse.class);
		if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
			return Optional.of(response.getBody());
		}
		return Optional.empty();
	}

	public Optional<ResultIdsResponse> queryUrl(String url) {
		var response = this.restClient.mutate().baseUrl(url).build().get().retrieve().toEntity(ResultIdsResponse.class);
		if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
			return Optional.of(response.getBody());
		}
		return Optional.empty();
	}

}
