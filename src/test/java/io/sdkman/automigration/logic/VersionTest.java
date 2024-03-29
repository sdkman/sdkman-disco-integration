package io.sdkman.automigration.logic;

import io.sdkman.automigration.wire.in.Feature;
import io.sdkman.automigration.wire.in.PackageResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

	@Test
	void testDefault() {
		var packageResponse = new PackageResponse("corretto", "x64", "11.0.14.1+1", null, "linux", "filename.zip", "ga",
				false, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testLiberica() {
		var packageResponse = new PackageResponse("liberica", "x64", "11.0.14.1+1", null, "linux", "filename.zip", "ga",
				false, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testLibericaFX() {
		var packageResponse = new PackageResponse("liberica", "x64", "11.0.14.1+1", null, "linux", "filename.zip", "ga",
				true, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1.fx"));
	}

	@Test
	void testOpen() {
		var packageResponse = new PackageResponse("oracle_open_jdk", "x64", "11.0.14.1", null, "linux", "filename.zip",
				"ga", false, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testOpenEa() {
		var packageResponse = new PackageResponse("oracle_open_jdk", "x64", "18-ea+3", null, "linux", "filename.zip",
				"ea", false, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("18.ea.3"));
	}

	@Test
	void testZulu() {
		var packageResponse = new PackageResponse("zulu", "x64", "11.0.14.1+1", null, "linux", "filename.zip", "ga",
				false, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1"));
	}

	@Test
	void testZuluFx() {
		var packageResponse = new PackageResponse("zulu", "x64", "11.0.14.1+1", null, "linux", "filename.zip", "ga",
				true, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("11.0.14.1.fx"));
	}

	@Test
	void testZuluCrac() {
		var packageResponse = new PackageResponse("zulu", "x64", "17.0.7", null, "linux", "filename.zip", "ga", false,
				null, List.of(new Feature("CRAC")));
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("17.0.7.crac"));
	}

	@Test
	void testGraalVm() {
		var packageResponse = new PackageResponse("liberica_native", "x64", "22.1", "17", "linux", "filename.zip", "ga",
				false, null, List.of());
		var version = Version.format(packageResponse);
		assertThat(version).isEqualTo(Optional.of("22.1.r17"));
	}

}
