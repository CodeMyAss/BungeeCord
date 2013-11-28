package net.md_5.bungee.protocol.packet.protocolhack;

import io.netty.buffer.ByteBuf;
import net.md_5.bungee.protocol.packet.AbstractPacketHandler;
import net.md_5.bungee.protocol.packet.DefinedPacket;

import java.util.UUID;

public class PacketLoginSuccess extends Defined172Packet {
	String name;

	public PacketLoginSuccess(String name) {
		super(0x02);
		this.name = name;
	}

	@Override
	public void read(ByteBuf buf) {

	}

	@Override
	public void write(ByteBuf buf) {
		writeString(UUID.randomUUID().toString(), buf, true);
		writeString(name, buf, true);
	}

	@Override
	public void handle(AbstractPacketHandler handler) throws Exception {

	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "UUID:" + "cake" + ", NAME:" + name;
	}
}
