package io.sdkman.automigration.adapters;

import io.sdkman.automigration.wire.in.IdsResponse;
import io.sdkman.automigration.wire.out.Platform;
import io.sdkman.automigration.wire.out.VersionRequest;
import org.springframework.util.StringUtils;

import java.util.Map;

import static java.util.Map.entry;

public class VersionAdapter {

	// @formatter:off
    private static final Map<String, String> FOOJAY_SDKMAN_CHECKSUM_TYPE_MAPPING = Map.ofEntries(
            entry("sha1", "SHA-1"),
			entry("sha256", "SHA-256"));
    // @formatter:on

	public static VersionRequest toVersionRequest(String vendor, String version, Platform platform,
			IdsResponse idsResponse) {
		// var checksums = checksums(idsResponse);
		return new VersionRequest("java", vendor, version, platform, idsResponse.directDownloadUri(), null);
	}

	private static Map<String, String> checksums(IdsResponse idsResponse) {
		var foojayChecksum = idsResponse.checksum();
		var foojayChecksumType = idsResponse.checksumType();
		if (StringUtils.hasLength(foojayChecksum) && StringUtils.hasLength(foojayChecksumType)) {
			var sdkmanChecksumType = FOOJAY_SDKMAN_CHECKSUM_TYPE_MAPPING.get(foojayChecksumType);
			return Map.of(sdkmanChecksumType, foojayChecksum);
		}
		return null;
	}

}
