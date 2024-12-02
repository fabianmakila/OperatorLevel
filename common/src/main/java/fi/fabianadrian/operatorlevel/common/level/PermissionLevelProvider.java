package fi.fabianadrian.operatorlevel.common.level;

import java.util.function.BiFunction;

public final class PermissionLevelProvider<P> implements LevelProvider<P> {
	private final BiFunction<P, String, Boolean> permissionChecker;

	public PermissionLevelProvider(BiFunction<P, String, Boolean> permissionChecker) {
		this.permissionChecker = permissionChecker;
	}

	@Override
	public int level(P player) {
		int level = 0;
		for (int i = 4; i > 0; i--) {
			if (this.permissionChecker.apply(player, "operatorlevel.level." + i)) {
				level = i;
				break;
			}
		}
		return level;
	}
}
