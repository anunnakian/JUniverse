package com.enlil.universe.celestial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpectralClassTest {

    @Test
    @DisplayName("should classify a star from its surface temperature")
    void classify_from_temperature() {
        Assertions.assertEquals(SpectralClass.O, SpectralClass.fromTemperature(40_000));
        Assertions.assertEquals(SpectralClass.B, SpectralClass.fromTemperature(10_000));
        Assertions.assertEquals(SpectralClass.G, SpectralClass.fromTemperature(5_772));
        Assertions.assertEquals(SpectralClass.M, SpectralClass.fromTemperature(3_042));
    }

    @Test
    @DisplayName("should reject a non positive temperature")
    void reject_negative_temperature() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SpectralClass.fromTemperature(-1));
    }
}
