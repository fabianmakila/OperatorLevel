package fi.fabianadrian.operatorlevel.listener;

import fi.fabianadrian.operatorlevel.OperatorLevel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListener implements Listener {
	private final OperatorLevel plugin;

	public PlayerListener(OperatorLevel plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.plugin.updateOpLevel(event.getPlayer());
	}
}
