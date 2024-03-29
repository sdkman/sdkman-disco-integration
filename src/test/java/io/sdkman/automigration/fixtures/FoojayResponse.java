package io.sdkman.automigration.fixtures;

public class FoojayResponse {

	public static String empty() {
		return """
				{"result": [ ]}
				""";
	}

	public static String liberica8() {
		return """
				{"result": [
				 {
				       "id": "40eec16d2c3fb302d76d49184e9b06a6",
				       "archive_type": "deb",
				       "distribution": "liberica",
				       "major_version": 8,
				       "java_version": "8",
				       "distribution_version": "8",
				       "jdk_version": 8,
				       "latest_build_available": false,
				       "release_status": "ga",
				       "term_of_support": "lts",
				       "operating_system": "linux",
				       "lib_c_type": "glibc",
				       "architecture": "amd64",
				       "fpu": "unknown",
				       "package_type": "jdk",
				       "javafx_bundled": true,
				       "directly_downloadable": true,
				       "filename": "bellsoft-jdk8-linux-amd64.deb",
				       "links": {
				         "pkg_info_uri": "https://api.foojay.io/disco/v3.0/ids/40eec16d2c3fb302d76d49184e9b06a6",
				         "pkg_download_redirect": "https://api.foojay.io/disco/v3.0/ids/40eec16d2c3fb302d76d49184e9b06a6/redirect"
				       },
				       "free_use_in_production": true,
				       "tck_tested": "unknown",
				       "tck_cert_uri": "",
				       "aqavit_certified": "unknown",
				       "aqavit_cert_uri": "",
				       "size": 241158578,
				       "feature": []
				     }
				 ]}
				""";
	}

	public static String liberica80322amd64() {
		return """
				{
				   "result":[
				      {
				         "id":"8d2136c6472ce970f80ee4fedcc92f99",
				         "archive_type":"tar.gz",
				         "distribution":"liberica",
				         "major_version":8,
				         "java_version":"8.0.322+6",
				         "distribution_version":"8+6",
				         "jdk_version":8,
				         "latest_build_available":true,
				         "release_status":"ga",
				         "term_of_support":"lts",
				         "operating_system":"linux",
				         "lib_c_type":"glibc",
				         "architecture":"amd64",
				         "fpu":"unknown",
				         "package_type":"jdk",
				         "javafx_bundled":false,
				         "directly_downloadable":true,
				         "filename":"bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				         "links":{
				            "pkg_info_uri":"https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99",
				            "pkg_download_redirect":"https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99/redirect"
				         },
				         "free_use_in_production":true,
				         "tck_tested":"unknown",
				         "tck_cert_uri":"",
				         "aqavit_certified":"unknown",
				         "aqavit_cert_uri":"",
				         "size":106800593,
				         "feature":[]
				      },
				      {
				         "id":"0d11fd13eeaec70355f7b9a05145a96a",
				         "archive_type":"tar.gz",
				         "distribution":"liberica",
				         "major_version":8,
				         "java_version":"8.0.322+6",
				         "distribution_version":"8+6",
				         "jdk_version":8,
				         "latest_build_available":true,
				         "release_status":"ga",
				         "term_of_support":"lts",
				         "operating_system":"linux",
				         "lib_c_type":"glibc",
				         "architecture":"amd64",
				         "fpu":"unknown",
				         "package_type":"jdk",
				         "javafx_bundled":false,
				         "directly_downloadable":true,
				         "filename":"bellsoft-jdk8u322+6-linux-amd64-lite.tar.gz",
				         "links":{
				            "pkg_info_uri":"https://api.foojay.io/disco/v3.0/ids/0d11fd13eeaec70355f7b9a05145a96a",
				            "pkg_download_redirect":"https://api.foojay.io/disco/v3.0/ids/0d11fd13eeaec70355f7b9a05145a96a/redirect"
				         },
				         "free_use_in_production":true,
				         "tck_tested":"unknown",
				         "tck_cert_uri":"",
				         "aqavit_certified":"unknown",
				         "aqavit_cert_uri":"",
				         "size":53968275,
				         "feature":[]
				      }
				   ]
				}
				""";
	}

	public static String liberica80322arm64() {
		return """
				{
				   "result":[
				      {
				         "id":"49a4992b4f68cfa4b796a3e7f8b68c6f",
				         "archive_type":"tar.gz",
				         "distribution":"liberica",
				         "major_version":8,
				         "java_version":"8.0.322+6",
				         "distribution_version":"8+6",
				         "jdk_version":8,
				         "latest_build_available":true,
				         "release_status":"ga",
				         "term_of_support":"lts",
				         "operating_system":"linux",
				         "lib_c_type":"glibc",
				         "architecture":"aarch64",
				         "fpu":"unknown",
				         "package_type":"jdk",
				         "javafx_bundled":false,
				         "directly_downloadable":true,
				         "filename":"bellsoft-jdk8u322+6-linux-aarch64.tar.gz",
				         "links":{
				            "pkg_info_uri":"https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99",
				            "pkg_download_redirect":"https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99/redirect"
				         },
				         "free_use_in_production":true,
				         "tck_tested":"unknown",
				         "tck_cert_uri":"",
				         "aqavit_certified":"unknown",
				         "aqavit_cert_uri":"",
				         "size":105710219,
				         "feature":[ ]
				      },
				      {
				         "id":"9561d0370ef91bfe5aa0dfd50986c0f5",
				         "archive_type":"tar.gz",
				         "distribution":"liberica",
				         "major_version":8,
				         "java_version":"8.0.322+6",
				         "distribution_version":"8+6",
				         "jdk_version":8,
				         "latest_build_available":true,
				         "release_status":"ga",
				         "term_of_support":"lts",
				         "operating_system":"linux",
				         "lib_c_type":"glibc",
				         "architecture":"aarch64",
				         "fpu":"unknown",
				         "package_type":"jdk",
				         "javafx_bundled":false,
				         "directly_downloadable":true,
				         "filename":"bellsoft-jdk8u322+6-linux-aarch64-lite.tar.gz",
				         "links":{
				            "pkg_info_uri":"https://api.foojay.io/disco/v3.0/ids/9561d0370ef91bfe5aa0dfd50986c0f5",
				            "pkg_download_redirect":"https://api.foojay.io/disco/v3.0/ids/9561d0370ef91bfe5aa0dfd50986c0f5/redirect"
				         },
				         "free_use_in_production":true,
				         "tck_tested":"unknown",
				         "tck_cert_uri":"",
				         "aqavit_certified":"unknown",
				         "aqavit_cert_uri":"",
				         "size":52841379,
				         "feature":[ ]
				      }
				   ]
				}
				""";
	}

	public static String idsResponseAmd64WithChecksum() {
		return """
				{
				  "result":[
				    {
				    "filename":"bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				    "direct_download_uri":"https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				    "download_site_uri":"",
				    "signature_uri":"",
				    "checksum_uri":"",
				    "checksum":"813eb415bd91e5dcb846fea4ffcf07befb254f5a",
				    "checksum_type":"sha1"
				  }
				    ],
				  "message":""
				}
				""";
	}

	public static String idsResponseArm64WithChecksum() {
		return """
				{
				  "result":[
				    {
				    "filename":"bellsoft-jdk8u322+6-linux-aarch64.tar.gz",
				    "direct_download_uri":"https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-aarch64.tar.gz",
				    "download_site_uri":"",
				    "signature_uri":"",
				    "checksum_uri":"",
				    "checksum":"813eb415bd91e5dcb846fea4ffcf07befb254f5a",
				    "checksum_type":"sha1"
				  }
				    ],
				  "message":""
				}
				""";
	}

	public static String idsResponseWithNoChecksum() {
		return """
				{
				  "result":[
				    {
				    "filename":"bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				    "direct_download_uri":"https://github.com/bell-sw/Liberica/releases/download/8u322+6/bellsoft-jdk8u322+6-linux-amd64.tar.gz",
				    "download_site_uri":"",
				    "signature_uri":"",
				    "checksum_uri":"",
				    "checksum":"",
				    "checksum_type":"sha1"
				  }
				    ],
				  "message":""
				}
				""";
	}

	public static String libericaNik80322amd64() {
		return """
				{
				    "result":[
				       {
				          "id":"fd3f88feb5963a9a45a5cefaf58ee0b7",
				          "archive_type":"tar.gz",
				          "distribution":"liberica_native",
				          "major_version":22,
				          "java_version":"22.1",
				          "distribution_version":"22.1",
				          "jdk_version":17,
				          "latest_build_available":true,
				          "release_status":"ga",
				          "term_of_support":"lts",
				          "operating_system":"linux",
				          "lib_c_type":"glibc",
				          "architecture":"amd64",
				          "fpu":"unknown",
				          "package_type":"jdk",
				          "javafx_bundled":false,
				          "directly_downloadable":true,
				          "filename":"bellsoft-liberica-vm-openjdk17.0.3+7-22.1.0+1-linux-amd64.tar.gz",
				          "links":{
				             "pkg_info_uri":"https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99",
				             "pkg_download_redirect":"https://api.foojay.io/disco/v3.0/ids/8d2136c6472ce970f80ee4fedcc92f99/redirect"
				          },
				          "free_use_in_production":true,
				          "tck_tested":"unknown",
				          "tck_cert_uri":"",
				          "aqavit_certified":"unknown",
				          "aqavit_cert_uri":"",
				          "size":459775291,
				          "feature":[ ]
				       },
				       {
				          "id":"e5cf097477c95cb9896de5d10f8470b5",
				          "archive_type":"tar.gz",
				          "distribution":"liberica_native",
				          "major_version":22,
				          "java_version":"22.0.0.2",
				          "distribution_version":"22.0.0.2",
				          "jdk_version":17,
				          "latest_build_available":true,
				          "release_status":"ga",
				          "term_of_support":"lts",
				          "operating_system":"linux",
				          "lib_c_type":"glibc",
				          "architecture":"amd64",
				          "fpu":"unknown",
				          "package_type":"jdk",
				          "javafx_bundled":false,
				          "directly_downloadable":true,
				          "filename":"bellsoft-liberica-vm-openjdk17-22.0.0.2-linux-amd64.tar.gz",
				          "links":{
				             "pkg_info_uri":"https://api.foojay.io/disco/v3.0/ids/e5cf097477c95cb9896de5d10f8470b5",
				             "pkg_download_redirect":"https://api.foojay.io/disco/v3.0/ids/e5cf097477c95cb9896de5d10f8470b5/redirect"
				          },
				          "free_use_in_production":true,
				          "tck_tested":"unknown",
				          "tck_cert_uri":"",
				          "aqavit_certified":"unknown",
				          "aqavit_cert_uri":"",
				          "size":444562306,
				          "feature":[ ]
				       }
				    ],
				    "message":"2 package(s) found"
				 }
				""";
	}

	public static String libericaNikIdsResponseWithNoChecksum() {
		return """
				{
				    "result":[
				       {
				          "filename":"bellsoft-liberica-vm-openjdk17.0.3+7-22.1.0+1-linux-amd64.tar.gz",
				          "direct_download_uri":"https://download.bell-sw.com/vm/22.1.0/bellsoft-liberica-vm-openjdk17.0.3+7-22.1.0+1-linux-amd64.tar.gz",
				          "download_site_uri":"",
				          "signature_uri":"",
				          "checksum_uri":"",
				          "checksum":"ccf398d93b5c472262e1c82d0328226bbe04368b",
				          "checksum_type":"sha1"
				       }
				    ],
				    "message":""
				 }
				""";
	}

	public static String zuluCracIdsResponse() {
		return """
				{
				  "result":[
				    {
				    "filename":"zulu17.42.19-ca-crac-jdk17.0.7-linux_x64.tar.gz",
				    "direct_download_uri":"https://cdn.azul.com/zulu/bin/zulu17.42.21-ca-crac-jdk17.0.7-linux_x64.tar.gz",
				    "download_site_uri":"",
				    "signature_uri":"",
				    "checksum_uri":"",
				    "checksum":"",
				    "checksum_type":"sha1"
				  }
				    ],
				  "message":""
				}
				""";
	}

}
