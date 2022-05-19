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

import java.util.List;

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
					foojayPackagesMockServer(mockServer, "8", TAR_GZ, LINUX, AMD64,
							FoojayResponse.liberica80322amd64());
					sdkmanBrokerMockServer(mockServer, HttpStatus.NOT_FOUND, LINUX_64);
					foojayIdsMockServer(mockServer, FoojayResponse.idsResponseAmd64WithChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.candidateAmd64WithNoChecksum());

					foojayPackagesMockServer(mockServer, "8", TAR_GZ, LINUX, ARM_64,
							FoojayResponse.liberica80322arm64());
					sdkmanBrokerMockServer(mockServer, HttpStatus.NOT_FOUND, LINUX_ARM64);
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
					foojayPackagesMockServer(mockServer, "8", TAR_GZ, LINUX, AMD64,
							FoojayResponse.liberica80322amd64());
					sdkmanBrokerMockServer(mockServer, HttpStatus.NOT_FOUND, LINUX_64);
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
			foojayPackagesMockServer(mockServer, "18", TAR_GZ, LINUX, AMD64, FoojayResponse.empty());
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionFoundInSdkmanBroker() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "8", TAR_GZ, LINUX, AMD64, FoojayResponse.liberica80322amd64());
			sdkmanBrokerMockServer(mockServer, HttpStatus.FOUND, LINUX_64);
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionIsNull() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "8", TAR_GZ, LINUX, AMD64, FoojayResponse.liberica8());
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionIsGreaterThanExpectedSize() {
		this.contextRunner.withPropertyValues("foojay.distribution.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "8", TAR_GZ, LINUX, AMD64, FoojayResponse.libericaLongerJavaVersion());
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	private void sdkmanReleaseMockServer(MockRestServiceServer mockServer, String request) {
		// @formatter:off
		mockServer.expect(ExpectedCount.once(), requestTo("http://localhost/release"))
				.andExpect(content().json(request))
				.andExpect(header("Consumer-Key", "any-key"))
				.andExpect(header("Consumer-Token", "any-token"))
				.andExpect(header("Accept", "application/json"))
				.andExpect(header("Content-Type", "application/json"))
				.andRespond(withStatus(HttpStatus.OK));
		// @formatter:on
	}

	private void sdkmanBrokerMockServer(MockRestServiceServer mockServer, HttpStatus httpStatus, String platform) {
		var url = "http://localhost/2/broker/download/java/8.0.322-librca/" + platform;
		mockServer.expect(ExpectedCount.once(), requestTo(url)).andRespond(withStatus(httpStatus));
	}

	private void foojayIdsMockServer(MockRestServiceServer mockServer, String response) {
		mockServer
				.expect(ExpectedCount.once(),
						requestTo("https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99"))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response));
	}

	private void foojayPackagesMockServer(MockRestServiceServer mockServer, String version, String archiveType,
			String os, String architecture, String response) {
		mockServer.expect(ExpectedCount.once(), request -> {
			var uriComponents = UriComponentsBuilder.fromUri(request.getURI()).build();
			assertThat(uriComponents.getPath()).isEqualTo("/disco/v3.0/packages");
			// @formatter:off
                    assertThat(uriComponents.getQueryParams())
                            .containsEntry("distribution", List.of("liberica"))
                            .containsEntry("bitness", List.of("64"))
                            .containsEntry("javafx_bundled", List.of("false"))
                            .containsEntry("directly_downloadable", List.of("true"))
                            .containsEntry("libc_type", List.of("glibc", "c_std_lib", "libc"))
                            .containsEntry("archive_type", List.of(archiveType))
                            .containsEntry("operating_system", List.of(os))
                            .containsEntry("package_type", List.of("jdk"))
                            .containsEntry("release_status", List.of("ga"))
                            .containsEntry("version", List.of(version))
                            .containsEntry("architecture", List.of(architecture))
                            .containsEntry("latest", List.of("available"));
                    // @formatter:on
		}).andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response));
	}

}
