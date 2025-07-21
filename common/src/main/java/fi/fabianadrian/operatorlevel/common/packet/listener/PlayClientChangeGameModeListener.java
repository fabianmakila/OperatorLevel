package fi.fabianadrian.operatorlevel.common.packet.listener;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChangeGameMode;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;

public final class PlayClientChangeGameModeListener implements com.github.retrooper.packetevents.event.PacketListener {
	private final OperatorLevel<?> operatorLevel;

	public PlayClientChangeGameModeListener(OperatorLevel<?> operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@Override
	public void onPacketReceive(PacketReceiveEvent event) {
		if (event.getPacketType() != PacketType.Play.Client.CHANGE_GAME_MODE) {
			return;
		}

		event.setCancelled(true);

		WrapperPlayClientChangeGameMode packet = new WrapperPlayClientChangeGameMode(event);
		this.operatorLevel.attemptGameModeChange(event.getPlayer(), packet.getGameMode());
	}
}
