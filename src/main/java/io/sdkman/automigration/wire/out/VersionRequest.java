package io.sdkman.automigration.wire.out;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

// @formatter:off
public record VersionRequest(String candidate,
                             String vendor,
                             String version,
                             Platform platform,
                             String url,
                             Map<String, String> checksums,
                             @JsonProperty("default")
                             Boolean defaultCandidate) {
}
// @formatter:on
