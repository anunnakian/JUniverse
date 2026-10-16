package com.enlil.universe.celestial;

import com.enlil.universe.world.Earth;
import com.enlil.universe.world.Mars;
import com.enlil.universe.world.Sirius;
import com.enlil.universe.world.Sun;
import com.enlil.universe.world.Venus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

public class StarTest {

    private final Earth earth = new Earth();
    private final Venus venus = new Venus();
    private final Mars mars = new Mars();
    private final Sun sun = new Sun(List.of(venus, earth, mars));

    private static Star ofSolarMasses(double solarMasses) {
        return new Star("star", solarMasses * SOLAR_MASS, 1e9, 20_000, List.of());
    }

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

    @Test
    @DisplayName("the Sun should shine 3.828e26 W, magnitude 4.74; Sirius 25 times brighter")
    void luminosity() {
        Assertions.assertEquals(3.828e26, sun.luminosityWatts(), 0.005 * 3.828e26);
        Assertions.assertEquals(4.74, sun.absoluteBolometricMagnitude(), 0.01);
        Assertions.assertEquals(25, new Sirius().luminositySolar(), 0.05 * 25);
    }

    @Test
    @DisplayName("the Sun should live 10 billion years on the main sequence, Sirius 1.6 billion")
    void lifetimes() {
        Assertions.assertEquals(1e10, sun.mainSequenceLifetimeYears(), 1e7);
        Assertions.assertEquals(1.6e9, new Sirius().mainSequenceLifetimeYears(), 0.1e9);
    }

    @Test
    @DisplayName("the Earth should be in the Sun's habitable zone, Venus and Mars not")
    void habitable_zone() {
        HabitableZone zone = sun.getHabitableZone();

        Assertions.assertTrue(zone.contains(earth));
        Assertions.assertFalse(zone.contains(venus));
        Assertions.assertFalse(zone.contains(mars));
    }

    @Test
    @DisplayName("the Sun should end as a white dwarf, 10 suns as a neutron star, 25 as a black hole")
    void fates() {
        Assertions.assertEquals(StellarFate.WHITE_DWARF, sun.getFate());
        Assertions.assertEquals(StellarFate.NEUTRON_STAR, ofSolarMasses(10).getFate());
        Assertions.assertEquals(StellarFate.BLACK_HOLE, ofSolarMasses(25).getFate());
    }

    @Test
    @DisplayName("the Sun should be on the main sequence today, a red giant at 10.5 Gyr, a white dwarf at 12 Gyr")
    void sun_stages() {
        Assertions.assertEquals(StellarStage.MAIN_SEQUENCE, sun.stageAt(4.6e9));
        Assertions.assertEquals(StellarStage.RED_GIANT, sun.stageAt(10.5e9));
        Assertions.assertEquals(StellarStage.WHITE_DWARF, sun.stageAt(12e9));
        Assertions.assertTrue(sun.stageAt(12e9).isRemnant());
        Assertions.assertEquals(StellarStage.RED_SUPERGIANT,
                ofSolarMasses(25).stageAt(1.05 * ofSolarMasses(25).mainSequenceLifetimeYears()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> sun.stageAt(-1));
    }
}
