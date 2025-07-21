package fi.fabianadrian.operatorlevel.paper;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.Platform;
import fi.fabianadrian.operatorlevel.paper.command.PaperOperatorLevelCommand;
import fi.fabianadrian.operatorlevel.paper.listener.PlayerListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Locale;
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

	@Override
	public void attemptGameModeChange(Player player, GameMode gameMode) {
		org.bukkit.GameMode bukkitGameMode = org.bukkit.GameMode.valueOf(gameMode.toString().toUpperCase(Locale.ROOT));

		Bukkit.getScheduler().runTask(this, () -> {
			if (!player.hasPermission("operatorlevel.gamemode." + bukkitGameMode.toString().toLowerCase(Locale.ROOT))) {
				player.sendMessage(Component.translatable()
						.key("operatorlevel.gamemode.no-permission")
						.arguments(Argument.component("gamemode", Component.translatable(bukkitGameMode.translationKey())))
				);
				return;
			}

			player.setGameMode(bukkitGameMode);
			player.sendMessage(Component.translatable()
					.key("operatorlevel.gamemode")
					.arguments(Argument.component("gamemode", Component.translatable(bukkitGameMode.translationKey())))
			);
		});
	}

	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerListener(this.operatorLevel), this);
	}

	public void reload() {
		this.operatorLevel.reload();
		getServer().getOnlinePlayers().forEach(this.operatorLevel::updateLevel);
	}
}
