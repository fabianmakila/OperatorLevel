package fi.fabianadrian.operatorlevel.common.platform;

import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public interface Platform<P> {
	Logger logger();

	/**
	 * @return the plugin's data directory
	 */
	Path dataPath();

	void updateOpLevel(P player);

	/**
	 * This is only called from LuckPermsManager
	 *
	 * @param uuid UUID of the player
	 * @param level OP level that will be sent to the player
	 */
	void updateOpLevel(UUID uuid, byte level);
}
