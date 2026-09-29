package fi.fabianadrian.operatorlevel.common.config;

import space.arim.dazzleconf.engine.Comments;

import java.util.Locale;

public interface OperatorLevelConfig {
	@Comments("The default locale to fall back if user locale is not available")
	default Locale defaultLocale() {
		return Locale.ENGLISH;
	}

	@Comments("Use LuckPerms' meta system to define levels.")
	@Comments("Will fallback to a permission based system if LuckPerms isn't available.")
	default boolean luckPermsMeta() {
		return true;
	}
}
