package fi.fabianadrian.operatorlevel.paper.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListener implements Listener {
	private final OperatorLevel<Player> operatorLevel;

	public PlayerListener(OperatorLevel<Player> operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.operatorLevel.updateLevel(event.getPlayer());
	}
}
