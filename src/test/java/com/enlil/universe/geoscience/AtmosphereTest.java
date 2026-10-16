package com.enlil.universe.geoscience;

import com.enlil.universe.world.Earth;
import com.enlil.universe.world.Mars;
import com.enlil.universe.world.Mercury;
import com.enlil.universe.world.Sun;
import com.enlil.universe.world.Venus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AtmosphereTest {

    private final Earth earth = new Earth();
    private final Venus venus = new Venus();
    private final Mars mars = new Mars();
    private final Sun sun = new Sun(List.of(venus, earth, mars));
    private final Atmosphere air = earth.getAtmosphere().orElseThrow();

    @Test
    @DisplayName("the Earth's air should thin out by a factor e every 8.4 km")
    void earth_scale_height() {
        Assertions.assertEquals(8_400, air.scaleHeightMeters(earth.surfaceGravity()), 200);
    }

    @Test
    @DisplayName("the air at the summit of Everest should be about a third of sea level's")
    void everest_pressure() {
        Assertions.assertEquals(33_700, air.pressureAtPascals(8_849, earth.surfaceGravity()), 3_000);
        Assertions.assertEquals(101_325, air.pressureAtPascals(0, earth.surfaceGravity()));
    }

    @Test
    @DisplayName("the greenhouse effect should warm the Earth by 34 K, and Venus by more than 400 K")
    void greenhouse_warming() {
        double earthWarming = air.greenhouseWarmingKelvin(sun.equilibriumTemperatureKelvin(earth, Earth.BOND_ALBEDO));
        double venusWarming = venus.getAtmosphere().orElseThrow()
                .greenhouseWarmingKelvin(sun.equilibriumTemperatureKelvin(venus, Venus.BOND_ALBEDO));

        Assertions.assertEquals(34, earthWarming, 2);
        Assertions.assertTrue(venusWarming > 400, "Venus warming " + venusWarming);
    }

    @Test
    @DisplayName("the thin, cold air of Mars should have a scale height of 11 km")
    void mars_scale_height() {
        Assertions.assertEquals(11_000, mars.getAtmosphere().orElseThrow().scaleHeightMeters(mars.surfaceGravity()),
                1_000);
    }

    @Test
    @DisplayName("planets without a modeled atmosphere or interior should have none")
    void none_by_default() {
        Assertions.assertTrue(new Mercury().getAtmosphere().isEmpty());
        Assertions.assertTrue(mars.getInterior().isEmpty());
    }
}
