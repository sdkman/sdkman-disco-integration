package io.sdkman.automigration;

import io.sdkman.automigration.properties.FoojayConfigurationProperties;
import io.sdkman.automigration.properties.SdkmanConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FoojayConfigurationProperties.class, SdkmanConfigurationProperties.class })
public class SdkmanDiscoMigration {

	public static void main(String[] args) {
		SpringApplication.run(SdkmanDiscoMigration.class, args);
	}

}
