package fi.fabianadrian.operatorlevel.common.level;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.slf4j.Logger;

public final class LuckPermsLevelProvider<P> implements LevelProvider<P> {
	private final LuckPerms api;
	private final Logger logger;
	private final Class<P> playerClass;

	public LuckPermsLevelProvider(LuckPerms api, Logger logger, Class<P> playerClass) {
		this.api = api;
		this.logger = logger;
		this.playerClass = playerClass;
	}

	@Override
	public int level(P player) {
		User user = user(player);

		String unparsed = user.getCachedData().getMetaData().getMetaValue("operatorlevel");
		if (unparsed == null) {
			return 0;
		}

		int level;
		try {
			level = Integer.parseInt(unparsed);
		} catch (NumberFormatException e) {
			this.logger.warn(
					"Operator level must be a number between 0 and 4 but {} has a meta value of \"{}\"! Please check your LuckPerms configuration.",
					user.getUsername(),
					unparsed
			);
			return 0;
		}

		// Make sure that the level is always between 0 and 4
		if (level < 0 || level > 4) {
			this.logger.warn(
					"Operator level must be between 0 and 4 but {} has a level of {}! Please check your LuckPerms configuration.",
					user.getUsername(),
					level
			);
			level = Math.clamp(level, 0, 4);
		}

		return (byte) level;
	}

	private User user(P player) {
		return this.api.getPlayerAdapter(this.playerClass).getUser(player);
	}
}
