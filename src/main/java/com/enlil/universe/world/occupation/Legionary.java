package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.ArmedOccupation;
import com.enlil.universe.civilization.occupation.Force;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.occupation.Weapon;

import java.util.Set;

/**
 * A Roman legionary, a professional soldier since the reforms of Marius in 107 BCE until the fall of the West in
 * 476, armed with the gladius and the pilum.
 */
public class Legionary extends ArmedOccupation {

    public Legionary() {
        super("Legionary", OccupationGroup.ARMED_FORCES, Set.of(), -107, 476, Force.MILITARY,
                Set.of(Weapon.SWORD, Weapon.SPEAR));
    }
}
