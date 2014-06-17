package sourcecoded.strikeachord.midi;

import sourcecoded.data.GravityBuffer;
import sourcecoded.strikeachord.eventsystem.EventBus;
import sourcecoded.strikeachord.eventsystem.events.MidiMessageScheduled;

import javax.sound.midi.MidiMessage;
import java.util.ArrayList;

public class MidiCache {

    public static GravityBuffer<MidiMessage> messages = new GravityBuffer<MidiMessage>();

    public static void addMidiMessage(MidiMessage theMessage) {
        messages.append(theMessage);
        EventBus.Publisher.raiseEvent(new MidiMessageScheduled());
    }

    public static MidiMessage retrieveOrder() {
        MidiMessage returned = null;

        if (messages.size() != 0) {
            returned = messages.retrieve();
            messages.delete();
        }

        return returned;
    }

}
