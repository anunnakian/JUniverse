package com.enlil.universe.matter.chemistry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.enlil.universe.world.chemistry.Compounds.*;

public class SolutionTest {

    @Test
    @DisplayName("0.01 M hydrochloric acid should have a pH of 2")
    void strong_acid() {
        Solution solution = new Solution(HYDROCHLORIC_ACID, WATER, 0.01);

        Assertions.assertEquals(2.00, solution.ph(), 0.01);
        Assertions.assertEquals(Acidity.ACIDIC, solution.acidity());
    }

    @Test
    @DisplayName("vinegar's 0.83 M acetic acid should have a pH of 2.42")
    void weak_acid() {
        Assertions.assertEquals(2.42, new Solution(ACETIC_ACID, WATER, 0.83).ph(), 0.01);
    }

    @Test
    @DisplayName("0.1 M ammonia should have a pH of 11.13, 0.01 M caustic soda 12")
    void bases() {
        Solution ammonia = new Solution(AMMONIA, WATER, 0.1);

        Assertions.assertEquals(11.13, ammonia.ph(), 0.01);
        Assertions.assertEquals(Acidity.BASIC, ammonia.acidity());
        Assertions.assertEquals(12.00, new Solution(SODIUM_HYDROXIDE, WATER, 0.01).ph(), 0.01);
    }

    @Test
    @DisplayName("a very dilute acid should stay neutral, counting water's own ions")
    void dilute_acid_stays_neutral() {
        Solution solution = new Solution(HYDROCHLORIC_ACID, WATER, 1e-9);

        Assertions.assertEquals(7.00, solution.ph(), 0.01);
        Assertions.assertEquals(Acidity.NEUTRAL, solution.acidity());
    }

    @Test
    @DisplayName("salt water should be neutral")
    void salt_is_neutral() {
        Assertions.assertEquals(7, new Solution(SODIUM_CHLORIDE, WATER, 0.6).ph());
    }

    @Test
    @DisplayName("should reject a solvent other than water, and a zero concentration")
    void reject_bad_solution() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Solution(ACETIC_ACID, ETHANOL, 0.1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Solution(ACETIC_ACID, WATER, 0));
    }
}
