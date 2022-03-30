package io.sdkman.automigration.models;

import java.util.List;

// @formatter:off
public record VendorProperties(List<OS> linux,
                               List<OS> macos,
                               List<OS> windows) {

    public record OS(String architecture,
                     String archiveType) {

    }
}
// @formatter:on
