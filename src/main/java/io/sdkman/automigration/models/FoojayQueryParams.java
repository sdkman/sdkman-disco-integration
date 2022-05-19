package io.sdkman.automigration.models;

public record FoojayQueryParams(String distribution, String version, String javaVersion, String releaseStatus,
		String os, boolean javafxBundled, VendorProperties.OS vendorOsProperties) {
}
