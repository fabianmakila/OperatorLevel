package fi.fabianadrian.operatorlevel.common.platform;

import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public interface Platform<P> {
	Logger logger();

	Path dataPath();

	void updateOpLevel(P player);

	void updateOpLevel(UUID uuid, byte level);
}
