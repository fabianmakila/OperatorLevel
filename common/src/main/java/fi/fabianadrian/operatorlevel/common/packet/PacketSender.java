package fi.fabianadrian.operatorlevel.common.packet;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;

public final class PacketSender {
	public void sendPacket(Object player, int level) {
		User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
		if (user == null) {
			return;
		}

		WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(user.getEntityId(), 24 + level);
		user.sendPacketSilently(packet);
	}
}
