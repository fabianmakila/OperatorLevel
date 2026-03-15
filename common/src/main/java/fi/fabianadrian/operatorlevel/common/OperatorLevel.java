package fi.fabianadrian.operatorlevel.common;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import fi.fabianadrian.operatorlevel.common.config.ConfigManager;
import fi.fabianadrian.operatorlevel.common.config.OperatorLevelConfig;
import fi.fabianadrian.operatorlevel.common.locale.TranslationManager;
import fi.fabianadrian.operatorlevel.common.packet.PacketSender;
import fi.fabianadrian.operatorlevel.common.packet.listener.PlayClientChangeGameModeListener;
import fi.fabianadrian.operatorlevel.common.packet.listener.PlayServerEntityStatusListener;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;

public final class OperatorLevel<P> {
	private final PacketSender packetSender = new PacketSender();
	private final Platform<P> platform;
	private final TranslationManager translationManager;
	private final ConfigManager configManager;

	public OperatorLevel(Platform<P> platform) {
		this.platform = platform;

		this.translationManager = new TranslationManager(this);
		this.configManager = new ConfigManager(this);
	}

	public void load() {
		this.translationManager.load();
		this.configManager.load();
		this.platform.levelProviderManager().load();
	}

	public OperatorLevelConfig config() {
		return this.configManager.config();
	}

	public Logger logger() {
		return this.platform.logger();
	}

	public Path configDirectory() {
		return this.platform.configDirectory();
	}

	public void updateLevel(P player) {
		int level = this.platform.levelProviderManager().provider().level(player);
		this.packetSender.sendPacket(player, level);
	}

	public void attemptGameModeChange(P player, GameMode gameMode) {
		this.platform.attemptGameModeChange(player, gameMode);
	}

	public void registerPacketEventsListeners() {
		EventManager manager = PacketEvents.getAPI().getEventManager();
		List.of(
				new PlayClientChangeGameModeListener(this),
				new PlayServerEntityStatusListener(this)
		).forEach(listener -> manager.registerListener(listener, PacketListenerPriority.NORMAL));
	}
}
