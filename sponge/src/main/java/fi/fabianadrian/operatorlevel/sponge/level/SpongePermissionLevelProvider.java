package fi.fabianadrian.operatorlevel.sponge.level;

import fi.fabianadrian.operatorlevel.common.level.PermissionLevelProvider;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public final class SpongePermissionLevelProvider extends PermissionLevelProvider<Player> {
	@Override
	protected boolean hasPermission(Player player, String permission) {
		return ((ServerPlayer) player).hasPermission(permission);
	}
}
