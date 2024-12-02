package fi.fabianadrian.operatorlevel.paper;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.Platform;
import fi.fabianadrian.operatorlevel.paper.command.PaperOperatorLevelCommand;
import fi.fabianadrian.operatorlevel.paper.listener.PlayerListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public final class OperatorLevelPaper extends JavaPlugin implements Platform<Player> {
	private OperatorLevel<Player> operatorLevel;

	@Override
	public void onEnable() {
		this.operatorLevel = new OperatorLevel<>(this);
		this.operatorLevel.createLevelProviderFactory(Player::hasPermission, Player.class);
		this.operatorLevel.startup();

		PaperOperatorLevelCommand operatorLevelCommand = new PaperOperatorLevelCommand(this);
		operatorLevelCommand.register();

		registerListeners();

		new Metrics(this, 23464);
	}

	@Override
	public void onDisable() {
		this.operatorLevel.shutdown();
	}

	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this.operatorLevel), this);
	}

	public void reload() {
		this.operatorLevel.reload();
		getServer().getOnlinePlayers().forEach(this.operatorLevel::updateLevel);
	}

	@Override
	public Logger logger() {
		return getSLF4JLogger();
	}

	@Override
	public Path configDirectory() {
		return getDataFolder().toPath();
	}

	@Override
	public Player player(UUID uuid) {
		return getServer().getPlayer(uuid);
	}
}
