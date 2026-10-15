package com.enlil.universe.civilization.occupation;

import com.enlil.universe.civilization.science.Science;
import lombok.Getter;

import java.util.Set;

/**
 * A warrior class rather than a mere job: fighting men bound by a code of honour, sometimes by birth (the samurai),
 * sometimes by an accolade (the knight).
 */
@Getter
public abstract class Warrior extends ArmedOccupation {

    private final String codeOfHonour;
    private final boolean hereditary;

    protected Warrior(String name, int sinceYear, int untilYear, Set<Weapon> weapons, String codeOfHonour,
                      boolean hereditary) {
        super(name, OccupationGroup.ARMED_FORCES, Set.<Science>of(), sinceYear, untilYear, Force.MILITARY, weapons);
        this.codeOfHonour = codeOfHonour;
        this.hereditary = hereditary;
    }
}
