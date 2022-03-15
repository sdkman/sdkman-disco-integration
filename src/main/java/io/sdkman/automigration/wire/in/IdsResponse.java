package io.sdkman.automigration.wire.in;

import com.fasterxml.jackson.annotation.JsonProperty;

// @formatter:off
public record IdsResponse(@JsonProperty("direct_download_uri")
                          String directDownloadUri,
                          String checksum,
                          @JsonProperty("checksum_type")
                          String checksumType) {
}
// @formatter:on
