package io.sdkman.automigration.fixtures;

public class SdkmanReleaseRequest {

	public static String candidateWithChecksum() {
		return """
				{
				    "candidate": "java",
				    "version": "8.0.322",
				    "vendor": "librca",
				    "url": "https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				    "platform": "LINUX_64",
				    "checksums": {
				        "SHA-1": "813eb415bd91e5dcb846fea4ffcf07befb254f5a"
				    }
				}
				""";
	}

	public static String candidateAmd64WithNoChecksum() {
		return """
				{
				    "candidate": "java",
				    "version": "8.0.322",
				    "vendor": "librca",
				    "url": "https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				    "platform": "LINUX_64"
				}
				""";
	}

	public static String candidateArm64WithNoChecksum() {
		return """
				{
				    "candidate": "java",
				    "version": "8.0.322",
				    "vendor": "librca",
				    "url": "https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-aarch64.tar.gz",
				    "platform": "LINUX_ARM64"
				}
				""";
	}

	public static String libericaNikCandidateAmd64WithNoChecksum() {
		return """
				{
				    "candidate": "java",
				    "version": "22.1.r17",
				    "vendor": "nik",
				    "url": "https://download.bell-sw.com/vm/22.1.0/bellsoft-liberica-vm-openjdk17.0.3+7-22.1.0+1-linux-amd64.tar.gz",
				    "platform": "LINUX_64"
				}
				""";
	}

}
