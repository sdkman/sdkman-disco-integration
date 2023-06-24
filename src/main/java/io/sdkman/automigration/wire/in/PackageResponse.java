package io.sdkman.automigration.wire.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// @formatter:off
public record PackageResponse(String distribution,
                              String architecture,
                              @JsonProperty("java_version")
                              String javaVersion,
                              @JsonProperty("jdk_version")
                              String jdkVersion,
                              @JsonProperty("operating_system")
                              String operatingSystem,
                              String filename,
                              @JsonProperty("release_status")
                              String releaseStatus,
                              @JsonProperty("javafx_bundled")
                              boolean javafxBundled,
                              Links links,
                              List<Feature> feature) {
}
// @formatter:on
