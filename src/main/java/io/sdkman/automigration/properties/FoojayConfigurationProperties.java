package io.sdkman.automigration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "foojay")
public record FoojayConfigurationProperties(String url) {

}
