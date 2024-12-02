package fi.fabianadrian.operatorlevel.common.level;

public abstract class PermissionLevelProvider<P> implements LevelProvider<P> {
	@Override
	public int level(P player) {
		int level = 0;
		for (int i = 4; i > 0; i--) {
			if (hasPermission(player, "operatorlevel.level." + i)) {
				level = i;
				break;
			}
		}
		return level;
	}

	protected abstract boolean hasPermission(P player, String permission);
}
