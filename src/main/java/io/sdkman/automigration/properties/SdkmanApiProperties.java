package io.sdkman.automigration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sdkman-api")
public record SdkmanApiProperties(Broker broker, Release release) {

	public record Broker(String url) {

	}

	public record Release(String url) {

	}
}
