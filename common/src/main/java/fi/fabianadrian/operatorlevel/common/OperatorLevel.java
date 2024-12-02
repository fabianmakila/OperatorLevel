package fi.fabianadrian.operatorlevel.common;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import fi.fabianadrian.operatorlevel.common.config.ConfigManager;
import fi.fabianadrian.operatorlevel.common.config.OperatorLevelConfig;
import fi.fabianadrian.operatorlevel.common.level.LevelProvider;
import fi.fabianadrian.operatorlevel.common.listener.LuckPermsListener;
import fi.fabianadrian.operatorlevel.common.locale.TranslationManager;
import fi.fabianadrian.operatorlevel.common.packet.PacketListener;
import fi.fabianadrian.operatorlevel.common.packet.PacketSender;
import fi.fabianadrian.operatorlevel.common.platform.Platform;
import net.luckperms.api.LuckPerms;
import org.slf4j.Logger;

public final class OperatorLevel<P> {
	private final ConfigManager<OperatorLevelConfig> configManager;
	private final PacketSender packetSender = new PacketSender();
	private final Platform<P> platform;
	private LevelProvider<P> levelProvider;

	public OperatorLevel(Platform<P> platform) {
		this.platform = platform;

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

	public void load() {
		this.configManager.load();
		this.levelProvider = this.platform.levelProviderFactory().levelProvider();
		this.platform.updateAll();
	}

	public Logger logger() {
		return this.platform.logger();
	}

	public void updateLevel(P player) {
		int level = this.levelProvider.level(player);
		this.packetSender.sendPacket(player, level);
	}

	public void registerLuckPermsListener(LuckPerms luckPerms) {
		new LuckPermsListener<>(this.platform, luckPerms, this);
	}
}
