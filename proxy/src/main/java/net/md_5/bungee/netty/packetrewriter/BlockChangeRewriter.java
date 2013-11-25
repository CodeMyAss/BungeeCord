package net.md_5.bungee.netty.packetrewriter;

import io.netty.buffer.ByteBuf;
import net.md_5.bungee.netty.Var;

public class BlockChangeRewriter extends PacketRewriter
{

    @Override
    public void rewriteClientToServer(ByteBuf in, ByteBuf out)
    {
        unsupported( true );
    }

    @Override
    public void rewriteServerToClient(ByteBuf in, ByteBuf out)
    {
        int x = in.readInt();
        byte y = in.readByte();
        int z = in.readInt();
        short blockType = in.readShort();
        byte blockData = in.readByte();
        out.writeInt( x );
        out.writeByte( y );
        out.writeInt( z );
        Var.writeVarInt( blockType, out );
        out.writeByte( blockData );
    }

}
