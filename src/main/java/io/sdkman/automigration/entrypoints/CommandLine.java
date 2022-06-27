package io.sdkman.automigration.entrypoints;

import io.sdkman.automigration.adapters.PackageAdapter;
import io.sdkman.automigration.controllers.JavaMigration;
import io.sdkman.automigration.models.FoojayQueryParams;
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
			var version = this.environment.getRequiredProperty("foojay.distribution.version", String.class);
			var releaseStatus = this.environment.getRequiredProperty("foojay.java.release-status", String.class);
			var javafxBundled = this.environment.getProperty("foojay.java.javafx-bundled", Boolean.class, false);
			var javaVersion = this.environment.getProperty("foojay.java.version", String.class);
			var defaultCandidate = this.environment.getProperty("sdkman.default.candidate", Boolean.class);

			var vendorProperties = Binder.get(this.environment)
					.bind(ConfigurationPropertyName.of("sdkman").append(distribution.replace("_", "-")),
							Bindable.of(VendorProperties.class))
					.orElse(null);

			process(vendorProperties.linux(), distribution, version, javaVersion, releaseStatus, "linux", javafxBundled,
					defaultCandidate);
			process(vendorProperties.macos(), distribution, version, javaVersion, releaseStatus, "macos", javafxBundled,
					defaultCandidate);
			process(vendorProperties.windows(), distribution, version, javaVersion, releaseStatus, "windows",
					javafxBundled, defaultCandidate);
		};
	}

	void process(List<VendorProperties.OS> os, String distribution, String version, String javaVersion,
			String releaseStatus, String operatingSystem, boolean javafxBundled, Boolean defaultCandidate) {
		if (os != null) {
			os.stream().map(vendorProperties -> {
				var foojayQueryParams = new FoojayQueryParams(distribution, version, javaVersion, releaseStatus,
						operatingSystem, javafxBundled, vendorProperties);
				return PackageAdapter.toQueryParams(foojayQueryParams);
			}).forEach(queryParams -> this.javaMigration.execute(queryParams, defaultCandidate));
		}
	}

}
