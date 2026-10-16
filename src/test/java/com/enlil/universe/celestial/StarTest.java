package com.enlil.universe.celestial;

import com.enlil.universe.world.Earth;
import com.enlil.universe.world.Sun;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StarTest {

    private final Earth earth = new Earth();
    private final Sun sun = new Sun(List.of(earth));

    @Test
    @DisplayName("without its greenhouse effect, the Earth would freeze at 254 K")
    void earth_equilibrium_temperature() {
        Assertions.assertEquals(254, sun.equilibriumTemperatureKelvin(earth, Earth.BOND_ALBEDO), 2);
    }

    @Test
    @DisplayName("should reject an albedo of 1")
    void reject_full_albedo() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> sun.equilibriumTemperatureKelvin(earth, 1));
    }
}
