package io.sdkman.automigration.adapters;

import io.sdkman.automigration.config.ObjectMapperConfig;
import io.sdkman.automigration.fixtures.SdkmanReleaseRequest;
import io.sdkman.automigration.wire.in.IdsResponse;
import io.sdkman.automigration.wire.out.Platform;
import io.sdkman.automigration.wire.out.VersionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@Import(ObjectMapperConfig.class)
class VersionAdapterTest {

	@Autowired
	private JacksonTester<VersionRequest> json;

	@Test
	void versionRequestWithChecksum() throws IOException {
		var idsResponse = new IdsResponse(
				"https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				"813eb415bd91e5dcb846fea4ffcf07befb254f5a", "sha1");
		var request = VersionAdapter.toVersionRequest("librca", "8.0.322", Platform.LINUX_64, idsResponse, null);
		assertThat(this.json.write(request)).isStrictlyEqualToJson(SdkmanReleaseRequest.candidateAmd64WithNoChecksum());
	}

	@Test
	void versionRequestWithNoChecksum() throws IOException {
		var idsResponse = new IdsResponse(
				"https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				null, "sha1");
		var request = VersionAdapter.toVersionRequest("librca", "8.0.322", Platform.LINUX_64, idsResponse, null);
		assertThat(this.json.write(request)).isStrictlyEqualToJson(SdkmanReleaseRequest.candidateAmd64WithNoChecksum());
	}

	@Test
	void versionRequestWithDefaultCandidate() throws IOException {
		var idsResponse = new IdsResponse(
				"https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				"813eb415bd91e5dcb846fea4ffcf07befb254f5a", "sha1");
		var request = VersionAdapter.toVersionRequest("librca", "8.0.322", Platform.LINUX_64, idsResponse, true);
		assertThat(this.json.write(request))
			.isStrictlyEqualToJson(SdkmanReleaseRequest.defaultCandidateAmd64WithNoChecksum());
	}

}
