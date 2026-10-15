package com.enlil.universe.life.human.activity;

import lombok.Getter;

/** Playing a musical instrument, named after it: "Playing guitar". */
@Getter
public class MusicalPractice extends Activity {

    private final String instrument;
    private final InstrumentFamily instrumentFamily;

    public MusicalPractice(String instrument, InstrumentFamily instrumentFamily, double metabolicEquivalent) {
        super("Playing " + instrument, metabolicEquivalent);
        this.instrument = instrument;
        this.instrumentFamily = instrumentFamily;
    }

    @Override
    public ActivityCategory getCategory() {
        return ActivityCategory.MUSIC;
    }
}
