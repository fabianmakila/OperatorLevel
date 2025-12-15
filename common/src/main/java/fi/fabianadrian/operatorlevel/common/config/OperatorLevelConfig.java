package fi.fabianadrian.operatorlevel.common.config;

import space.arim.dazzleconf.engine.Comments;

public interface OperatorLevelConfig {
	@Comments("Use LuckPerms' meta system to define levels.")
	@Comments("Will fallback to a permission based system if LuckPerms isn't available.")
	default boolean luckPermsMeta() {
		return true;
	}
}
