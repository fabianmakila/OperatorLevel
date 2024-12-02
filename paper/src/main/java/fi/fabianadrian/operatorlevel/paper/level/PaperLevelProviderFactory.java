package fi.fabianadrian.operatorlevel.paper.level;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderFactory;
import fi.fabianadrian.operatorlevel.common.level.LuckPermsLevelProvider;
import fi.fabianadrian.operatorlevel.common.level.PermissionLevelProvider;
import net.luckperms.api.LuckPerms;
import org.bukkit.entity.Player;

public final class PaperLevelProviderFactory extends LevelProviderFactory<Player> {
	private final PermissionLevelProvider<Player> permissionLevelProvider;
	private LuckPermsLevelProvider<Player> luckPermsLevelProvider;

	public PaperLevelProviderFactory(OperatorLevel<Player> operatorLevel) {
		super(operatorLevel);
		this.permissionLevelProvider = new PaperPermissionLevelProvider();
	}

	@Override
	protected PermissionLevelProvider<Player> permissionLevelProvider() {
		return this.permissionLevelProvider;
	}

	@Override
	protected LuckPermsLevelProvider<Player> luckPermsLevelProvider() {
		return this.luckPermsLevelProvider;
	}

	public void createLuckPermsProvider(LuckPerms luckPerms) {
		this.luckPermsLevelProvider = new PaperLuckPermsLevelProvider(luckPerms, this.logger);
	}
}
