package com.enlil.universe.celestial;

import com.enlil.universe.world.CrabPulsar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

public class NeutronStarTest {

    private final CrabPulsar crab = new CrabPulsar();

    @Test
    @DisplayName("the Crab pulsar should be as dense as an atomic nucleus")
    void crab_density() {
        Assertions.assertEquals(6.7e17, crab.densityKgPerCubicMeter(), 0.05 * 6.7e17);
    }

    @Test
    @DisplayName("escaping the Crab pulsar should take 64 % of the speed of light")
    void escape_velocity() {
        Assertions.assertEquals(0.64, crab.surfaceEscapeVelocityFractionOfLight(), 0.02);
    }

    @Test
    @DisplayName("the Crab pulsar, turning 30 times a second, should not be a millisecond pulsar")
    void not_millisecond_pulsar() {
        Assertions.assertFalse(crab.isMillisecondPulsar());
        Assertions.assertTrue(new NeutronStar("fast", 1.4 * SOLAR_MASS, 1e4, 0.001_6).isMillisecondPulsar());
    }

    @Test
    @DisplayName("should reject a neutron star of 3 solar masses, and one that doesn't spin")
    void reject_invalid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new NeutronStar("too heavy", 3 * SOLAR_MASS, 1e4, 1));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new NeutronStar("still", 1.4 * SOLAR_MASS, 1e4, 0));
    }
}
