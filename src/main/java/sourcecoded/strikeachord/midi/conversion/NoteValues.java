package sourcecoded.strikeachord.midi.conversion;

public enum NoteValues {

    F_SHARP(0.5F),
    G(0.5297316F),
    G_SHARP(0.561231F),
    A(0.59460354F),
    A_SHARP(0.62996054F),
    B(0.6674199F),
    C(0.70710677F),
    C_SHARP(0.74915355F),
    D(0.7937005F),
    D_SHARP(0.8408964F),
    E(0.8908987F),
    F(0.9438743F);

    float pitchValue;

    NoteValues(float pitch) {
        this.pitchValue = pitch;
    }

    public float getPitch() {
        return pitchValue;
    }

}
