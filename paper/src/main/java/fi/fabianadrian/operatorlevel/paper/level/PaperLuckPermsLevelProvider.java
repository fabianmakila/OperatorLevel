package fi.fabianadrian.operatorlevel.paper.level;

import fi.fabianadrian.operatorlevel.common.level.LuckPermsLevelProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

public final class PaperLuckPermsLevelProvider extends LuckPermsLevelProvider<Player> {
	public PaperLuckPermsLevelProvider(LuckPerms api, Logger logger) {
		super(api, logger);
	}

	@Override
	protected User user(Player player) {
		return super.api.getPlayerAdapter(Player.class).getUser(player);
	}
}
