package fi.fabianadrian.operatorlevel.common.platform;

import fi.fabianadrian.operatorlevel.common.level.LevelProviderFactory;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public interface Platform<P> {
	Logger logger();

	Path dataPath();

	LevelProviderFactory<P> levelProviderFactory();

	void registerListeners();

	void updateAll();

	P player(UUID uuid);
}
