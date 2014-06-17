package sourcecoded.strikeachord.eventsystem.events;

import sourcecoded.strikeachord.eventsystem.IEvent;

import javax.sound.midi.MidiMessage;

public class MidiMessageScheduled implements IEvent {

    public MidiMessage message;

    public MidiMessageScheduled(MidiMessage message) {
        this.message = message;
    }

}
