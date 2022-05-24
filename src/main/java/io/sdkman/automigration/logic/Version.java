package io.sdkman.automigration.logic;

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
			case "liberica", "zulu" -> javaFxVersion(javaVersion, packageResponse);
			case "oracle_open_jdk" -> openjdkVersion(javaVersion, packageResponse);
			case "gluon_graalvm", "graalvm_ce8", "graalvm_ce11", "graalvm_ce17", "liberica_native", "mandrel" -> graalVmVersion(
					javaVersion, packageResponse);
			default -> Optional.of(javaVersion);
			};
		}
		return Optional.empty();
	}

	private static Optional<String> javaFxVersion(String javaVersion, PackageResponse packageResponse) {
		if (packageResponse.javafxBundled()) {
			var javaVersionWithFxSuffix = javaVersion + ".fx";
			return Optional.of(javaVersionWithFxSuffix);
		}
		return Optional.of(javaVersion);
	}

	private static Optional<String> openjdkVersion(String javaVersion, PackageResponse packageResponse) {
		if (packageResponse.releaseStatus().equals("ea")) {
			var javaEaVersion = packageResponse.javaVersion().replace("-", ".").replace("+", ".");
			return Optional.of(javaEaVersion);
		}
		return Optional.of(javaVersion);
	}

	private static Optional<String> graalVmVersion(String javaVersion, PackageResponse packageResponse) {
		return Optional.of(javaVersion + ".r" + packageResponse.jdkVersion());
	}

	public static boolean isValid(String version) {
		return version.length() <= 17;
	}

}
