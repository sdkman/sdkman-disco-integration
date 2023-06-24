package io.sdkman.automigration;

import io.sdkman.automigration.fixtures.FoojayResponse;
import io.sdkman.automigration.fixtures.SdkmanReleaseRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class SdkmanDiscoMigrationTest {

	private static final String TAR_GZ = "tar.gz";

	private static final String LINUX = "linux";

	private static final String AMD64 = "amd64";

	private static final String ARM_64 = "arm64";

	private static final String LINUX_64 = "linux64";

	private static final String LINUX_ARM64 = "linuxarm64";

	// @formatter:off
    private final String[] propertyValues = {
			"spring.test.webclient.mockrestserviceserver.enabled=true",
            "foojay.url=http://localhost/disco/v3.0",
            "sdkman.broker.url=http://localhost/2/broker/download/java/{version}/{platform}",
            "sdkman.release.url=http://localhost/release",
            "foojay.java.distribution=liberica",
			"foojay.distribution.version=18",
			"foojay.java.release-status=ga",
            "sdkman.liberica.linux[0].architecture=amd64",
            "sdkman.liberica.linux[0].archive-type=tar.gz"};
    // @formatter:on

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MockRestServiceServerAutoConfiguration.class))
			.withUserConfiguration(SdkmanDiscoMigration.class).withPropertyValues(this.propertyValues);

	@Test
	void testWithDistributionsAndSdkmanReleaseWithChecksum() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8", "sdkman.release.consumer-key=any-key",
				"sdkman.release.consumer-token=any-token", "sdkman.liberica.linux[1].architecture=arm64",
				"sdkman.liberica.linux[1].archive-type=tar.gz").run(context -> {
					var mockServer = context.getBean(MockRestServiceServer.class);
					var commandLineRunner = context.getBean(CommandLineRunner.class);
					foojayPackagesMockServer(mockServer, "liberica", "8", TAR_GZ, LINUX, AMD64,
							FoojayResponse.liberica80322amd64());
					sdkmanBrokerMockServer(mockServer, "8.0.322-librca", HttpStatus.NOT_FOUND, LINUX_64);
					foojayIdsMockServer(mockServer, FoojayResponse.idsResponseAmd64WithChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.candidateAmd64WithNoChecksum());

					foojayPackagesMockServer(mockServer, "liberica", "8", TAR_GZ, LINUX, ARM_64,
							FoojayResponse.liberica80322arm64());
					sdkmanBrokerMockServer(mockServer, "8.0.322-librca", HttpStatus.NOT_FOUND, LINUX_ARM64);
					foojayIdsMockServer(mockServer, FoojayResponse.idsResponseArm64WithChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.candidateArm64WithNoChecksum());
					commandLineRunner.run();
					mockServer.verify();
				});
	}

	@Test
	void testWithDistributionsAndSdkmanReleaseWithNoChecksum() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8", "sdkman.release.consumer-key=any-key",
				"sdkman.release.consumer-token=any-token").run(context -> {
					var mockServer = context.getBean(MockRestServiceServer.class);
					var commandLineRunner = context.getBean(CommandLineRunner.class);
					foojayPackagesMockServer(mockServer, "liberica", "8", TAR_GZ, LINUX, AMD64,
							FoojayResponse.liberica80322amd64());
					sdkmanBrokerMockServer(mockServer, "8.0.322-librca", HttpStatus.NOT_FOUND, LINUX_64);
					foojayIdsMockServer(mockServer, FoojayResponse.idsResponseWithNoChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.candidateAmd64WithNoChecksum());
					commandLineRunner.run();
					mockServer.verify();
				});
	}

	@Test
	void testWithEmptyDistributions() {
		this.contextRunner.run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "liberica", "18", TAR_GZ, LINUX, AMD64, FoojayResponse.empty());
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionFoundInSdkmanBroker() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "liberica", "8", TAR_GZ, LINUX, AMD64,
					FoojayResponse.liberica80322amd64());
			sdkmanBrokerMockServer(mockServer, "8.0.322-librca", HttpStatus.FOUND, LINUX_64);
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionIsNull() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "liberica", "8", TAR_GZ, LINUX, AMD64, FoojayResponse.liberica8());
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithGraalVmDistributionsAndSdkmanReleaseWithNoChecksum() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=22", "foojay.java.version=17",
				"foojay.java.distribution=liberica_native", "sdkman.liberica_native.linux[0].architecture=amd64",
				"sdkman.liberica_native.linux[0].archive-type=tar.gz", "sdkman.release.consumer-key=any-key",
				"sdkman.release.consumer-token=any-token").run(context -> {
					var mockServer = context.getBean(MockRestServiceServer.class);
					var commandLineRunner = context.getBean(CommandLineRunner.class);
					foojayPackagesMockServer(mockServer, "liberica", "22", TAR_GZ, LINUX, AMD64,
							FoojayResponse.libericaNik80322amd64());
					sdkmanBrokerMockServer(mockServer, "22.1.r17-nik", HttpStatus.NOT_FOUND, LINUX_64);
					foojayIdsMockServer(mockServer, FoojayResponse.libericaNikIdsResponseWithNoChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.libericaNikCandidateAmd64WithNoChecksum());
					commandLineRunner.run();
					mockServer.verify();
				});
	}

	@Test
	void shouldReleaseDefaultCandidate() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8", "sdkman.release.consumer-key=any-key",
				"sdkman.release.consumer-token=any-token", "sdkman.default.candidate=true").run(context -> {
					var mockServer = context.getBean(MockRestServiceServer.class);
					var commandLineRunner = context.getBean(CommandLineRunner.class);
					foojayPackagesMockServer(mockServer, "liberica", "8", TAR_GZ, LINUX, AMD64,
							FoojayResponse.liberica80322amd64());
					sdkmanBrokerMockServer(mockServer, "8.0.322-librca", HttpStatus.NOT_FOUND, LINUX_64);
					foojayIdsMockServer(mockServer, FoojayResponse.idsResponseWithNoChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.defaultCandidateAmd64WithNoChecksum());
					commandLineRunner.run();
					mockServer.verify();
				});
	}

	@Test
	void shouldReturnCandidateWhenFeaturesIsUsed() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=17", "sdkman.release.consumer-key=any-key",
				"sdkman.release.consumer-token=any-token", "foojay.java.distribution=zulu", "foojay.java.features=crac",
				"sdkman.zulu.crac.linux[0].architecture=amd64", "sdkman.zulu.crac.linux[0].archive-type=tar.gz")
				.run(context -> {
					var mockServer = context.getBean(MockRestServiceServer.class);
					var commandLineRunner = context.getBean(CommandLineRunner.class);
					foojayPackagesMockServer(mockServer, "zulu", "17", TAR_GZ, LINUX, AMD64,
							Files.readString(Path.of("src/test/resources/feature-zulu-crac-response.json")));
					sdkmanBrokerMockServer(mockServer, "17.0.7.crac-zulu", HttpStatus.NOT_FOUND, LINUX_64);
					foojayIdsMockServer(mockServer, FoojayResponse.zuluCracIdsResponse());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.zuluCracCandidate());
					commandLineRunner.run();
					mockServer.verify();
				});
	}

	private void sdkmanReleaseMockServer(MockRestServiceServer mockServer, String request) {
		// @formatter:off
		mockServer.expect(ExpectedCount.once(), requestTo("http://localhost/release"))
				.andExpect(content().json(request, true))
				.andExpect(header("Consumer-Key", "any-key"))
				.andExpect(header("Consumer-Token", "any-token"))
				.andExpect(header("Accept", "application/json"))
				.andExpect(header("Content-Type", "application/json"))
				.andRespond(withStatus(HttpStatus.OK));
		// @formatter:on
	}

	private void sdkmanBrokerMockServer(MockRestServiceServer mockServer, String version, HttpStatus httpStatus,
			String platform) {
		var url = "http://localhost/2/broker/download/java/" + version + "/" + platform;
		mockServer.expect(ExpectedCount.once(), requestTo(url)).andRespond(withStatus(httpStatus));
	}

	private void foojayIdsMockServer(MockRestServiceServer mockServer, String response) {
		mockServer
				.expect(ExpectedCount.once(),
						requestTo("https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99"))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response));
	}

	private void foojayPackagesMockServer(MockRestServiceServer mockServer, String distribution, String version,
			String archiveType, String os, String architecture, String response) {
		mockServer.expect(ExpectedCount.once(), request -> {
			var uriComponents = UriComponentsBuilder.fromUri(request.getURI()).build();
			var expectedQueryParams = uriComponents.getQueryParams().containsKey("jdk_version")
					? graalVmDistributionQueryParams(version, archiveType, os, architecture)
					: javaDistributionQueryParams(distribution, version, archiveType, os, architecture);
			assertThat(uriComponents.getPath()).isEqualTo("/disco/v3.0/packages");
			assertThat(uriComponents.getQueryParams()).containsAllEntriesOf(expectedQueryParams);

		}).andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response));
	}

	private static Map<String, List<String>> javaDistributionQueryParams(String distribution, String version,
			String archiveType, String os, String architecture) {
		// @formatter:off
		return Map.ofEntries(entry("distribution", List.of(distribution)),
				entry("javafx_bundled", List.of("false")),
				entry("directly_downloadable", List.of("true")),
				entry("libc_type", List.of("glibc", "c_std_lib", "libc")),
				entry("archive_type", List.of(archiveType)),
				entry("operating_system", List.of(os)),
				entry("package_type", List.of("jdk")),
				entry("release_status", List.of("ga")),
				entry("version", List.of(version)),
				entry("architecture", List.of(architecture)),
				entry("latest", List.of("available")));
		// @formatter:on
	}

	private static Map<String, List<String>> graalVmDistributionQueryParams(String version, String archiveType,
			String os, String architecture) {
		// @formatter:off
		return Map.ofEntries(entry("distribution", List.of("liberica_native")),
				entry("jdk_version", List.of("17")),
				entry("javafx_bundled", List.of("false")),
				entry("directly_downloadable", List.of("true")),
				entry("libc_type", List.of("glibc", "c_std_lib", "libc")),
				entry("archive_type", List.of(archiveType)),
				entry("operating_system", List.of(os)),
				entry("package_type", List.of("jdk")),
				entry("release_status", List.of("ga")),
				entry("version", List.of(version)),
				entry("architecture", List.of(architecture)),
				entry("latest", List.of("available")));
		// @formatter:on
	}

}
