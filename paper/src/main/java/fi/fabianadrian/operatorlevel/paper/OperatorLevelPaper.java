package fi.fabianadrian.operatorlevel.paper;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderFactory;
import fi.fabianadrian.operatorlevel.common.platform.Platform;
import fi.fabianadrian.operatorlevel.paper.command.OperatorLevelCommand;
import fi.fabianadrian.operatorlevel.paper.level.PaperLevelProviderFactory;
import fi.fabianadrian.operatorlevel.paper.listener.PlayerListener;
import net.luckperms.api.LuckPerms;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public final class OperatorLevelPaper extends JavaPlugin implements Platform<Player> {
	private OperatorLevel<Player> operatorLevel;
	private PaperLevelProviderFactory levelProviderFactory;

	@Override
	public void onEnable() {
		this.operatorLevel = new OperatorLevel<>(this);
		this.levelProviderFactory = new PaperLevelProviderFactory(this.operatorLevel);

		PluginManager manager = getServer().getPluginManager();
		if (manager.isPluginEnabled("LuckPerms")) {
			RegisteredServiceProvider<LuckPerms> provider = this.getServer().getServicesManager().getRegistration(LuckPerms.class);
			if (provider != null) {
				LuckPerms luckPerms = provider.getProvider();
				this.levelProviderFactory.createLuckPermsProvider(luckPerms);
				this.operatorLevel.registerLuckPermsListener(luckPerms);
			}
		}

		this.operatorLevel.load();

		OperatorLevelCommand operatorLevelCommand = new OperatorLevelCommand(this, this.operatorLevel);
		operatorLevelCommand.register();

		registerListeners();

		new Metrics(this, 23464);
	}

	@Override
	public LevelProviderFactory<Player> levelProviderFactory() {
		return this.levelProviderFactory;
	}

	@Override
	public void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		manager.registerEvents(new PlayerListener(this.operatorLevel), this);
	}

	@Override
	public void updateAll() {
		getServer().getOnlinePlayers().forEach(this.operatorLevel::updateLevel);
	}

	@Override
	public Player player(UUID uuid) {
		return getServer().getPlayer(uuid);
	}

	@Override
	public Logger logger() {
		return this.getSLF4JLogger();
	}

	@Override
	public Path dataPath() {
		//TODO Switch to this.getDataPath() when minimum version becomes 1.21
		return this.getDataFolder().toPath();
	}
}
