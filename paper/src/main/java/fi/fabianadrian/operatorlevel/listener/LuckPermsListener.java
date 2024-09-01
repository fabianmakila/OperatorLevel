package fi.fabianadrian.operatorlevel.listener;

import fi.fabianadrian.operatorlevel.OperatorLevelPaper;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.entity.Player;

public final class LuckPermsListener {
	private final OperatorLevelPaper plugin;

	public LuckPermsListener(OperatorLevelPaper plugin, LuckPerms luckPerms) {
		this.plugin = plugin;
		EventBus eventBus = luckPerms.getEventBus();
		eventBus.subscribe(plugin, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
	}

	private void onUserDataRecalculate(UserDataRecalculateEvent event) {
		Player player = this.plugin.getServer().getPlayer(event.getUser().getUniqueId());

		if (player == null) {
			return;
		}

		this.plugin.updateOpLevelLuckPerms(player, event.getData().getMetaData());
	}
}
