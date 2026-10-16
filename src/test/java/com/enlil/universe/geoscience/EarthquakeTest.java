package com.enlil.universe.geoscience;

import com.enlil.universe.world.geoscience.Earthquakes;
import com.enlil.universe.world.geoscience.TectonicPlates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EarthquakeTest {

    @Test
    @DisplayName("Valdivia should have released 251 times the energy of the San Francisco earthquake")
    void energy_ratio() {
        Assertions.assertEquals(Math.pow(10, 1.5 * 1.6),
                Earthquakes.VALDIVIA_1960.energyRatioTo(Earthquakes.SAN_FRANCISCO_1906), 1e-6);
        Assertions.assertEquals(251, Earthquakes.VALDIVIA_1960.energyRatioTo(Earthquakes.SAN_FRANCISCO_1906), 1);
    }

    @Test
    @DisplayName("a magnitude 9.1 earthquake should have a seismic moment of 5.6e22 N·m")
    void seismic_moment() {
        Assertions.assertEquals(5.6e22, Earthquakes.TOHOKU_2011.seismicMomentNewtonMeters(), 0.1e22);
    }

    @Test
    @DisplayName("the San Francisco earthquake should have struck the San Andreas Fault")
    void boundary() {
        Assertions.assertEquals(TectonicPlates.SAN_ANDREAS_FAULT, Earthquakes.SAN_FRANCISCO_1906.getBoundary().orElseThrow());
        Assertions.assertTrue(Earthquakes.LISBON_1755.getBoundary().isEmpty());
    }

    @Test
    @DisplayName("should reject a magnitude of 0")
    void reject_zero_magnitude() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Earthquake("none", 2000, 0));
    }
}
