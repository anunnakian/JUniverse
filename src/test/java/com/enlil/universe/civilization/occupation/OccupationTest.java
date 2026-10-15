package com.enlil.universe.civilization.occupation;

import com.enlil.universe.civilization.science.Science;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class OccupationTest {

    private static ArmedOccupation guard(Set<Weapon> weapons) {
        return new ArmedOccupation("Guard", OccupationGroup.SERVICE_AND_SALES_WORKERS, Set.of(), 1000,
                Force.LAW_ENFORCEMENT, weapons) {
        };
    }

    private static CivilOccupation scribe(int sinceYear, int untilYear) {
        return new CivilOccupation("Scribe", OccupationGroup.CLERICAL_SUPPORT_WORKERS, Set.of(Science.LINGUISTICS),
                sinceYear, untilYear) {
        };
    }

    @Test
    @DisplayName("an armed occupation should know whether it carries firearms")
    void firearms() {
        Assertions.assertTrue(guard(Set.of(Weapon.BATON, Weapon.HANDGUN)).carriesFirearms());
        Assertions.assertFalse(guard(Set.of(Weapon.SPEAR)).carriesFirearms());
        Assertions.assertTrue(guard(Set.of(Weapon.SPEAR)).isArmed());
        Assertions.assertFalse(scribe(-3000, 1500).isArmed());
    }

    @Test
    @DisplayName("an occupation should exist only between its appearance and its disappearance")
    void existed_in() {
        CivilOccupation scribe = scribe(-3000, 1500);

        Assertions.assertTrue(scribe.existedIn(-1000));
        Assertions.assertFalse(scribe.existedIn(2000));
        Assertions.assertFalse(scribe.isExtant());
        Assertions.assertTrue(guard(Set.of(Weapon.SPEAR)).isExtant());
    }

    @Test
    @DisplayName("ISCO-08 should number its major groups from 0, the armed forces, to 9")
    void isco_codes() {
        Assertions.assertEquals(0, OccupationGroup.ARMED_FORCES.getIscoCode());
        Assertions.assertEquals(2, OccupationGroup.PROFESSIONALS.getIscoCode());
        Assertions.assertEquals(9, OccupationGroup.ELEMENTARY_OCCUPATIONS.getIscoCode());
    }

    @Test
    @DisplayName("an armed occupation should carry a weapon, and history should run forward")
    void rejects_invalid_occupations() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> guard(Set.of()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> scribe(1500, -3000));
        Assertions.assertThrows(IllegalArgumentException.class, () -> scribe(0, 1500));
    }
}
