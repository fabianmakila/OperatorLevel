package fi.fabianadrian.operatorlevel.common.level;

public interface LevelProvider<P> {
	int level(P player);
}
