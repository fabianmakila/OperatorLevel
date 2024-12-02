package fi.fabianadrian.operatorlevel.sponge.level;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderFactory;
import fi.fabianadrian.operatorlevel.common.level.LuckPermsLevelProvider;
import fi.fabianadrian.operatorlevel.common.level.PermissionLevelProvider;
import net.luckperms.api.LuckPerms;
import org.spongepowered.api.entity.living.player.Player;

public final class SpongeLevelProviderFactory extends LevelProviderFactory<Player> {
	private final PermissionLevelProvider<Player> permissionLevelProvider;
	private LuckPermsLevelProvider<Player> luckPermsLevelProvider;

	public SpongeLevelProviderFactory(OperatorLevel<Player> operatorLevel) {
		super(operatorLevel);
		this.permissionLevelProvider = new SpongePermissionLevelProvider();
	}

	@Override
	protected PermissionLevelProvider<Player> permissionLevelProvider() {
		return this.permissionLevelProvider;
	}

	@Override
	protected LuckPermsLevelProvider<Player> luckPermsLevelProvider() {
		return this.luckPermsLevelProvider;
	}

	@Override
	public void createLuckPermsProvider(LuckPerms luckPerms) {
		this.luckPermsLevelProvider = new SpongeLuckPermsLevelProvider(luckPerms, this.logger);
	}
}
