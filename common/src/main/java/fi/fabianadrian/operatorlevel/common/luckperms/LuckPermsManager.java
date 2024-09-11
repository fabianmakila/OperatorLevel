package fi.fabianadrian.operatorlevel.common.luckperms;

import fi.fabianadrian.operatorlevel.common.platform.Platform;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.luckperms.api.model.user.User;

public abstract class LuckPermsManager {
	private final Platform<?> platform;

	public LuckPermsManager(Platform<?> platform, LuckPerms luckPerms) {
		this.platform = platform;

		EventBus eventBus = luckPerms.getEventBus();
		eventBus.subscribe(platform, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
	}

	protected int level(User user) {
		String unparsed = user.getCachedData().getMetaData().getMetaValue("operatorlevel");
		if (unparsed == null) {
			return 0;
		}

		int level;
		try {
			level = Integer.parseInt(unparsed);
		} catch (NumberFormatException e) {
			this.platform.logger().warn(
					"Operator level must be a number between 0 and 4 but {} has a meta value of \"{}\"! Please check your LuckPerms configuration.",
					user.getUsername(),
					unparsed
			);
			return 0;
		}

		// Make sure that the level is always between 0 and 4
		if (level < 0 || level > 4) {
			this.platform.logger().warn(
					"Operator level must be between 0 and 4 but {} has a level of {}! Please check your LuckPerms configuration.",
					user.getUsername(),
					level
			);
			level = Math.clamp(level, 0, 4);
		}

		return (byte) level;
	}

	private void onUserDataRecalculate(UserDataRecalculateEvent event) {
		User user = event.getUser();
		this.platform.updateOpLevel(user.getUniqueId(), level(user));
	}
}
