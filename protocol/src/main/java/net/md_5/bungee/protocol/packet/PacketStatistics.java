package net.md_5.bungee.protocol.packet;

import java.nio.charset.Charset;

import net.md_5.bungee.protocol.packet.protocolhack.Defined172Packet;
import io.netty.buffer.ByteBuf;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class PacketStatistics extends DefinedPacket {

	private int aid;
	private int value;

	private PacketStatistics() {
		super(0xC8);
	}

	public PacketStatistics(int aid, int value) {
		this();
		this.aid = aid;
		this.value = value;
	}

	@Override
	public void read(ByteBuf buf) {
	}

	@Override
	public void write(ByteBuf buf) {
		buf.writeInt(aid);
		buf.writeInt(value);

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
		return aid + ":" + value;
	}
}
