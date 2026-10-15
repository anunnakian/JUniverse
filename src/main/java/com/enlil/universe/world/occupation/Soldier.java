package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.ArmedOccupation;
import com.enlil.universe.civilization.occupation.Force;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.occupation.Weapon;

import java.util.Set;

/**
 * A soldier of a standing army (ISCO-08 0), carrying a rifle today. Sargon of Akkad kept the first known
 * standing army, of 5,400 men, around 2300 BCE.
 */
public class Soldier extends ArmedOccupation {

    public Soldier() {
        super("Soldier", OccupationGroup.ARMED_FORCES, Set.of(), -2300, Force.MILITARY, Set.of(Weapon.RIFLE));
    }
}
