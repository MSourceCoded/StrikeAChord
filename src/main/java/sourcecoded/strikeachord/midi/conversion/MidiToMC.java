package sourcecoded.strikeachord.midi.conversion;

import javax.sound.midi.MidiMessage;

public class MidiToMC {

    public static float convertToPitch(byte[] message, Instruments inst) {
        byte reduced = (byte) (message[1] - inst.start);
        int octave = 1;

        if (reduced < 0) return 0;

        if (reduced > 11) {
            reduced = (byte) (reduced - 12);
            octave = 2;
        }

        NoteValues val = NoteValues.values()[reduced];
        return val.getPitch() * octave;
    }

}
