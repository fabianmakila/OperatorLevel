package fi.fabianadrian.operatorlevel.sponge17.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.listener.ListenerManager;
import fi.fabianadrian.operatorlevel.sponge17.OperatorLevelSponge;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.plugin.PluginContainer;

import java.lang.invoke.MethodHandles;

public final class SpongeListenerManager extends ListenerManager<ServerPlayer> {
	private final PluginContainer container;

	public SpongeListenerManager(OperatorLevelSponge plugin, OperatorLevel<ServerPlayer> operatorLevel, PluginContainer container) {
		super(plugin, operatorLevel);
		this.container = container;
	}

	@Override
	protected void registerPlayerListener() {
		EventManager manager = Sponge.eventManager();
		manager.registerListeners(this.container, new PlayerListener(this.operatorLevel), MethodHandles.lookup());
	}
}
