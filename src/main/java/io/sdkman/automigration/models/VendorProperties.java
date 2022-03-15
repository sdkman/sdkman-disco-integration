package io.sdkman.automigration.models;

// @formatter:off
public record VendorProperties(String version,
							   String operatingSystem,
							   String architecture,
							   String archiveType,
							   String releaseStatus,
							   boolean javafxBundled) {
}
// @formatter:on
