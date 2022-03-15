package io.sdkman.automigration.wire.in;

import com.fasterxml.jackson.annotation.JsonProperty;

// @formatter:off
public record Links(@JsonProperty("pkg_info_uri")
                    String pkgInfoUri) {
}
// @formatter:on
