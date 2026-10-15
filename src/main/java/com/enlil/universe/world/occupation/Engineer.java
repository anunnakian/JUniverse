package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.CivilOccupation;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.science.Science;

import java.util.Set;

/**
 * An engineer (ISCO-08 214). Imhotep, who built the step pyramid of Djoser around 2650 BCE, is the first one
 * known by name.
 */
public class Engineer extends CivilOccupation {

    public Engineer() {
        super("Engineer", OccupationGroup.PROFESSIONALS,
                Set.of(Science.ENGINEERING, Science.PHYSICS, Science.MATHEMATICS), -2650);
    }
}
