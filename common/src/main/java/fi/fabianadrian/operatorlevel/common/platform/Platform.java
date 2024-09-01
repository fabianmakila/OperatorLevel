package fi.fabianadrian.operatorlevel.common.platform;

import fi.fabianadrian.operatorlevel.common.luckperms.LuckPermsManager;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public interface Platform<P> {
	Logger logger();

	Path dataPath();

	LuckPermsManager luckPermsManager();

	void updateOpLevel(P player);

	void updateOpLevel(UUID uuid, byte level);
}
