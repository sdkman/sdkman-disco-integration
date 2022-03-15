package io.sdkman.automigration.logic;

import io.sdkman.automigration.wire.in.PackageResponse;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

	@Test
	void testDefault() {
		var packageResponse = new PackageResponse("corretto", "x64", "11.0.14.1+1", "linux", "filename.zip", "ga",
				false, null);
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testLiberica() {
		var packageResponse = new PackageResponse("liberica", "x64", "11.0.14.1+1", "linux", "filename.zip", "ga",
				false, null);
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testLibericaFX() {
		var packageResponse = new PackageResponse("liberica", "x64", "11.0.14.1+1", "linux", "filename.zip", "ga", true,
				null);
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1.fx"));
	}

	@Test
	void testOpen() {
		var packageResponse = new PackageResponse("oracle_open_jdk", "x64", "11.0.14.1", "linux", "filename.zip", "ga",
				false, null);
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testOpenEa() {
		var packageResponse = new PackageResponse("oracle_open_jdk", "x64", "18-ea+3", "linux", "filename.zip", "ea",
				false, null);
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("18.ea.3"));
	}

	@Test
	void testZulu() {
		var packageResponse = new PackageResponse("zulu", "x64", "11.0.14.1+1", "linux", "filename.zip", "ga", false,
				null);
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testZuluFx() {
		var packageResponse = new PackageResponse("zulu", "x64", "11.0.14.1+1", "linux", "filename.zip", "ga", true,
				null);
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1.fx"));
	}

}
