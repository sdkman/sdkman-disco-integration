package io.sdkman.automigration.logic;

import io.sdkman.automigration.wire.in.Feature;
import io.sdkman.automigration.wire.in.PackageResponse;

import java.util.Optional;
import java.util.regex.Pattern;

public class Version {

	private Version() {
	}

	public static Optional<String> format(PackageResponse packageResponse) {
		var pattern = Pattern.compile("\\d[^+_]+");
		var matcher = pattern.matcher(packageResponse.javaVersion());
		if (matcher.find()) {
			var javaVersion = matcher.group();
			return switch (packageResponse.distribution()) {
				case "liberica" -> resolveLibericaVersion(javaVersion, packageResponse);
				case "zulu" -> resolveZuluVersion(javaVersion, packageResponse);
				case "oracle_open_jdk" -> openjdkVersion(javaVersion, packageResponse);
				case "gluon_graalvm", "graalvm_ce8", "graalvm_ce11", "graalvm_ce17", "graalvm_ce19", "liberica_native",
						"mandrel" ->
					graalVmVersion(javaVersion, packageResponse);
				default -> Optional.of(javaVersion);
			};
		}
		return Optional.empty();
	}

	private static Optional<String> javaFxVersion(String javaVersion, PackageResponse packageResponse) {
		if (packageResponse.javafxBundled()) {
			var javaVersionWithFxSuffix = "%s.fx".formatted(javaVersion);
			return Optional.of(javaVersionWithFxSuffix);
		}
		return Optional.empty();
	}

	private static Optional<String> openjdkVersion(String javaVersion, PackageResponse packageResponse) {
		if (packageResponse.releaseStatus().equals("ea")) {
			var javaEaVersion = packageResponse.javaVersion().replace("-", ".").replace("+", ".");
			return Optional.of(javaEaVersion);
		}
		return Optional.of(javaVersion);
	}

	private static Optional<String> graalVmVersion(String javaVersion, PackageResponse packageResponse) {

		return Optional.of("%s.r%s".formatted(javaVersion, packageResponse.jdkVersion()));
	}

	private static Optional<String> resolveLibericaVersion(String javaVersion, PackageResponse packageResponse) {
		Optional<String> javaFxVersion = javaFxVersion(javaVersion, packageResponse);
		if (javaFxVersion.isPresent()) {
			return javaFxVersion;
		}
		return Optional.of(javaVersion);
	}

	private static Optional<String> resolveZuluVersion(String javaVersion, PackageResponse packageResponse) {
		Optional<String> javaFxVersion = javaFxVersion(javaVersion, packageResponse);
		if (javaFxVersion.isPresent()) {
			return javaFxVersion;
		}
		else if (!packageResponse.feature().isEmpty() && packageResponse.feature().contains(new Feature("CRAC"))) {
			return Optional.of("%s.crac".formatted(javaVersion));
		}
		return Optional.of(javaVersion);
	}

}
