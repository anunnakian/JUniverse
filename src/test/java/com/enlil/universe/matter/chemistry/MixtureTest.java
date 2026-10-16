package com.enlil.universe.matter.chemistry;

import com.enlil.universe.world.chemistry.Compounds;
import com.enlil.universe.world.chemistry.Mixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MixtureTest {

    @Test
    @DisplayName("dry air should weigh 28.96 g/mol")
    void dry_air_molar_mass() {
        Assertions.assertEquals(28.96, Mixtures.DRY_AIR.molarMassGramsPerMole(), 0.01);
        Assertions.assertEquals(0.755, Mixtures.DRY_AIR.massFraction(Compounds.NITROGEN_GAS), 0.001);
    }

    @Test
    @DisplayName("seawater should hold about one salt formula unit for 90 water molecules")
    void seawater() {
        Assertions.assertEquals(0.011, Mixtures.SEAWATER.moleFraction(Compounds.SODIUM_CHLORIDE), 0.000_5);
        Assertions.assertEquals(0.035, Mixtures.SEAWATER.massFraction(Compounds.SODIUM_CHLORIDE), 1e-9);
    }

    @Test
    @DisplayName("should reject fractions that don't sum to 1")
    void reject_bad_fractions() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Mixture(Map.of(Compounds.NITROGEN_GAS, 0.5, Compounds.OXYGEN_GAS, 0.4)));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Mixture(Map.of(Compounds.NITROGEN_GAS, 1.2, Compounds.OXYGEN_GAS, -0.2)));
    }
}
