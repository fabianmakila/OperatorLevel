package fi.fabianadrian.operatorlevel.sponge17;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.google.inject.Inject;
import dev.faststats.core.ErrorTracker;
import dev.faststats.core.Metrics;
import dev.faststats.sponge.SpongeMetrics;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.OperatorLevelPlugin;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderManager;
import fi.fabianadrian.operatorlevel.common.listener.ListenerManager;
import fi.fabianadrian.operatorlevel.sponge17.command.SpongeOperatorLevelCommand;
import fi.fabianadrian.operatorlevel.sponge17.level.SpongeLevelProviderManager;
import fi.fabianadrian.operatorlevel.sponge17.listener.SpongeListenerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;

import java.nio.file.Path;
import java.util.Locale;
import java.util.UUID;

@org.spongepowered.plugin.builtin.jvm.Plugin("operatorlevel")
public final class OperatorLevelSponge implements OperatorLevelPlugin<ServerPlayer> {
	private final PluginContainer container;
	private final OperatorLevel<ServerPlayer> operatorLevel;
	private final Logger logger;
	private final Path configDirectory;
	private final SpongeLevelProviderManager levelProviderManager;
	private final SpongeMetrics.Factory metricsFactory;
	private final ErrorTracker errorTracker = ErrorTracker.contextAware();
	private final SpongeListenerManager listenerManager;
	private Metrics metrics;

	@Inject
	public OperatorLevelSponge(
			PluginContainer container,
			@ConfigDir(sharedRoot = false) Path configDirectory,
			SpongeMetrics.Factory metricsFactory
	) {
		this.container = container;
		this.configDirectory = configDirectory;
		this.logger = LoggerFactory.getLogger("operatorlevel");

		this.operatorLevel = new OperatorLevel<>(this);

		this.listenerManager = new SpongeListenerManager(this, this.operatorLevel, container);
		this.levelProviderManager = new SpongeLevelProviderManager(this.operatorLevel);

		this.metricsFactory = metricsFactory;
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		this.metrics = this.metricsFactory
				.errorTracker(this.errorTracker)
				.create(this.container);

		this.operatorLevel.start();
	}

	@Listener
	public void onServerStop(final StoppingEngineEvent<Server> event) {
		if (this.metrics != null) {
			this.metrics.shutdown();
		}
	}

	@Listener
	public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
		event.register(this.container, new SpongeOperatorLevelCommand(this).command(), "operatorlevel");
	}

	@Override
	public Logger logger() {
		return this.logger;
	}

	@Override
	public Path configDirectory() {
		return this.configDirectory;
	}

	@Override
	public ServerPlayer player(UUID uuid) {
		return Sponge.server().player(uuid).orElse(null);
	}

	@Override
	public void attemptGameModeChange(ServerPlayer player, GameMode gameMode) {
		org.spongepowered.api.entity.living.player.gamemode.GameMode spongeGameMode = GameModes.registry().findValue(ResourceKey.minecraft(gameMode.toString().toLowerCase(Locale.ROOT))).orElseThrow(() -> new IllegalStateException("Unknown gamemode"));
		Sponge.server().scheduler().submit(Task.builder().plugin(this.container).execute(() -> {
			if (!player.hasPermission("operatorlevel.gamemode." + spongeGameMode.toString().toLowerCase(Locale.ROOT))) {
				player.sendMessage(Component.translatable()
						.key("operatorlevel.gamemode.no-permission")
						.arguments(Argument.component("gamemode", spongeGameMode.asComponent()))
				);
				return;
			}

			player.gameMode().set(spongeGameMode);
			player.sendMessage(Component.translatable()
					.key("operatorlevel.gamemode")
					.arguments(Argument.component("gamemode", spongeGameMode.asComponent()))
			);
		}).build());
	}

	@Override
	public LevelProviderManager<ServerPlayer> levelProviderManager() {
		return this.levelProviderManager;
	}

	@Override
	public ListenerManager<ServerPlayer> listenerManager() {
		return this.listenerManager;
	}

	public void reload() {
		this.operatorLevel.load();
		Sponge.server().onlinePlayers().forEach(this.operatorLevel::updateLevel);
	}
}
