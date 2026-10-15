package com.enlil.universe.civilization.occupation;

import com.enlil.universe.civilization.science.Science;

import java.util.Set;

/** An unarmed occupation, serving people rather than fighting them: doctors, teachers, firefighters... */
public abstract class CivilOccupation extends Occupation {

    protected CivilOccupation(String name, OccupationGroup group, Set<Science> sciences, int sinceYear) {
        super(name, group, sciences, sinceYear);
    }

    protected CivilOccupation(String name, OccupationGroup group, Set<Science> sciences, int sinceYear,
                              int untilYear) {
        super(name, group, sciences, sinceYear, untilYear);
    }

    @Override
    public boolean isArmed() {
        return false;
    }
}
