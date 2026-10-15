package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.CivilOccupation;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.science.Science;

import java.util.Set;

/**
 * A firefighter (ISCO-08 5411), since Augustus founded the vigiles of Rome in 6 CE, seven thousand freedmen who
 * fought fires and patrolled the streets at night.
 */
public class Firefighter extends CivilOccupation {

    public Firefighter() {
        super("Firefighter", OccupationGroup.SERVICE_AND_SALES_WORKERS, Set.of(Science.CHEMISTRY), 6);
    }
}
