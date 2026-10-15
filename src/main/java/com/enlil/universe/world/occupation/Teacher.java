package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.CivilOccupation;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.science.Science;

import java.util.Set;

/**
 * A teacher (ISCO-08 23), since the Sumerian scribal schools, the "tablet houses", around 2500 BCE. Teachers rely
 * on psychology to teach, and on every other science for what they teach.
 */
public class Teacher extends CivilOccupation {

    public Teacher() {
        super("Teacher", OccupationGroup.PROFESSIONALS, Set.of(Science.PSYCHOLOGY), -2500);
    }
}
