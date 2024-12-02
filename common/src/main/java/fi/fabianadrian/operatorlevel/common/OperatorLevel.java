package fi.fabianadrian.operatorlevel.common;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import fi.fabianadrian.operatorlevel.common.config.ConfigManager;
import fi.fabianadrian.operatorlevel.common.config.OperatorLevelConfig;
import fi.fabianadrian.operatorlevel.common.level.LevelProvider;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderFactory;
import fi.fabianadrian.operatorlevel.common.listener.LuckPermsListener;
import fi.fabianadrian.operatorlevel.common.locale.TranslationManager;
import fi.fabianadrian.operatorlevel.common.packet.PacketListener;
import fi.fabianadrian.operatorlevel.common.packet.PacketSender;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.slf4j.Logger;

import java.util.function.BiFunction;

public final class OperatorLevel<P> {
	private final ConfigManager<OperatorLevelConfig> configManager;
	private final PacketSender packetSender = new PacketSender();
	private final PacketListener packetListener = new PacketListener();
	private final Platform<P> platform;
	private LevelProviderFactory<P> levelProviderFactory;
	private LevelProvider<P> levelProvider;

	public OperatorLevel(Platform<P> platform) {
		this.platform = platform;

		new TranslationManager(platform.logger());

		this.configManager = ConfigManager.create(
				platform.configDirectory(),
				"config.yml",
				OperatorLevelConfig.class,
				platform.logger()
		);
		this.configManager.load();
	}

	public void startup() {
		try {
			LuckPerms luckPerms = LuckPermsProvider.get();
			this.levelProviderFactory.createLuckPermsProvider(luckPerms);
			new LuckPermsListener<>(this.platform, luckPerms, this);
		} catch (NoClassDefFoundError ignored) {

		}

		this.levelProvider = levelProviderFactory.levelProvider();
		PacketEvents.getAPI().getEventManager().registerListener(this.packetListener, PacketListenerPriority.NORMAL);
	}

	public void shutdown() {
		PacketEvents.getAPI().getEventManager().unregisterListener(this.packetListener);
	}

	public void createLevelProviderFactory(BiFunction<P, String, Boolean> permissionChecker, Class<P> playerClass) {
		this.levelProviderFactory = new LevelProviderFactory<>(this, permissionChecker, playerClass);
	}

	public OperatorLevelConfig config() {
		return this.configManager.config();
	}

	public void reload() {
		this.configManager.load();
		this.levelProvider = this.levelProviderFactory.levelProvider();
	}

	public Logger logger() {
		return this.platform.logger();
	}

	public void updateLevel(P player) {
		int level = this.levelProvider.level(player);
		this.packetSender.sendPacket(player, level);
	}
}
