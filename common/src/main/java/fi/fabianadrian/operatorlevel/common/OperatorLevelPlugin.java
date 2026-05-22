package fi.fabianadrian.operatorlevel.common;

import com.github.retrooper.packetevents.protocol.player.GameMode;
import fi.fabianadrian.operatorlevel.common.level.LevelProviderManager;
import fi.fabianadrian.operatorlevel.common.listener.ListenerManager;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public interface OperatorLevelPlugin<P> {
	Logger logger();

	Path configDirectory();

	P player(UUID uuid);

	void attemptGameModeChange(P player, GameMode gameMode);

	LevelProviderManager<P> levelProviderManager();

	ListenerManager<P> listenerManager();
}
