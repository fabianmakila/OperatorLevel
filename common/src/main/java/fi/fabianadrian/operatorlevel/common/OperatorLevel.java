package fi.fabianadrian.operatorlevel.common;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import fi.fabianadrian.operatorlevel.common.config.ConfigManager;
import fi.fabianadrian.operatorlevel.common.config.OperatorLevelConfig;
import fi.fabianadrian.operatorlevel.common.locale.TranslationManager;
import fi.fabianadrian.operatorlevel.common.packet.PacketListener;
import fi.fabianadrian.operatorlevel.common.platform.Platform;

public final class OperatorLevel {
	private final ConfigManager<OperatorLevelConfig> configManager;

	public OperatorLevel(Platform<?> platform) {
		new TranslationManager(platform.logger());

		this.configManager = ConfigManager.create(
				platform.dataPath(),
				"config.yml",
				OperatorLevelConfig.class,
				platform.logger()
		);

		PacketEvents.getAPI().getEventManager().registerListener(new PacketListener(), PacketListenerPriority.NORMAL);
	}

	public OperatorLevelConfig config() {
		return this.configManager.config();
	}

	public void reload() {
		this.configManager.load();
	}

	public void sendPacket(Object player, int level) {
		User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
		WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(user.getEntityId(), 24 + level);
		user.sendPacketSilently(packet);
	}
}
