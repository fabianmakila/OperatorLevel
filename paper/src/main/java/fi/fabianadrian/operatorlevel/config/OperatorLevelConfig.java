package fi.fabianadrian.operatorlevel.config;

import space.arim.dazzleconf.annote.ConfComments;
import space.arim.dazzleconf.annote.ConfDefault;

public interface OperatorLevelConfig {
	@ConfComments("Use LuckPerms' meta system instead of permissions to define levels.")
	@ConfDefault.DefaultBoolean(false)
	boolean luckPermsMeta();
}
