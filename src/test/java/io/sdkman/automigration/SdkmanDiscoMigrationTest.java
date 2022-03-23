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

	// @formatter:off
    private final String[] propertyValues = {"spring.test.webclient.mockrestserviceserver.enabled=true",
            "foojay.url=http://localhost/disco/v3.0",
            "sdkman.broker.url=http://localhost/2/broker/download/java/{version}/{platform}",
            "sdkman.release.url=http://localhost/release",
            "foojay.java.distribution=liberica",
            "spring.profiles.active=liberica18linuxamd64",
            "sdkman.liberica.version=18",
            "sdkman.liberica.architecture=amd64",
            "sdkman.liberica.release-status=ga",
            "sdkman.liberica.archive-type=tar.gz",
            "sdkman.liberica.operating-system=linux",
			"sdkman.liberica.filename-exclusions=bellsoft-jdk.+.-linux-amd64-lite.tar.gz"};
    // @formatter:on

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MockRestServiceServerAutoConfiguration.class))
			.withUserConfiguration(SdkmanDiscoMigration.class).withPropertyValues(this.propertyValues);

	@Test
	void testWithDistributionsAndSdkmanReleaseWithChecksum() {
		this.contextRunner.withPropertyValues("sdkman.liberica.version=8", "sdkman.release.consumer-key=any-key",
				"sdkman.release.consumer-token=any-token").run(context -> {
					var mockServer = context.getBean(MockRestServiceServer.class);
					var commandLineRunner = context.getBean(CommandLineRunner.class);
					foojayPackagesMockServer(mockServer, "8", FoojayResponse.liberica80322());
					sdkmanBrokerMockServer(mockServer, HttpStatus.NOT_FOUND);
					foojayIdsMockServer(mockServer, FoojayResponse.idsResponseWithChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.candidateWithNoChecksum());
					commandLineRunner.run();
					mockServer.verify();
				});
	}

	@Test
	void testWithDistributionsAndSdkmanReleaseWithNoChecksum() {
		this.contextRunner.withPropertyValues("sdkman.liberica.version=8", "sdkman.release.consumer-key=any-key",
				"sdkman.release.consumer-token=any-token").run(context -> {
					var mockServer = context.getBean(MockRestServiceServer.class);
					var commandLineRunner = context.getBean(CommandLineRunner.class);
					foojayPackagesMockServer(mockServer, "8", FoojayResponse.liberica80322());
					sdkmanBrokerMockServer(mockServer, HttpStatus.NOT_FOUND);
					foojayIdsMockServer(mockServer, FoojayResponse.idsResponseWithNoChecksum());
					sdkmanReleaseMockServer(mockServer, SdkmanReleaseRequest.candidateWithNoChecksum());
					commandLineRunner.run();
					mockServer.verify();
				});
	}

	@Test
	void testWithEmptyDistributions() {
		this.contextRunner.run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "18", FoojayResponse.empty());
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionFoundInSdkmanBroker() {
		this.contextRunner.withPropertyValues("sdkman.liberica.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "8", FoojayResponse.liberica80322());
			sdkmanBrokerMockServer(mockServer, HttpStatus.FOUND);
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionIsNull() {
		this.contextRunner.withPropertyValues("sdkman.liberica.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "8", FoojayResponse.liberica8());
			commandLineRunner.run();
			mockServer.verify();
		});
	}

	@Test
	void testWithDistributionsAndVersionIsGreaterThanExpectedSize() {
		this.contextRunner.withPropertyValues("sdkman.liberica.version=8").run(context -> {
			var mockServer = context.getBean(MockRestServiceServer.class);
			var commandLineRunner = context.getBean(CommandLineRunner.class);
			foojayPackagesMockServer(mockServer, "8", FoojayResponse.libericaLongerJavaVersion());
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

	private void sdkmanBrokerMockServer(MockRestServiceServer mockServer, HttpStatus httpStatus) {
		mockServer
				.expect(ExpectedCount.once(),
						requestTo("http://localhost/2/broker/download/java/8.0.322-librca/linux64"))
				.andRespond(withStatus(httpStatus));
	}

	private void foojayIdsMockServer(MockRestServiceServer mockServer, String response) {
		mockServer
				.expect(ExpectedCount.once(),
						requestTo("https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99"))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response));
	}

	private void foojayPackagesMockServer(MockRestServiceServer mockServer, String version, String response) {
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
                            .containsEntry("archive_type", List.of("tar.gz"))
                            .containsEntry("operating_system", List.of("linux"))
                            .containsEntry("package_type", List.of("jdk"))
                            .containsEntry("release_status", List.of("ga"))
                            .containsEntry("version", List.of(version))
                            .containsEntry("architecture", List.of("amd64"))
                            .containsEntry("latest", List.of("per_version"));
                    // @formatter:on
		}).andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response));
	}

}
