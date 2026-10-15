package com.enlil.universe.life.human;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrgansTest {

    @Test
    @DisplayName("the reference heart should weigh 330 g in men and 250 g in women (ICRP 89)")
    void heart_mass() {
        Assertions.assertEquals(0.330, Organs.reference(Sex.MALE).massKg(Organ.HEART), 1e-9);
        Assertions.assertEquals(0.250, Organs.reference(Sex.FEMALE).massKg(Organ.HEART), 1e-9);
    }

    @Test
    @DisplayName("every modeled organ should have a mass, totalling 8.85 kg in the reference man")
    void total_mass() {
        Organs organs = Organs.reference(Sex.MALE);

        Assertions.assertEquals(Organ.values().length, organs.getMassesKg().size());
        Assertions.assertEquals(8.85, organs.totalMassKg(), 1e-9);
    }

    @Test
    @DisplayName("the organs should not be modifiable")
    void immutable() {
        Organs organs = Organs.reference(Sex.FEMALE);

        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> organs.getMassesKg().put(Organ.BRAIN, 2.0));
    }
}
