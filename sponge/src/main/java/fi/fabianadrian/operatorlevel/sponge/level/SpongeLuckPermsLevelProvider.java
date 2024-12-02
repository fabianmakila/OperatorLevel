package fi.fabianadrian.operatorlevel.sponge.level;

import fi.fabianadrian.operatorlevel.common.level.LuckPermsLevelProvider;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;

public final class SpongeLuckPermsLevelProvider extends LuckPermsLevelProvider<Player> {
	public SpongeLuckPermsLevelProvider(LuckPerms api, Logger logger) {
		super(api, logger);
	}

	@Override
	protected User user(Player player) {
		return this.api.getPlayerAdapter(Player.class).getUser(player);
	}
}
