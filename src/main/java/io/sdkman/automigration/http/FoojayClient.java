package io.sdkman.automigration.http;

import io.sdkman.automigration.wire.in.ResultIdsResponse;
import io.sdkman.automigration.wire.in.ResultPackageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
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

	private final RestTemplate restTemplate;

	public FoojayClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Optional<ResultPackageResponse> queryPackages(String url, Map<String, List<String>> queryParams) {
		var packageUrl = url.concat("/packages");
		var newUrl = UriComponentsBuilder.fromHttpUrl(packageUrl)
				.queryParams(CollectionUtils.toMultiValueMap(queryParams)).encode().toUriString();
		var response = this.restTemplate.getForEntity(newUrl, ResultPackageResponse.class);
		if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
			return Optional.of(response.getBody());
		}
		return Optional.empty();
	}

	public Optional<ResultIdsResponse> queryUrl(String url) {
		var response = this.restTemplate.getForEntity(url, ResultIdsResponse.class);
		if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
			return Optional.of(response.getBody());
		}
		return Optional.empty();
	}

}
