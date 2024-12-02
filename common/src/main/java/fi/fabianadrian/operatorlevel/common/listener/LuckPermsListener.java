package fi.fabianadrian.operatorlevel.common.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.Platform;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;

public final class LuckPermsListener<P> {
	private final Platform<P> platform;
	private final OperatorLevel<P> operatorLevel;

	public LuckPermsListener(Platform<P> platform, LuckPerms luckPerms, OperatorLevel<P> operatorLevel) {
		this.platform = platform;
		this.operatorLevel = operatorLevel;

		EventBus eventBus = luckPerms.getEventBus();
		eventBus.subscribe(platform, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
	}

	private void onUserDataRecalculate(UserDataRecalculateEvent event) {
		P player = this.platform.player(event.getUser().getUniqueId());
		if (player == null) {
			return;
		}

		this.operatorLevel.updateLevel(player);
	}
}
