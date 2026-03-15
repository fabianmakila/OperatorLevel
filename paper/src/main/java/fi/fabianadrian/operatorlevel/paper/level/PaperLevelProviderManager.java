package fi.fabianadrian.operatorlevel.paper.level;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderManager;
import fi.fabianadrian.operatorlevel.common.level.LuckPermsLevelProvider;
import fi.fabianadrian.operatorlevel.common.level.PermissionLevelProvider;
import org.bukkit.entity.Player;

public final class PaperLevelProviderManager extends LevelProviderManager<Player> {
	public PaperLevelProviderManager(OperatorLevel<Player> operatorLevel) {
		super(operatorLevel);
	}

	@Override
	public PermissionLevelProvider<Player> permissionLevelProvider() {
		return new PermissionLevelProvider<>(Player::hasPermission);
	}

	@Override
	public LuckPermsLevelProvider<Player> luckPermsLevelProvider() {
		return new LuckPermsLevelProvider<>(super.logger, Player.class);
	}
}
