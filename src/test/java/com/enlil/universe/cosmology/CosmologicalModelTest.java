package com.enlil.universe.cosmology;

import com.enlil.universe.world.cosmology.Planck2018;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CosmologicalModelTest {

    private static final double RECOMBINATION_REDSHIFT = 1_089.8;

    private final CosmologicalModel planck = new Planck2018();

    @Test
    @DisplayName("the universe should be 13.79 billion years old, younger than its Hubble time of 14.5")
    void age() {
        Assertions.assertEquals(13.79e9, planck.ageYears(), 0.03e9);
        Assertions.assertEquals(14.5e9, planck.hubbleTimeYears(), 0.1e9);
    }

    @Test
    @DisplayName("the CMB should have been released 377 000 years after the Big Bang, at 2 973 K")
    void recombination() {
        Assertions.assertEquals(377_000, planck.ageAtYears(RECOMBINATION_REDSHIFT), 37_700);
        Assertions.assertEquals(2_973, planck.cmbTemperatureAtKelvin(RECOMBINATION_REDSHIFT), 1);
    }

    @Test
    @DisplayName("light from z = 1 should have travelled 7.9 billion years from 3 400 Mpc away")
    void redshift_one() {
        Assertions.assertEquals(7.9e9, planck.lookbackTimeYears(1), 0.1e9);
        Assertions.assertEquals(3_400, planck.comovingDistanceMegaparsecs(1), 50);
        Assertions.assertEquals(0.5, planck.scaleFactorAt(1));
    }

    @Test
    @DisplayName("the luminosity distance should be (1+z)² times the angular diameter distance")
    void distance_duality() {
        double z = 2;

        Assertions.assertEquals((1 + z) * (1 + z) * planck.angularDiameterDistanceMegaparsecs(z),
                planck.luminosityDistanceMegaparsecs(z), 1e-6);
    }

    @Test
    @DisplayName("a galaxy 100 Mpc away should recede at 6 740 km/s")
    void hubble_law() {
        Assertions.assertEquals(6_740, planck.recessionVelocityKmPerSecond(100), 1e-9);
        Assertions.assertEquals(67.4, planck.hubbleParameterAt(0), 1e-9);
        Assertions.assertTrue(planck.hubbleParameterAt(1) > planck.hubbleParameterAt(0));
    }

    @Test
    @DisplayName("redshiftAtAge should invert ageAtYears")
    void redshift_at_age() {
        for (double z : new double[]{0.1, 1, 6, 1_000}) {
            Assertions.assertEquals(z, planck.redshiftAtAge(planck.ageAtYears(z)), 1e-6 * (1 + z));
        }
        Assertions.assertThrows(IllegalArgumentException.class, () -> planck.redshiftAtAge(20e9));
    }

    @Test
    @DisplayName("dark energy should have overtaken matter at z = 0.30")
    void matter_dark_energy_equality() {
        Assertions.assertEquals(0.30, planck.matterDarkEnergyEqualityRedshift(), 0.02);
    }

    @Test
    @DisplayName("should reject a universe that isn't flat, and a negative redshift")
    void reject_invalid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CosmologicalModel(70, 0.3, 0.3, 9e-5, 2.7));
        Assertions.assertThrows(IllegalArgumentException.class, () -> planck.ageAtYears(-0.1));
    }
}
