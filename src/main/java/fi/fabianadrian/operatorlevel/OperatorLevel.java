package fi.fabianadrian.operatorlevel;

import fi.fabianadrian.operatorlevel.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class OperatorLevel extends JavaPlugin {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
	}
}
