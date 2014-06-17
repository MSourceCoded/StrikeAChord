package sourcecoded.strikeachord.midi;

import javax.sound.midi.*;

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
                        MidiCache.addMidiMessage(currentMessage);

                        //Handle the track here. DON'T FORGET TO REMOVE IT FROM THE BUFFER
                    }

                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        refreshThread.start();
    }


}
