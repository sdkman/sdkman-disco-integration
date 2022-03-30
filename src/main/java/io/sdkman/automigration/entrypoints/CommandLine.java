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

import java.util.List;

@Component
public class CommandLine {

	private final Environment environment;

	private final JavaMigration javaMigration;

	public CommandLine(Environment environment, JavaMigration javaMigration) {
		this.environment = environment;
		this.javaMigration = javaMigration;
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			var distribution = this.environment.getRequiredProperty("foojay.java.distribution", String.class);
			var version = this.environment.getRequiredProperty("foojay.java.version", String.class);
			var releaseStatus = this.environment.getRequiredProperty("foojay.java.release-status", String.class);
			var javafxBundled = this.environment.getProperty("foojay.java.javafx-bundled", Boolean.class, false);

			var vendorProperties = Binder.get(this.environment)
					.bind(ConfigurationPropertyName.of("sdkman").append(distribution.replace("_", "-")),
							Bindable.of(VendorProperties.class))
					.orElse(null);

			process(vendorProperties.linux(), distribution, version, releaseStatus, "linux", javafxBundled);
			process(vendorProperties.macos(), distribution, version, releaseStatus, "macos", javafxBundled);
			process(vendorProperties.windows(), distribution, version, releaseStatus, "windows", javafxBundled);
		};
	}

	void process(List<VendorProperties.OS> os, String distribution, String version, String releaseStatus,
			String operatingSystem, boolean javafxBundled) {
		if (os != null) {
			os.stream().map(vendorProperties1 -> PackageAdapter.toQueryParams(distribution, version, releaseStatus,
					operatingSystem, javafxBundled, vendorProperties1)).forEach(this.javaMigration::execute);
		}
	}

}
