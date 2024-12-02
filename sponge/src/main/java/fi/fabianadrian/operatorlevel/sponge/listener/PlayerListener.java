package fi.fabianadrian.operatorlevel.sponge.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public final class PlayerListener {
	private final OperatorLevel<Player> operatorLevel;

	public PlayerListener(OperatorLevel<Player> operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		this.operatorLevel.updateLevel(event.player());
	}
}
