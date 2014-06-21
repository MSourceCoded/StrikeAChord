package sourcecoded.strikeachord.midi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import sourcecoded.strikeachord.StrikeAChord;
import sourcecoded.strikeachord.eventsystem.EventBus;
import sourcecoded.strikeachord.eventsystem.events.MidiMessageScheduled;
import sourcecoded.strikeachord.network.Pkt0x00SoundSend;
import sourcecoded.strikeachord.network.SACPacketPipeline;

import javax.sound.midi.*;
import java.util.Arrays;

public class MidiUtils {

    public static MidiDevice inputDevice;

    public static volatile boolean refresh = false;

    public static Thread refreshThread;

    static Sequencer sequencer;
    static Transmitter transmitter;
    static Receiver receiver;

    public static volatile Track theTrack;

    public static MidiDevice.Info[] getInfoList() {
        return MidiSystem.getMidiDeviceInfo();
    }

    public static void setInputDevice(MidiDevice.Info info) {
        try {
            inputDevice = MidiSystem.getMidiDevice(info);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            sequencer = MidiSystem.getSequencer();

            inputDevice.open();
            sequencer.open();
            transmitter = inputDevice.getTransmitter();
            receiver = sequencer.getReceiver();

            transmitter.setReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void startRecording() {
        try {
            Sequence seq = new Sequence(Sequence.PPQ, 24);
            theTrack = seq.createTrack();

            sequencer.setSequence(seq);

            sequencer.setTickPosition(0);
            sequencer.recordEnable(theTrack, -1);

            sequencer.startRecording();

            refresh = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopRecording() {
        sequencer.stopRecording();
        refresh = false;
                    }

                public static void setRefresh(boolean val) {
                    refresh = val;
                }

                public static void doRefresh() {
                    refreshThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Check the Track Bank here
                            while (refresh) {
                                if (theTrack.size() != 0) {
                                    MidiEvent event = theTrack.get(0);
                                    theTrack.remove(event);

                                    MidiMessage currentMessage = event.getMessage();

                        EventBus.Publisher.raiseEvent(new MidiMessageScheduled(currentMessage));

                        if(currentMessage.getMessage()[0] == -112) {
                            //WorldCache.cachedWorld.playSoundEffect(StrikeAChord.proxy.getClientPlayer().posX, StrikeAChord.proxy.getClientPlayer().posY, StrikeAChord.proxy.getClientPlayer().posZ, "note.harp", 3.0F, (float) (currentMessage.getMessage()[1] - 36) / 24);
                            StrikeAChord.proxy.getClientPlayer().playSound("note.harp", 3.0F, (float) (currentMessage.getMessage()[1] - 36) / 24);
                            SACPacketPipeline.INSTANCE.sendToServer(new Pkt0x00SoundSend(currentMessage.getMessage(), StrikeAChord.proxy.getClientPlayer().posX, StrikeAChord.proxy.getClientPlayer().posY, StrikeAChord.proxy.getClientPlayer().posZ, StrikeAChord.proxy.getClientPlayer().dimension));
                            //Do the server ping to make sure not to play twice
                        }
                    }

                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        refreshThread.start();
    }

    public static void playToWorld(byte[] message, double x, double y, double z, int dim) {
        WorldServer wrld = MinecraftServer.getServer().worldServerForDimension(dim);

        float pitch = (float) (message[1] - 36) / 24;

        if (wrld != null) {
            wrld.playSoundEffect(x, y, z, "note.harp", 3.0F, pitch);
        }

    }


}
