package com.enlil.universe.geoscience;

import com.enlil.universe.world.Earth;
import com.enlil.universe.world.geoscience.TectonicPlates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TectonicPlateTest {

    private static final double EARTH_SURFACE_SQUARE_KM = 510_000_000;

    @Test
    @DisplayName("the seven major plates should cover 90 to 95 % of the Earth")
    void major_plates_cover_most_of_earth() {
        double share = TectonicPlates.MAJOR.stream().mapToDouble(TectonicPlate::getAreaSquareKm).sum()
                / EARTH_SURFACE_SQUARE_KM;

        Assertions.assertTrue(share > 0.90 && share < 0.95, "share " + share);
        Assertions.assertEquals(TectonicPlates.ALL, new Earth().getTectonicPlates());
    }

    @Test
    @DisplayName("the Atlantic should widen by 1 km in 40 000 years")
    void atlantic_widening() {
        Assertions.assertEquals(40_000, TectonicPlates.MID_ATLANTIC_RIDGE.yearsToWiden(1), 1e-6);
    }

    @Test
    @DisplayName("a transform fault should not widen")
    void transform_fault_does_not_widen() {
        Assertions.assertThrows(IllegalStateException.class, () -> TectonicPlates.SAN_ANDREAS_FAULT.yearsToWiden(1));
    }

    @Test
    @DisplayName("the Pacific plate should drift 70 km in a million years; a still plate never")
    void plate_drift() {
        Assertions.assertEquals(1e6, TectonicPlates.PACIFIC.yearsToMove(70), 1e-6);
        Assertions.assertThrows(IllegalStateException.class,
                () -> new TectonicPlate("still", 1, PlateType.OCEANIC, 0).yearsToMove(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PlateBoundary("self",
                TectonicPlates.PACIFIC, TectonicPlates.PACIFIC, BoundaryType.DIVERGENT, 1));
    }
}
