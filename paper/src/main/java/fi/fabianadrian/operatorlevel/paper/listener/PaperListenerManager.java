package fi.fabianadrian.operatorlevel.paper.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.listener.ListenerManager;
import fi.fabianadrian.operatorlevel.paper.OperatorLevelPaper;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public final class PaperListenerManager extends ListenerManager<Player> {
	public PaperListenerManager(OperatorLevelPaper plugin, OperatorLevel<Player> operatorLevel) {
		super(plugin, operatorLevel);
	}

	@Override
	protected void registerPlayerListener() {
		OperatorLevelPaper plugin = (OperatorLevelPaper) super.plugin;
		PluginManager manager = plugin.getServer().getPluginManager();
		manager.registerEvents(new PlayerListener(super.operatorLevel), plugin);
	}
}
