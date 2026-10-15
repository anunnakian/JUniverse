package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.ArmedOccupation;
import com.enlil.universe.civilization.occupation.Force;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.occupation.Weapon;

import java.util.Set;

/**
 * A police officer (ISCO-08 5412), since Robert Peel founded London's Metropolitan Police, the first modern
 * police force, in 1829. Most carry a handgun; British officers still patrol with a baton only.
 */
public class PoliceOfficer extends ArmedOccupation {

    public PoliceOfficer() {
        super("Police officer", OccupationGroup.SERVICE_AND_SALES_WORKERS, Set.of(), 1829, Force.LAW_ENFORCEMENT,
                Set.of(Weapon.BATON, Weapon.HANDGUN));
    }
}
