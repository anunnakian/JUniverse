package com.enlil.universe.celestial;

import com.enlil.universe.world.SiriusB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

public class WhiteDwarfTest {

    @Test
    @DisplayName("Sirius B should pack 2.4 tonnes into each cubic centimeter")
    void sirius_b_density() {
        Assertions.assertEquals(2.4e9, new SiriusB().densityKgPerCubicMeter(), 0.1 * 2.4e9);
        Assertions.assertTrue(new SiriusB().luminosityWatts() > 0);
    }

    @Test
    @DisplayName("should reject a white dwarf above the Chandrasekhar limit")
    void reject_beyond_chandrasekhar() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new WhiteDwarf("too heavy", 1.5 * SOLAR_MASS, 5e6, 10_000));
    }
}
