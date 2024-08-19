package fi.fabianadrian.operatorlevel;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class OperatorLevel extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		for (int i = 4; i > 0; i--) {
			if (player.hasPermission("operatorlevel." + i)) {
				player.sendOpLevel((byte) i);
				break;
			}
		}
	}
}
