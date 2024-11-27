package fi.fabianadrian.operatorlevel.common.packet;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;

public final class PacketListener implements com.github.retrooper.packetevents.event.PacketListener {
	@Override
	public void onPacketSend(PacketSendEvent event) {
		if (event.getPacketType() != PacketType.Play.Server.ENTITY_STATUS) {
			return;
		}

		WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(event);
		int status = packet.getStatus();
		if (status > 23 && status < 29) {
			event.setCancelled(true);
		}
	}
}
