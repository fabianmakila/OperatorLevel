package fi.fabianadrian.operatorlevel.common.packet.listener;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;

public final class PlayServerEntityStatusListener implements com.github.retrooper.packetevents.event.PacketListener {
	private final OperatorLevel<?> operatorLevel;

	public PlayServerEntityStatusListener(OperatorLevel<?> operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@Override
	public void onPacketSend(PacketSendEvent event) {
		if (event.getPacketType() != PacketType.Play.Server.ENTITY_STATUS) {
			return;
		}

		WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(event);
		int status = packet.getStatus();
		if (status > 23 && status < 29) {
			event.setCancelled(true);
			// Player is null when this packet is sent on join, we'll use the join event listener instead
			if (event.getPlayer() == null) {
				return;
			}
			this.operatorLevel.updateLevel(event.getPlayer());
		}
	}
}
