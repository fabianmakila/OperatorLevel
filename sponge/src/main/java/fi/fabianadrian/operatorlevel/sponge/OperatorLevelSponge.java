package fi.fabianadrian.operatorlevel.sponge;

import com.google.inject.Inject;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.Platform;
import fi.fabianadrian.operatorlevel.sponge.command.SpongeOperatorLevelCommand;
import fi.fabianadrian.operatorlevel.sponge.listener.PlayerListener;
import org.bstats.sponge.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;
import java.util.UUID;

@Plugin("operatorlevel")
public final class OperatorLevelSponge implements Platform<Player> {
	private final PluginContainer container;
	private final OperatorLevel<Player> operatorLevel;
	private final Logger logger;
	private final Path configDirectory;

	@Inject
	public OperatorLevelSponge(
			PluginContainer container,
			@ConfigDir(sharedRoot = false) Path configDirectory,
			Metrics.Factory metricsFactory
	) {
		this.container = container;
		this.configDirectory = configDirectory;
		this.logger = LoggerFactory.getLogger("operatorlevel");

		this.operatorLevel = new OperatorLevel<>(this);
		this.operatorLevel.createLevelProviderFactory(
				(player, permission) -> ((ServerPlayer) player).hasPermission(permission),
				Player.class
		);

		metricsFactory.make(24064);
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		this.operatorLevel.startup();
		registerListeners();
	}

	@Listener
	public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
		event.register(this.container, new SpongeOperatorLevelCommand(this).command(), "operatorlevel");
	}

	public void registerListeners() {
		Sponge.eventManager().registerListeners(this.container, new PlayerListener(this.operatorLevel));
	}

	public void reload() {
		this.operatorLevel.reload();
		Sponge.server().onlinePlayers().forEach(this.operatorLevel::updateLevel);
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
}
