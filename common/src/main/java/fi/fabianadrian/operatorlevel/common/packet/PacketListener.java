package fi.fabianadrian.operatorlevel.common.packet;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;

public final class PacketListener extends PacketListenerCommon implements com.github.retrooper.packetevents.event.PacketListener {
	@Override
	public PacketListenerAbstract asAbstract(PacketListenerPriority priority) {
		return com.github.retrooper.packetevents.event.PacketListener.super.asAbstract(priority);
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
		}
	}
}
