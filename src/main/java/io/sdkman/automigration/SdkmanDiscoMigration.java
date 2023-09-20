package io.sdkman.automigration;

import io.sdkman.automigration.properties.FoojayApiProperties;
import io.sdkman.automigration.properties.SdkmanApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FoojayApiProperties.class, SdkmanApiProperties.class })
public class SdkmanDiscoMigration {

	public static void main(String[] args) {
		SpringApplication.run(SdkmanDiscoMigration.class, args);
	}

}
