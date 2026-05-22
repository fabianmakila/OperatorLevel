package fi.fabianadrian.operatorlevel.common.listener;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.OperatorLevelPlugin;
import fi.fabianadrian.operatorlevel.common.listener.packet.PlayClientChangeGameModeListener;
import fi.fabianadrian.operatorlevel.common.listener.packet.PlayServerEntityStatusListener;

import java.util.List;

public abstract class ListenerManager<P> {
	protected final OperatorLevelPlugin<P> plugin;
	protected final OperatorLevel<P> operatorLevel;

	public ListenerManager(OperatorLevelPlugin<P> plugin, OperatorLevel<P> operatorLevel) {
		this.plugin = plugin;
		this.operatorLevel = operatorLevel;
	}

	public void registerListeners() {
		registerPlayerListener();
		registerLuckPermsListener();
		registerPacketEventsListeners();
	}

	protected abstract void registerPlayerListener();

	private void registerLuckPermsListener() {
		try {
			new LuckPermsListener(this.plugin, this.operatorLevel);
			this.plugin.logger().info("Registered LuckPerms listener");
		} catch (NoClassDefFoundError ignored) {

		}
	}

	private void registerPacketEventsListeners() {
		EventManager manager = PacketEvents.getAPI().getEventManager();
		List.of(
				new PlayClientChangeGameModeListener(this.operatorLevel),
				new PlayServerEntityStatusListener<>(this.operatorLevel)
		).forEach(listener -> manager.registerListener(listener, PacketListenerPriority.NORMAL));
	}
}
