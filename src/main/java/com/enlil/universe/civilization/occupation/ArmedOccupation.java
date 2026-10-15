package com.enlil.universe.civilization.occupation;

import com.enlil.universe.civilization.science.Science;
import lombok.Getter;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/** An occupation carrying weapons, for war, for the law or for secrets. */
@Getter
public abstract class ArmedOccupation extends Occupation {

    private final Force force;
    private final Set<Weapon> weapons;

    protected ArmedOccupation(String name, OccupationGroup group, Set<Science> sciences, int sinceYear,
                              Force force, Set<Weapon> weapons) {
        super(name, group, sciences, sinceYear);
        this.force = force;
        this.weapons = checkedWeapons(weapons);
    }

    protected ArmedOccupation(String name, OccupationGroup group, Set<Science> sciences, int sinceYear,
                              int untilYear, Force force, Set<Weapon> weapons) {
        super(name, group, sciences, sinceYear, untilYear);
        this.force = force;
        this.weapons = checkedWeapons(weapons);
    }

    private static Set<Weapon> checkedWeapons(Set<Weapon> weapons) {
        if (weapons.isEmpty()) {
            throw new IllegalArgumentException("an armed occupation carries at least one weapon");
        }
        return Collections.unmodifiableSet(EnumSet.copyOf(weapons));
    }

    @Override
    public boolean isArmed() {
        return true;
    }

    public boolean carriesFirearms() {
        return weapons.stream().anyMatch(Weapon::isFirearm);
    }
}
