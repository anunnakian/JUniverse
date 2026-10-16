package com.enlil.universe.celestial;

import com.enlil.universe.world.CygnusX1;
import com.enlil.universe.world.SagittariusAStar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

public class BlackHoleTest {

    private final BlackHole solar = new BlackHole("solar", SOLAR_MASS);

    @Test
    @DisplayName("a black hole of one solar mass should be 2 953 m across in radius")
    void solar_mass_schwarzschild_radius() {
        Assertions.assertEquals(2_953, solar.schwarzschildRadiusMeters(), 1);
        Assertions.assertEquals(solar.getRadiusMeters(), solar.schwarzschildRadiusMeters());
        Assertions.assertEquals(1.5 * 2_953, solar.photonSphereRadiusMeters(), 2);
        Assertions.assertEquals(3 * 2_953, solar.innermostStableOrbitRadiusMeters(), 3);
    }

    @Test
    @DisplayName("a solar-mass black hole should glow at 62 nanokelvins and evaporate in 2.1e67 years")
    void hawking_radiation() {
        Assertions.assertEquals(6.17e-8, solar.hawkingTemperatureKelvin(), 0.01 * 6.17e-8);
        Assertions.assertEquals(2.1e67, solar.evaporationTimeYears(), 0.05 * 2.1e67);
    }

    @Test
    @DisplayName("Sagittarius A* should be supermassive, 1.27e10 m in radius; Cygnus X-1 should not")
    void supermassive() {
        SagittariusAStar sagittarius = new SagittariusAStar();

        Assertions.assertEquals(1.27e10, sagittarius.schwarzschildRadiusMeters(), 0.01 * 1.27e10);
        Assertions.assertTrue(sagittarius.isSupermassive());
        Assertions.assertFalse(new CygnusX1().isSupermassive());
    }

    @Test
    @DisplayName("a clock at 3 r_s should tick at √(2/3) of a distant one's rate")
    void time_dilation() {
        Assertions.assertEquals(Math.sqrt(2.0 / 3), solar.timeDilationFactor(3 * solar.schwarzschildRadiusMeters()),
                1e-12);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> solar.timeDilationFactor(0.5 * solar.schwarzschildRadiusMeters()));
    }

    @Test
    @DisplayName("tides near a stellar black hole should tear a person apart, not near Sagittarius A*'s horizon")
    void tides() {
        double nearStellar = new CygnusX1().tidalAccelerationDifference(1e5, 2);
        SagittariusAStar sagittarius = new SagittariusAStar();
        double atSupermassiveHorizon =
                sagittarius.tidalAccelerationDifference(sagittarius.schwarzschildRadiusMeters(), 2);

        Assertions.assertTrue(nearStellar > 1_000, "stellar " + nearStellar);
        Assertions.assertTrue(atSupermassiveHorizon < 0.01, "supermassive " + atSupermassiveHorizon);
    }

    @Test
    @DisplayName("a black hole should have no surface gravity")
    void no_surface_gravity() {
        Assertions.assertThrows(UnsupportedOperationException.class, solar::surfaceGravity);
    }
}
