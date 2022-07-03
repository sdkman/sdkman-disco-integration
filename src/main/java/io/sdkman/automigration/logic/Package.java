package io.sdkman.automigration.logic;

import io.sdkman.automigration.wire.in.PackageResponse;

public class Package {

	public static boolean libericaFilenameDoesNotContainLite(PackageResponse packageResponse) {
		return !("liberica".equals(packageResponse.distribution())
				&& (packageResponse.filename().matches("bellsoft-jdk.+.-lite.*")
						|| packageResponse.filename().matches("bellsoft-jdk.+.-arm32-vfp-hflt-lite.*")));
	}

}
