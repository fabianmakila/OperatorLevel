package fi.fabianadrian.operatorlevel.paper.level;

import fi.fabianadrian.operatorlevel.common.level.PermissionLevelProvider;
import org.bukkit.entity.Player;

public final class PaperPermissionLevelProvider extends PermissionLevelProvider<Player> {
	@Override
	protected boolean hasPermission(Player player, String permission) {
		return player.hasPermission(permission);
	}
}
