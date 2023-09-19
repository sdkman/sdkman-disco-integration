package io.sdkman.automigration.models;

// @formatter:off
public record FoojayQueryParams(String distribution,
								String version,
								String javaVersion,
								String releaseStatus,
								String os,
								boolean javafxBundled,
								String features,
								VendorProperties.OS vendorOsProperties) {
}
// @formatter:on
