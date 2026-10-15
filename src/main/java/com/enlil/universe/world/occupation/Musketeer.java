package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.ArmedOccupation;
import com.enlil.universe.civilization.occupation.Force;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.occupation.Weapon;

import java.util.Set;

/**
 * A musketeer of the military household of the King of France, from Louis XIII's company of 1622 until Louis XVI
 * disbanded them in 1776, as fond of the sword as of the musket they were named after.
 */
public class Musketeer extends ArmedOccupation {

    public Musketeer() {
        super("Musketeer", OccupationGroup.ARMED_FORCES, Set.of(), 1622, 1776, Force.MILITARY,
                Set.of(Weapon.MUSKET, Weapon.SWORD));
    }
}
