package io.sdkman.automigration.entrypoints;

import io.sdkman.automigration.adapters.PackageAdapter;
import io.sdkman.automigration.controllers.JavaMigration;
import io.sdkman.automigration.models.VendorProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CommandLine {

	private final JavaMigration javaMigration;

	public CommandLine(JavaMigration javaMigration) {
		this.javaMigration = javaMigration;
	}

	@Bean
	CommandLineRunner runner(Environment environment) {
		return args -> {
			var distribution = environment.getRequiredProperty("foojay.java.distribution", String.class);
			var queryParamsByDistribution = Binder.get(environment)
					.bind(ConfigurationPropertyName.of("sdkman").append(distribution.replace("_", "-")),
							Bindable.of(VendorProperties.class))
					.map(vendorProperties -> PackageAdapter.toQueryParams(distribution, vendorProperties))
					.orElse(Collections.emptyMap());
			this.javaMigration.execute(queryParamsByDistribution);
		};
	}

}
