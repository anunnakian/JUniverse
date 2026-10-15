package com.enlil.universe.civilization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KardashevTypeTest {

    @Test
    @DisplayName("harnessing the Sun's whole luminosity should make a type II civilization")
    void sun_luminosity_is_type_two() {
        Assertions.assertEquals(KardashevType.TYPE_II, KardashevType.of(3.828e26));
    }

    @Test
    @DisplayName("each threshold should belong to the type it opens")
    void thresholds() {
        Assertions.assertEquals(KardashevType.TYPE_0, KardashevType.of(9.9e15));
        Assertions.assertEquals(KardashevType.TYPE_I, KardashevType.of(1e16));
        Assertions.assertEquals(KardashevType.TYPE_II, KardashevType.of(1e26));
        Assertions.assertEquals(KardashevType.TYPE_III, KardashevType.of(1e36));
    }

    @Test
    @DisplayName("a civilization should not harness a non-positive power")
    void rejects_non_positive_power() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> KardashevType.of(0));
    }
}
