package fi.fabianadrian.operatorlevel.paper;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import dev.faststats.bukkit.BukkitContext;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.OperatorLevelPlugin;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderManager;
import fi.fabianadrian.operatorlevel.common.listener.ListenerManager;
import fi.fabianadrian.operatorlevel.paper.command.PaperOperatorLevelCommandBrigadier;
import fi.fabianadrian.operatorlevel.paper.level.PaperLevelProviderManager;
import fi.fabianadrian.operatorlevel.paper.listener.PaperListenerManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Locale;
import java.util.UUID;

public final class OperatorLevelPaper extends JavaPlugin implements OperatorLevelPlugin<Player> {
	public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();
	private final OperatorLevel<Player> operatorLevel;
	private final PaperLevelProviderManager levelProviderManager;
	private final PaperListenerManager listenerManager;
	private final BukkitContext context;

	public OperatorLevelPaper() {
		this.operatorLevel = new OperatorLevel<>(this);
		this.listenerManager = new PaperListenerManager(this, this.operatorLevel);
		this.levelProviderManager = new PaperLevelProviderManager(this.operatorLevel);
		this.context = new BukkitContext.Factory(this, "0b0987345c22b4cdcbf5d606315abf17")
				.errorTrackerService(ERROR_TRACKER)
				.metrics(Metrics.Factory::create)
				.create();
	}

	@Override
	public void onEnable() {
		this.context.ready();
		this.operatorLevel.start();

		getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
			commands.registrar().register(PaperOperatorLevelCommandBrigadier.create(this));
		});
	}

	@Override
	public void onDisable() {
		this.context.shutdown();
	}

	@Override
	public Logger logger() {
		return getSLF4JLogger();
	}

	@Override
	public Path configDirectory() {
		return getDataPath();
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

	@Override
	public LevelProviderManager<Player> levelProviderManager() {
		return this.levelProviderManager;
	}

	@Override
	public ListenerManager<Player> listenerManager() {
		return this.listenerManager;
	}

	public void reload() {
		this.operatorLevel.load();
		getServer().getOnlinePlayers().forEach(this.operatorLevel::updateLevel);
	}
}
