package com.enlil.universe.geoscience;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AtmosphericLayerTest {

    @Test
    @DisplayName("airliners should fly in the troposphere, weather balloons in the stratosphere")
    void layers_by_altitude() {
        Assertions.assertEquals(AtmosphericLayer.TROPOSPHERE, AtmosphericLayer.of(10_000));
        Assertions.assertEquals(AtmosphericLayer.STRATOSPHERE, AtmosphericLayer.of(30_000));
        Assertions.assertEquals(AtmosphericLayer.EXOSPHERE, AtmosphericLayer.of(10_000_000));
    }

    @Test
    @DisplayName("the Kármán line should lie in the thermosphere")
    void karman_line_in_thermosphere() {
        Assertions.assertEquals(AtmosphericLayer.THERMOSPHERE, AtmosphericLayer.of(AtmosphericLayer.KARMAN_LINE_METERS));
    }

    @Test
    @DisplayName("should reject an altitude below the ground or beyond the exosphere")
    void reject_out_of_range() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> AtmosphericLayer.of(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> AtmosphericLayer.of(20_000_000));
    }
}
