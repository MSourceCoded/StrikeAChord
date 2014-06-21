package sourcecoded.strikeachord.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.EnumMap;

public enum SACPacketPipeline {

    INSTANCE;

    public EnumMap<Side, FMLEmbeddedChannel> channels;

    private SACPacketPipeline() {
        this.channels = NetworkRegistry.INSTANCE.newChannel("SC|SAC", new SACNetworkCodec());
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            addClientHandler();
            addServerHandler();
        } else {
            addServerHandler();
        }
    }

    public void init() {
    }

    private void addClientHandler() {
        FMLEmbeddedChannel channel = this.channels.get(Side.CLIENT);
        String codec = channel.findChannelHandlerNameForType(SACNetworkCodec.class);

        channel.pipeline().addAfter(codec, "PingReply", new Pkt0x02PingReply());
    }

    private void addServerHandler() {
        FMLEmbeddedChannel channel = this.channels.get(Side.SERVER);
        String codec = channel.findChannelHandlerNameForType(SACNetworkCodec.class);

        channel.pipeline().addAfter(codec, "SoundSend", new Pkt0x00SoundSend());
        channel.pipeline().addAfter("SoundSend", "PingRequest", new Pkt0x01Ping());
    }

    private class SACNetworkCodec extends FMLIndexedMessageToMessageCodec<IPacket> {

        public SACNetworkCodec() {
            addDiscriminator(0x00, Pkt0x00SoundSend.class);
            addDiscriminator(0x01, Pkt0x01Ping.class);
            addDiscriminator(0x02, Pkt0x02PingReply.class);
        }

        @Override
        public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf target) throws Exception {
            msg.encodeInto(ctx, msg, target);
        }

        @Override
        public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IPacket msg) {
            msg.decodeInto(ctx, dat, msg);
        }
    }

    public void sendTo(IPacket message, EntityPlayerMP player){
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    public void sendToServer(IPacket message){
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

}
