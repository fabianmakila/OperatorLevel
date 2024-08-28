package fi.fabianadrian.operatorlevel.listener;

import fi.fabianadrian.operatorlevel.OperatorLevelPaper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListener implements Listener {
	private final OperatorLevelPaper plugin;

	public PlayerListener(OperatorLevelPaper plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.plugin.updateOpLevel(event.getPlayer());
	}
}
