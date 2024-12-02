package fi.fabianadrian.operatorlevel.common;

import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public interface Platform<P> {
	Logger logger();

	Path configDirectory();

	P player(UUID uuid);
}
