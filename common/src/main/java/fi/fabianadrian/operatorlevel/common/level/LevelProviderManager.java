package fi.fabianadrian.operatorlevel.common.level;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import net.luckperms.api.LuckPermsProvider;
import org.slf4j.Logger;

public abstract class LevelProviderManager<P> {
	private final OperatorLevel<P> operatorLevel;
	protected final Logger logger;
	private LevelProvider<P> provider;

	public LevelProviderManager(OperatorLevel<P> operatorLevel) {
		this.operatorLevel = operatorLevel;
		this.logger = this.operatorLevel.logger();
	}

	public void load() {
		if (!this.operatorLevel.config().luckPermsMeta()) {
			this.logger.info("Using permission-based check (operatorlevel.level.x) for levels");
			this.provider = permissionLevelProvider();
			return;
		}

		if (!isLuckPermsAvailable()) {
			this.logger.warn("LuckPerms isn't available. Falling back to a permission-based check for levels");
			this.provider = permissionLevelProvider();
			return;
		}

		this.provider = luckPermsLevelProvider();
		this.logger.info("Using LuckPerms' meta system for levels");
	}

	public LevelProvider<P> provider() {
		if (this.provider == null) {
			throw new IllegalStateException("LevelProvider isn't loaded");
		}
		return this.provider;
	}

	private boolean isLuckPermsAvailable() {
		try {
			LuckPermsProvider.get();
			return true;
		} catch (NoClassDefFoundError error) {
			return false;
		}
	}

	public abstract PermissionLevelProvider<P> permissionLevelProvider();

	public abstract LuckPermsLevelProvider<P> luckPermsLevelProvider();
}
