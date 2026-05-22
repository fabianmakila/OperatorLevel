package fi.fabianadrian.operatorlevel.common.listener.packet;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;

public final class PlayServerEntityStatusListener<P> implements PacketListener {
	private final OperatorLevel<P> operatorLevel;

	public PlayServerEntityStatusListener(OperatorLevel<P> operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@Override
	public void onPacketSend(PacketSendEvent event) {
		if (event.getPacketType() != PacketType.Play.Server.ENTITY_STATUS) {
			return;
		}

		WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(event);
		int status = packet.getStatus();
		if (status >= 24 && status <= 28) {
			event.setCancelled(true);
			// Player is null when this packet is sent on join, we'll use the join event listener instead
			if (event.getPlayer() == null) {
				return;
			}
			P player = event.getPlayer();
			this.operatorLevel.updateLevel(player);
		}
	}
}
