package fi.fabianadrian.operatorlevel.common;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import fi.fabianadrian.operatorlevel.common.config.ConfigManager;
import fi.fabianadrian.operatorlevel.common.config.OperatorLevelConfig;
import fi.fabianadrian.operatorlevel.common.locale.TranslationManager;
import fi.fabianadrian.operatorlevel.common.packet.PacketSender;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public final class OperatorLevel<P> {
	private final PacketSender packetSender = new PacketSender();
	private final OperatorLevelPlugin<P> plugin;
	private final TranslationManager translationManager;
	private final ConfigManager configManager;

	public OperatorLevel(OperatorLevelPlugin<P> plugin) {
		this.plugin = plugin;

		this.translationManager = new TranslationManager(this);
		this.configManager = new ConfigManager(this);
	}

	public void start() {
		this.plugin.listenerManager().registerListeners();
		load();
	}

	public void load() {
		this.translationManager.load();
		this.configManager.load();
		this.plugin.levelProviderManager().load();
	}

	public OperatorLevelConfig config() {
		return this.configManager.config();
	}

	public Logger logger() {
		return this.plugin.logger();
	}

	public Path configDirectory() {
		return this.plugin.configDirectory();
	}

	public void updateLevel(UUID uuid) {
		updateLevel(this.plugin.player(uuid));
	}

	public void updateLevel(P player) {
		int level = this.plugin.levelProviderManager().provider().level(player);
		this.packetSender.sendPacket(player, level);
	}

	public void attemptGameModeChange(P player, GameMode gameMode) {
		this.plugin.attemptGameModeChange(player, gameMode);
	}
}
