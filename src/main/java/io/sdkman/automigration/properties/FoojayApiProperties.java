package io.sdkman.automigration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "foojay-api")
public record FoojayApiProperties(String url) {

}
