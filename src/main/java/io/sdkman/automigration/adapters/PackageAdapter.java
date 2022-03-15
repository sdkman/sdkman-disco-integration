package io.sdkman.automigration.adapters;

import io.sdkman.automigration.models.VendorProperties;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class PackageAdapter {

	// @formatter:off
    public static final Map<String, String> FOOJAY_SDKMAN_VENDOR_MAPPING = Map.ofEntries(
            entry("corretto", "amzn"),
            entry("dragonwell", "albba"),
            entry("gluon_graalvm", "gln"),
            entry("graalvm_ce8", "grl"),
            entry("graalvm_ce11", "grl"),
            entry("graalvm_ce17", "grl"),
            entry("liberica", "librca"),
            entry("liberica_native", "nik"),
            entry("mandrel", "mandrel"),
            entry("microsoft", "ms"),
            entry("oracle", "oracle"),
            entry("oracle_open_jdk", "open"),
            entry("sap_machine", "sapmchn"),
            entry("semeru", "sem"),
            entry("temurin", "tem"),
            entry("zulu", "zulu"));
    // @formatter:on

	public static Map<String, List<String>> toQueryParams(String distribution, VendorProperties properties) {
		// @formatter:off
        return Map.of("version", List.of(properties.version()),
                "release_status", List.of(properties.releaseStatus()),
                "operating_system", List.of(properties.operatingSystem()),
                "architecture", List.of(properties.architecture()),
                "archive_type", List.of(properties.archiveType()),
                "distribution", List.of(distribution),
                "javafx_bundled", List.of(String.valueOf(properties.javafxBundled())));
        // @formatter:on
	}

}
