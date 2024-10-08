package fi.fabianadrian.operatorlevel.paper.luckperms;

import fi.fabianadrian.operatorlevel.paper.OperatorLevelPaper;
import fi.fabianadrian.operatorlevel.common.luckperms.LuckPermsManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.entity.Player;

public final class PaperLuckPermsManager extends LuckPermsManager {
	private final PlayerAdapter<Player> playerAdapter;
	public PaperLuckPermsManager(OperatorLevelPaper plugin, LuckPerms luckPerms) {
		super(plugin, luckPerms);
		this.playerAdapter = luckPerms.getPlayerAdapter(Player.class);
	}

	public int level(Player player) {
		User user = this.playerAdapter.getUser(player);
		return level(user);
	}
}
