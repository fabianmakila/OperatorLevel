package fi.fabianadrian.operatorlevel.common.level;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import net.luckperms.api.LuckPerms;
import org.slf4j.Logger;

import java.util.function.BiFunction;

public final class LevelProviderFactory<P> {
	private final OperatorLevel<P> operatorLevel;
	private final Logger logger;
	private final PermissionLevelProvider<P> permissionLevelProvider;
	private final Class<P> playerClass;
	private LuckPermsLevelProvider<P> luckPermsLevelProvider;

	public LevelProviderFactory(
			OperatorLevel<P> operatorLevel,
			BiFunction<P, String, Boolean> permissionChecker,
			Class<P> playerClass
	) {
		this.operatorLevel = operatorLevel;
		this.logger = this.operatorLevel.logger();
		this.permissionLevelProvider = new PermissionLevelProvider<>(permissionChecker);
		this.playerClass = playerClass;
	}

	public LevelProvider<P> levelProvider() {
		if (!this.operatorLevel.config().luckPermsMeta()) {
			return this.permissionLevelProvider;
		}

		if (this.luckPermsLevelProvider == null) {
			this.logger.warn("luckPermsMeta config option was enabled, but LuckPerms isn't enabled. Falling back to a permission-based check.");
			return this.permissionLevelProvider;
		}

		return this.luckPermsLevelProvider;
	}

	public void createLuckPermsProvider(LuckPerms luckPerms) {
		this.luckPermsLevelProvider = new LuckPermsLevelProvider<P>(luckPerms, this.logger, this.playerClass);
	}
}
