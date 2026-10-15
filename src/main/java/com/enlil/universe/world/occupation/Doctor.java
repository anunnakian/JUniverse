package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.CivilOccupation;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.science.Science;

import java.util.Set;

/**
 * A medical doctor (ISCO-08 221). Hesy-Ra, "chief of dentists and physicians" in Egypt around 2600 BCE, is the
 * first one known by name; Hippocrates gave the profession its oath two thousand years later.
 */
public class Doctor extends CivilOccupation {

    public Doctor() {
        super("Doctor", OccupationGroup.PROFESSIONALS, Set.of(Science.MEDICINE, Science.BIOLOGY, Science.CHEMISTRY),
                -2600);
    }
}
