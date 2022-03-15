package io.sdkman.automigration.logic;

import io.sdkman.automigration.wire.out.Platform;

import java.util.Map;

import static java.util.Map.entry;

public class Release {

	// @formatter:off
    private static final Map<String, Map<String, Platform>> PLATFORMS = Map.ofEntries(
            entry("linux", Map.of("x64", Platform.LINUX_64,
                    "x86", Platform.LINUX_64,
                    "amd64", Platform.LINUX_64,
                    "aarch64", Platform.LINUX_ARM64,
                    "arm", Platform.LINUX_ARM64,
                    "arm64", Platform.LINUX_ARM64)),
            entry("macos", Map.of("x64", Platform.MAC_OSX,
                    "x86", Platform.MAC_OSX,
                    "amd64", Platform.MAC_OSX,
                    "aarch64", Platform.MAC_ARM64,
                    "arm", Platform.MAC_ARM64)),
            entry("windows", Map.of("x64", Platform.WINDOWS_64,
                    "x86", Platform.WINDOWS_64,
                    "amd64", Platform.WINDOWS_64)));
    // @formatter:on

	public static Platform platform(String os, String arch) {
		return PLATFORMS.get(os).get(arch);
	}

}
