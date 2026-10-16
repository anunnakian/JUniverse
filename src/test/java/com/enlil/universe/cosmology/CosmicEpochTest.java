package com.enlil.universe.cosmology;

import com.enlil.universe.world.cosmology.Planck2018;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.enlil.universe.celestial.PhysicalConstants.JULIAN_YEAR;

public class CosmicEpochTest {

    @Test
    @DisplayName("each epoch should start where the previous one ended")
    void contiguous() {
        CosmicEpoch[] epochs = CosmicEpoch.values();

        Assertions.assertEquals(0, epochs[0].getStartSeconds());
        for (int i = 1; i < epochs.length; i++) {
            Assertions.assertEquals(epochs[i - 1].getEndSeconds().getAsDouble(), epochs[i].getStartSeconds());
        }
        Assertions.assertTrue(epochs[epochs.length - 1].getEndSeconds().isEmpty());
    }

    @Test
    @DisplayName("1 µs after the Big Bang should start the hadron epoch; 3 minutes should be nucleosynthesis")
    void early_epochs() {
        Assertions.assertEquals(CosmicEpoch.HADRON, CosmicEpoch.at(1e-6));
        Assertions.assertEquals(CosmicEpoch.QUARK, CosmicEpoch.at(0.99e-6));
        Assertions.assertEquals(CosmicEpoch.NUCLEOSYNTHESIS, CosmicEpoch.at(180));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CosmicEpoch.at(-1));
    }

    @Test
    @DisplayName("today should be dominated by dark energy")
    void today() {
        Assertions.assertEquals(CosmicEpoch.DARK_ENERGY_DOMINATED, CosmicEpoch.atYears(new Planck2018().ageYears()));
    }

    @Test
    @DisplayName("dark energy should have taken over 10.3 billion years after the Big Bang, as Planck 2018 says")
    void dark_energy_boundary_matches_model() {
        CosmologicalModel planck = new Planck2018();
        double equalityAgeYears = planck.ageAtYears(planck.matterDarkEnergyEqualityRedshift());

        Assertions.assertEquals(CosmicEpoch.DARK_ENERGY_DOMINATED.getStartSeconds() / JULIAN_YEAR, equalityAgeYears,
                0.1e9);
    }
}
