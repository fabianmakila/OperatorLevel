package fi.fabianadrian.operatorlevel.sponge17.level;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderManager;
import fi.fabianadrian.operatorlevel.common.level.LuckPermsLevelProvider;
import fi.fabianadrian.operatorlevel.common.level.PermissionLevelProvider;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.permission.Subject;

public final class SpongeLevelProviderManager extends LevelProviderManager<ServerPlayer> {
	public SpongeLevelProviderManager(OperatorLevel<ServerPlayer> operatorLevel) {
		super(operatorLevel);
	}

	@Override
	public PermissionLevelProvider<ServerPlayer> permissionLevelProvider() {
		return new PermissionLevelProvider<>(Subject::hasPermission);
	}

	@Override
	public LuckPermsLevelProvider<ServerPlayer> luckPermsLevelProvider() {
		return new LuckPermsLevelProvider<>(super.logger, ServerPlayer.class);
	}
}
