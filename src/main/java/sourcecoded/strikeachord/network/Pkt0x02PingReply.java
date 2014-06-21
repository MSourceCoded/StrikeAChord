package sourcecoded.strikeachord.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sourcecoded.strikeachord.StrikeAChord;

public class Pkt0x02PingReply extends SimpleChannelInboundHandler<Pkt0x02PingReply> implements IPacket {

    public Pkt0x02PingReply() {
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf target) throws Exception {
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IPacket msg) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Pkt0x02PingReply msg) throws Exception {
        StrikeAChord.canTakePackets = true;
    }
}
