package fi.fabianadrian.operatorlevel.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		int i = 4;
		while (i > 0) {
			if (player.hasPermission("operatorlevel." + i)) {
				player.sendOpLevel((byte) i);
				break;
			}

			i--;
		}
	}
}
