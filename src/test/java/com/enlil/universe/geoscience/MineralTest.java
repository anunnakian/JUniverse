package com.enlil.universe.geoscience;

import com.enlil.universe.matter.chemistry.Formula;
import com.enlil.universe.world.geoscience.Minerals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MineralTest {

    @Test
    @DisplayName("diamond should scratch quartz, and quartz not topaz")
    void scratches() {
        Assertions.assertTrue(Minerals.DIAMOND.scratches(Minerals.QUARTZ));
        Assertions.assertFalse(Minerals.QUARTZ.scratches(Minerals.TOPAZ));
        Assertions.assertFalse(Minerals.QUARTZ.scratches(Minerals.QUARTZ));
    }

    @Test
    @DisplayName("the Mohs scale should go strictly from 1 to 10")
    void mohs_scale() {
        List<Mineral> scale = Minerals.MOHS_SCALE;

        Assertions.assertEquals(10, scale.size());
        for (int i = 0; i < scale.size(); i++) {
            Assertions.assertEquals(i + 1, scale.get(i).getMohsHardness());
        }
    }

    @Test
    @DisplayName("quartz should weigh 60.08 g/mol")
    void quartz_molar_mass() {
        Assertions.assertEquals(60.08, Minerals.QUARTZ.molarMassGramsPerMole(), 0.01);
    }

    @Test
    @DisplayName("should reject a hardness of 11")
    void reject_hardness_beyond_diamond() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Mineral("Harder than diamond", Formula.parse("C"), 11, 3_500));
    }
}
