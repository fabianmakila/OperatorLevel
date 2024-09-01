package fi.fabianadrian.operatorlevel.common;

import fi.fabianadrian.operatorlevel.common.config.ConfigManager;
import fi.fabianadrian.operatorlevel.common.config.OperatorLevelConfig;
import fi.fabianadrian.operatorlevel.common.platform.Platform;

public final class OperatorLevel {
	private final ConfigManager<OperatorLevelConfig> configManager;

	public OperatorLevel(Platform<?> platform) {
		this.configManager = ConfigManager.create(
				platform.dataPath(),
				"config.yml",
				OperatorLevelConfig.class,
				platform.logger()
		);
	}

	public OperatorLevelConfig config() {
		return this.configManager.config();
	}

	public void reload() {
		this.configManager.load();
	}
}
