package io.sdkman.automigration.adapters;

import io.sdkman.automigration.models.FoojayQueryParams;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class PackageAdapter {

	// @formatter:off
    private static final Map<String, String> FOOJAY_SDKMAN_GRAALVM_VENDOR_MAPPING = Map.ofEntries(
            entry("gluon_graalvm", "gln"),
            entry("graalvm_ce8", "grl"),
            entry("graalvm_ce11", "grl"),
            entry("graalvm_ce17", "grl"),
            entry("liberica_native", "nik"),
            entry("mandrel", "mandrel"));

    private static final Map<String, String> FOOJAY_SDKMAN_JVM_VENDOR_MAPPING = Map.ofEntries(
            entry("corretto", "amzn"),
            entry("dragonwell", "albba"),
            entry("liberica", "librca"),
            entry("microsoft", "ms"),
            entry("oracle", "oracle"),
            entry("oracle_open_jdk", "open"),
            entry("sap_machine", "sapmchn"),
            entry("semeru", "sem"),
            entry("temurin", "tem"),
            entry("zulu", "zulu"));

    public static final Map<String, String> FOOJAY_SDKMAN_VENDOR_MAPPING = Stream.concat(FOOJAY_SDKMAN_GRAALVM_VENDOR_MAPPING.entrySet().stream(), FOOJAY_SDKMAN_JVM_VENDOR_MAPPING.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    // @formatter:on

	public static Map<String, List<String>> toQueryParams(FoojayQueryParams foojayQueryParams) {
		Map<String, List<String>> graalVmQueryParams = addQueryParamsIfGraalVm(foojayQueryParams.distribution(),
				foojayQueryParams.javaVersion());
		// @formatter:off
        Map<String, List<String>> defaultQueryParams = Map.of("version", List.of(foojayQueryParams.version()),
                "release_status", List.of(foojayQueryParams.releaseStatus()),
                "operating_system", List.of(foojayQueryParams.os()),
                "architecture", List.of(foojayQueryParams.vendorOsProperties().architecture()),
                "archive_type", List.of(foojayQueryParams.vendorOsProperties().archiveType()),
                "distribution", List.of(foojayQueryParams.distribution()),
                "javafx_bundled", List.of(String.valueOf(foojayQueryParams.javafxBundled())));
        // @formatter:on

		return Stream.concat(defaultQueryParams.entrySet().stream(), graalVmQueryParams.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private static Map<String, List<String>> addQueryParamsIfGraalVm(String distribution, String javaVersion) {
		if (FOOJAY_SDKMAN_GRAALVM_VENDOR_MAPPING.containsKey(distribution)) {
			return Map.of("jdk_version", List.of(javaVersion));
		}
		return Collections.emptyMap();
	}

}
