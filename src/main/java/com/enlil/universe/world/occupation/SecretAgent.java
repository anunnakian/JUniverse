package com.enlil.universe.world.occupation;

import com.enlil.universe.civilization.occupation.ArmedOccupation;
import com.enlil.universe.civilization.occupation.Force;
import com.enlil.universe.civilization.occupation.OccupationGroup;
import com.enlil.universe.civilization.occupation.Weapon;
import com.enlil.universe.civilization.science.Science;

import java.util.Set;

/**
 * A secret agent, an intelligence officer working undercover. Sun Tzu already devoted the last chapter of The Art of
 * War to spies, around 500 BCE; modern services followed, like Britain's Secret Service Bureau in 1909. ISCO-08
 * has no code for spies, so they are filed with police inspectors and detectives (3355).
 */
public class SecretAgent extends ArmedOccupation {

    public SecretAgent() {
        super("Secret agent", OccupationGroup.TECHNICIANS_AND_ASSOCIATE_PROFESSIONALS,
                Set.of(Science.PSYCHOLOGY, Science.LINGUISTICS), -500, Force.INTELLIGENCE, Set.of(Weapon.HANDGUN));
    }
}
