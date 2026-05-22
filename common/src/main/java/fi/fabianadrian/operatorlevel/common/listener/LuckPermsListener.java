package fi.fabianadrian.operatorlevel.common.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.OperatorLevelPlugin;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;

public final class LuckPermsListener {

	public LuckPermsListener(OperatorLevelPlugin<?> plugin, OperatorLevel<?> operatorLevel) {
		EventBus eventBus = LuckPermsProvider.get().getEventBus();
		eventBus.subscribe(plugin, UserDataRecalculateEvent.class, event -> operatorLevel.updateLevel(event.getUser().getUniqueId()));
	}
}
