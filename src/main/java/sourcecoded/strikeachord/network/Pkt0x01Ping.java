package sourcecoded.strikeachord.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Pkt0x01Ping extends SimpleChannelInboundHandler<Pkt0x01Ping> implements IPacket {

    public Pkt0x01Ping() {
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf target) throws Exception {
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IPacket msg) {
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Pkt0x01Ping msg) throws Exception {
        ctx.writeAndFlush(new Pkt0x02PingReply()).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}
