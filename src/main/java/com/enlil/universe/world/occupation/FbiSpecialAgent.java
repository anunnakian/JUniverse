package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.ArmedOccupation;
import com.enlil.universe.civilization.occupation.Force;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.occupation.Weapon;
import com.enlil.universe.civilization.science.Science;

import java.util.Set;

/**
 * A special agent of the Federal Bureau of Investigation of the United States (ISCO-08 3355, police inspectors and
 * detectives), since the bureau was founded in 1908. Its Behavioral Analysis Unit profiles criminals with
 * psychology, its laboratory reads evidence with chemistry and biology.
 */
public class FbiSpecialAgent extends ArmedOccupation {

    public FbiSpecialAgent() {
        super("FBI special agent", OccupationGroup.TECHNICIANS_AND_ASSOCIATE_PROFESSIONALS,
                Set.of(Science.PSYCHOLOGY, Science.CHEMISTRY, Science.BIOLOGY), 1908, Force.LAW_ENFORCEMENT,
                Set.of(Weapon.HANDGUN));
    }
}
