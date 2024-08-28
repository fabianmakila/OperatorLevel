package fi.fabianadrian.operatorlevel;

import fi.fabianadrian.operatorlevel.listener.LuckpermsListener;
import fi.fabianadrian.operatorlevel.listener.PlayerListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class OperatorLevel extends JavaPlugin {
	@Override
	public void onEnable() {
		registerListeners();
	}

	public void updateOpLevel(Player player) {
		for (int i = 4; i > 0; i--) {
			if (player.hasPermission("operatorlevel." + i)) {
				player.sendOpLevel((byte) i);
				return;
			}
		}
		player.sendOpLevel((byte) 0);
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		new LuckpermsListener(this);
	}
}
