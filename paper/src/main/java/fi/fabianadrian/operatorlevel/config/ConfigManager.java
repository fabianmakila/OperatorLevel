package fi.fabianadrian.operatorlevel.config;

import org.slf4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public final class ConfigManager<C> {
	private final Logger logger;
	private final ConfigurationHelper<C> helper;
	private volatile C data;

	private ConfigManager(ConfigurationHelper<C> configurationHelper, Logger logger) {
		this.helper = configurationHelper;
		this.logger = logger;
	}

	public static <C> ConfigManager<C> create(Path configurationFolder, String fileName, Class<C> configurationClass, Logger logger) {
		// SnakeYaml example
		SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
				.yamlSupplier(() -> {
					DumperOptions dumperOptions = new DumperOptions();
					// Enables comments
					dumperOptions.setProcessComments(true);
					dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
					return new Yaml(dumperOptions);
				})
				.commentMode(CommentMode.fullComments())
				.build();

		ConfigurationOptions configurationOptions = new ConfigurationOptions.Builder()
				.sorter(new AnnotationBasedSorter())
				.build();

		ConfigurationFactory<C> factory = SnakeYamlConfigurationFactory.create(
				configurationClass,
				configurationOptions,
				yamlOptions
		);
		return new ConfigManager<>(new ConfigurationHelper<>(configurationFolder, fileName, factory), logger);
	}

	public void load() {
		try {
			this.data = this.helper.reloadConfigData();
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);

		} catch (ConfigFormatSyntaxException ex) {
			this.data = this.helper.getFactory().loadDefaults();
			this.logger.error(
					"The yaml syntax in your configuration is invalid. " +
							"Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/",
					ex
			);
		} catch (InvalidConfigException ex) {
			this.data = this.helper.getFactory().loadDefaults();
			this.logger.error(
					"One of the values in your configuration is not valid. " +
							"Check to make sure you have specified the right data types.",
					ex
			);
		}
	}

	public C config() {
		C configData = this.data;
		if (configData == null) {
			throw new IllegalStateException("Configuration has not been loaded yet");
		}
		return configData;
	}

}
