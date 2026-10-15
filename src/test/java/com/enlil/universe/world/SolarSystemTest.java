package com.enlil.universe.world;

import com.enlil.universe.celestial.GalaxyType;
import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.PhysicalConstants;
import com.enlil.universe.celestial.SpectralClass;
import com.enlil.universe.life.Creature;
import com.enlil.universe.life.Kingdom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SolarSystemTest {

    @Test
    @DisplayName("the Sun should be a G-type star")
    void sun_is_g_type() {
        Assertions.assertEquals(SpectralClass.G, new Sun(List.of()).getSpectralClass());
    }

    @Test
    @DisplayName("the Earth should orbit the Sun in about a year")
    void earth_orbits_in_a_year() {
        Earth earth = new Earth();
        Sun sun = new Sun(List.of(earth));

        Assertions.assertEquals(365.25, sun.orbitalPeriodDays(earth), 0.5);
    }

    @Test
    @DisplayName("the Earth should have a surface gravity of about 9.8 m/s²")
    void earth_gravity() {
        Assertions.assertEquals(9.81, new Earth().surfaceGravity(), 0.05);
    }

    @Test
    @DisplayName("the Earth should have one moon")
    void earth_has_the_moon() {
        Assertions.assertEquals(List.of("Moon"), new Earth().getMoons().stream().map(moon -> moon.getName()).toList());
    }

    @Test
    @DisplayName("the Earth should host the creatures it is given")
    void earth_hosts_creatures() {
        Creature human = new Creature("Homo sapiens", Kingdom.ANIMALIA, 70);
        Earth earth = new Earth(List.of(human));

        Assertions.assertEquals(List.of(human), earth.getCreatures());
        Assertions.assertEquals(686.5, earth.weightNewtons(human), 1);
    }

    @Test
    @DisplayName("the Earth should be lifeless by default")
    void earth_lifeless_by_default() {
        Assertions.assertTrue(new Earth().getCreatures().isEmpty());
    }

    @Test
    @DisplayName("the planets should orbit the Sun with their real periods")
    void planets_orbital_periods() {
        Sun sun = new Sun(List.of());

        Assertions.assertEquals(87.97, sun.orbitalPeriodDays(new Mercury()), 0.1);
        Assertions.assertEquals(686.98, sun.orbitalPeriodDays(new Mars()), 1);
        Assertions.assertEquals(4_332.6, sun.orbitalPeriodDays(new Jupiter()), 5);
        Assertions.assertEquals(60_190, sun.orbitalPeriodDays(new Neptune()), 300);
    }

    @Test
    @DisplayName("the planets should have their real surface gravity")
    void planets_gravity() {
        Assertions.assertEquals(3.73, new Mars().surfaceGravity(), 0.05);
        // At the mean radius; NASA's 24.79 m/s² is measured at the (wider) equator of this oblate planet
        Assertions.assertEquals(25.92, new Jupiter().surfaceGravity(), 0.1);
        Assertions.assertEquals(8.87, new Venus().surfaceGravity(), 0.05);
    }

    @Test
    @DisplayName("Jupiter should have its four Galilean moons")
    void jupiter_galilean_moons() {
        Assertions.assertEquals(List.of("Io", "Europa", "Ganymede", "Callisto"),
                new Jupiter().getMoons().stream().map(moon -> moon.getName()).toList());
    }

    @Test
    @DisplayName("the stars near the Sun should have their real spectral classes")
    void neighbour_stars_spectral_classes() {
        Assertions.assertEquals(SpectralClass.A, new Sirius().getSpectralClass());
        Assertions.assertEquals(SpectralClass.G, new AlphaCentauriA().getSpectralClass());
        Assertions.assertEquals(SpectralClass.M, new ProximaCentauri().getSpectralClass());
    }

    @Test
    @DisplayName("the Earth should be closest to the Sun in early January and farthest in early July")
    void earth_perihelion_and_aphelion() {
        Sun sun = new Sun(List.of());
        Earth earth = new Earth();

        Assertions.assertEquals(0.983_3, sun.positionOf(earth, Instant.parse("2026-01-03T00:00:00Z")).distanceAu(), 0.000_5);
        Assertions.assertEquals(1.016_7, sun.positionOf(earth, Instant.parse("2026-07-06T00:00:00Z")).distanceAu(), 0.000_5);
    }

    @Test
    @DisplayName("Mars should come closest to the Earth around its February 2027 opposition, at about 0.68 AU")
    void mars_closest_approach_2027() {
        Earth earth = new Earth();
        Mars mars = new Mars();
        Sun sun = new Sun(List.of(earth, mars));

        Instant closest = sun.closestApproach(earth, mars,
                Instant.parse("2026-10-01T00:00:00Z"), Instant.parse("2027-06-01T00:00:00Z"));

        Assertions.assertEquals(0, Duration.between(Instant.parse("2027-02-20T00:00:00Z"), closest).toDays(), 4);
        Assertions.assertEquals(0.678, sun.distanceAu(earth, mars, closest), 0.01);
    }

    @Test
    @DisplayName("Mercury should never stray more than 7° from the ecliptic, seen from the Sun")
    void mercury_inclination() {
        Sun sun = new Sun(List.of());
        Mercury mercury = new Mercury();

        double maxLatitude = 0;
        for (int day = 0; day < 88; day++) {
            Instant instant = PhysicalConstants.J2000.plus(Duration.ofDays(day));
            maxLatitude = Math.max(maxLatitude, Math.abs(sun.positionOf(mercury, instant).latitudeDegrees()));
        }
        Assertions.assertEquals(7.0, maxLatitude, 0.1);
    }

    @Test
    @DisplayName("the moons should orbit their planets with their real periods")
    void moons_orbital_periods() {
        Earth earth = new Earth();
        Jupiter jupiter = new Jupiter();
        Saturn saturn = new Saturn();

        Assertions.assertEquals(27.322, earth.orbitalPeriodDays(earth.getMoons().get(0)), 0.01);
        double[] galileanPeriodsDays = {1.769, 3.551, 7.155, 16.689};
        for (int i = 0; i < galileanPeriodsDays.length; i++) {
            Assertions.assertEquals(galileanPeriodsDays[i], jupiter.orbitalPeriodDays(jupiter.getMoons().get(i)), 0.01);
        }
        Assertions.assertEquals(15.945, saturn.orbitalPeriodDays(saturn.getMoons().get(2)), 0.01);
        Assertions.assertEquals(0.319, new Mars().orbitalPeriodDays(new Mars().getMoons().get(0)), 0.001);
    }

    @Test
    @DisplayName("the Moon should be full at the 3 March 2026 lunar eclipse and new at the 12 August 2026 solar eclipse")
    void moon_phases_at_eclipses() {
        Earth earth = new Earth();
        Sun sun = new Sun(List.of(earth));
        NaturalSatellite moon = earth.getMoons().get(0);

        Assertions.assertTrue(sun.illuminatedFraction(earth, moon, Instant.parse("2026-03-03T11:33:00Z")) > 0.98);
        Assertions.assertTrue(sun.illuminatedFraction(earth, moon, Instant.parse("2026-08-12T17:46:00Z")) < 0.02);
    }

    @Test
    @DisplayName("the Milky Way should be a barred spiral galaxy holding the Earth")
    void milky_way_holds_earth() {
        Earth earth = new Earth();
        MilkyWay milkyWay = new MilkyWay(List.of(new Sun(List.of(earth))));

        Assertions.assertEquals(GalaxyType.BARRED_SPIRAL, milkyWay.getType());
        Assertions.assertEquals(List.of(earth), milkyWay.getPlanets());
    }
}
