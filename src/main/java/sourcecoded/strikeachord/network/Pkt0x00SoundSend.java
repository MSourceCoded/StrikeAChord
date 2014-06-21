package sourcecoded.strikeachord.network;

import cpw.mods.fml.common.FMLCommonHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import sourcecoded.strikeachord.midi.MidiUtils;

import java.util.Arrays;

public class Pkt0x00SoundSend extends SimpleChannelInboundHandler<Pkt0x00SoundSend> implements IPacket {

    byte[] midiD;
    double px, py, pz;
    int dim;

    public Pkt0x00SoundSend() {}

    public Pkt0x00SoundSend(byte[] midi, double playerX, double playerY, double playerZ, int playerDim) {
        midiD = midi;
        this.px = playerX;
        this.py = playerY;
        this.pz = playerZ;
        this.dim = playerDim;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Pkt0x00SoundSend msg) throws Exception {
        MidiUtils.playToWorld(msg.midiD, msg.px, msg.py, msg.pz, msg.dim);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IPacket msg, ByteBuf target) throws Exception {
        target.writeInt(midiD.length);
        target.writeBytes(midiD);

        target.writeDouble(px);
        target.writeDouble(py);
        target.writeDouble(pz);

        target.writeInt(dim);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, IPacket msg) {
        int length = dat.readInt();
        midiD = new byte[length];
        dat.readBytes(midiD);

        px = dat.readDouble();
        py = dat.readDouble();
        pz = dat.readDouble();

        dim = dat.readInt();
    }
}
