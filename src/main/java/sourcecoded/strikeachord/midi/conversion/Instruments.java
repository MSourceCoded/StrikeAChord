package sourcecoded.strikeachord.midi.conversion;

import javax.sound.midi.MidiMessage;

public enum Instruments {

    SNARE("note.snare", 90),
    HARP("note.harp", 66),
    BASS_GUITAR("note.bassattack", 42);

    String instrumentName;

    byte start;
    byte end;

    Instruments(String soundName, int start) {
        instrumentName = soundName;
        this.start = (byte) start;
        this.end = (byte) (start + 23);
    }

    public byte getInstrumentStart() {
        return start;
    }

    public String getSoundName() {
        return instrumentName;
    }

    public static Instruments getInstrumentFromMidiMessage(byte[] message) {
        byte key = message[1];

        for (Instruments instrument : Instruments.values()) {
            if (key >= instrument.start && key <= instrument.end) return instrument;
        }

        if (key > Instruments.SNARE.end) return Instruments.SNARE;
        return Instruments.BASS_GUITAR;
    }


}
