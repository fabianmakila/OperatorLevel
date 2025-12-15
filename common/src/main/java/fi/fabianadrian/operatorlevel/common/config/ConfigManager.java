package fi.fabianadrian.operatorlevel.common.config;

import fi.fabianadrian.operatorlevel.common.Platform;
import space.arim.dazzleconf.Configuration;
import space.arim.dazzleconf.StandardErrorPrint;
import space.arim.dazzleconf.backend.Backend;
import space.arim.dazzleconf.backend.PathRoot;
import space.arim.dazzleconf.backend.toml.TomlBackend;

public final class ConfigManager {
	private final Configuration<OperatorLevelConfig> configuration;
	private final Backend backend;
	private final StandardErrorPrint errorPrint;
	private OperatorLevelConfig config;

	public ConfigManager(Platform<?> platform) {
		this.configuration = Configuration.defaultBuilder(OperatorLevelConfig.class).build();
		this.backend = new TomlBackend(new PathRoot(platform.configDirectory().resolve("config.toml")));
		this.errorPrint = new StandardErrorPrint(output -> platform.logger().error(output.printString()));
	}

	public void load() {
		this.config = this.configuration.configureOrFallback(this.backend, this.errorPrint);
	}

	public OperatorLevelConfig config() {
		return this.config;
	}
}
