package fi.fabianadrian.operatorlevel.sponge.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityWorldEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public final class PlayerListener {
	private final OperatorLevel<ServerPlayer> operatorLevel;

	public PlayerListener(OperatorLevel<ServerPlayer> operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		this.operatorLevel.updateLevel(event.player());
	}

	@Listener
	public void onWorldChange(ChangeEntityWorldEvent.Post event) {
		if (!(event.entity() instanceof ServerPlayer player)) {
			return;
		}

		this.operatorLevel.updateLevel(player);
	}
}
