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
			case "liberica" -> libericaVersion(javaVersion, packageResponse);
			case "zulu" -> zuluVersion(javaVersion, packageResponse);
			case "oracle_open_jdk" -> openjdkVersion(javaVersion, packageResponse);
			default -> Optional.of(javaVersion);
			};
		}
		return Optional.empty();
	}

	private static Optional<String> libericaVersion(String javaVersion, PackageResponse packageResponse) {
		if (packageResponse.javafxBundled()) {
			var javaVersionWithFxSuffix = javaVersion + ".fx";
			return Optional.of(javaVersionWithFxSuffix);
		}
		return Optional.of(javaVersion);
	}

	private static Optional<String> zuluVersion(String javaVersion, PackageResponse packageResponse) {
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

	public static boolean isValid(String version) {
		return version.length() <= 17;
	}

}