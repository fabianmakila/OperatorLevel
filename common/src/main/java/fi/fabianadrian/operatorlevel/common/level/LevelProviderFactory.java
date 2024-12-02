package fi.fabianadrian.operatorlevel.common.level;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import net.luckperms.api.LuckPerms;
import org.slf4j.Logger;

public abstract class LevelProviderFactory<P> {
	private final OperatorLevel<P> operatorLevel;
	protected final Logger logger;

	public LevelProviderFactory(OperatorLevel<P> operatorLevel) {
		this.operatorLevel = operatorLevel;
		this.logger = this.operatorLevel.logger();
	}

	public LevelProvider<P> levelProvider() {
		if (!this.operatorLevel.config().luckPermsMeta()) {
			return permissionLevelProvider();
		}

		LevelProvider<P> luckPermsProvider = luckPermsLevelProvider();
		if (luckPermsProvider == null) {
			this.logger.warn("luckPermsMeta config option was enabled, but LuckPerms isn't enabled. Falling back to a permission-based check.");
			return permissionLevelProvider();
		}

		return luckPermsProvider;
	}

	protected abstract PermissionLevelProvider<P> permissionLevelProvider();

	protected abstract LuckPermsLevelProvider<P> luckPermsLevelProvider();

	public abstract void createLuckPermsProvider(LuckPerms luckPerms);
}
