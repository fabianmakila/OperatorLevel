package fi.fabianadrian.operatorlevel.sponge;

import com.google.inject.Inject;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderFactory;
import fi.fabianadrian.operatorlevel.common.platform.Platform;
import fi.fabianadrian.operatorlevel.sponge.level.SpongeLevelProviderFactory;
import fi.fabianadrian.operatorlevel.sponge.listener.CommandListener;
import fi.fabianadrian.operatorlevel.sponge.listener.PlayerListener;
import net.luckperms.api.LuckPerms;
import org.bstats.sponge.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Plugin("operatorlevel")
public final class OperatorLevelSponge implements Platform<Player> {
	private final PluginContainer container;
	private final Path configDir;
	private final Logger logger;
	private OperatorLevel<Player> operatorLevel;
	private LevelProviderFactory<Player> levelProviderFactory;

	@Inject
	public OperatorLevelSponge(
			PluginContainer container,
			@ConfigDir(sharedRoot = false) Path configDir,
			Metrics.Factory metricsFactory
	) {
		this.container = container;
		this.configDir = configDir;
		this.logger = LoggerFactory.getLogger("operatorlevel");

		metricsFactory.make(24064);
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		this.operatorLevel = new OperatorLevel<>(this);
		this.levelProviderFactory = new SpongeLevelProviderFactory(this.operatorLevel);

		if (Sponge.pluginManager().plugin("luckperms").isPresent()) {
			Optional<LuckPerms> luckPermsOptional = Sponge.serviceProvider().provide(LuckPerms.class);
			if (luckPermsOptional.isPresent()) {
				LuckPerms luckPerms = luckPermsOptional.get();
				this.levelProviderFactory.createLuckPermsProvider(luckPerms);
				this.operatorLevel.registerLuckPermsListener(luckPerms);
			}
		}

		registerListeners();

		this.operatorLevel.load();
	}

	@Override
	public Logger logger() {
		return this.logger;
	}

	@Override
	public Path dataPath() {
		return this.configDir;
	}

	@Override
	public LevelProviderFactory<Player> levelProviderFactory() {
		return this.levelProviderFactory;
	}

	@Override
	public void registerListeners() {
		EventManager manager = Sponge.eventManager();
		List.of(
				new CommandListener(this.container, this.operatorLevel),
				new PlayerListener(this.operatorLevel)
		).forEach(listener -> manager.registerListeners(this.container, listener));
	}

	@Override
	public void updateAll() {
		Sponge.server().onlinePlayers().forEach(this.operatorLevel::updateLevel);
	}

	@Override
	public ServerPlayer player(UUID uuid) {
		return Sponge.server().player(uuid).orElse(null);
	}
}
