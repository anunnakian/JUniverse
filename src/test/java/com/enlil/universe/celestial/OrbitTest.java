package com.enlil.universe.celestial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrbitTest {

    @Test
    @DisplayName("a circular orbit should keep a constant distance and go round once per period")
    void circular_orbit() {
        Orbit orbit = Orbit.circular(2);

        OrbitalPosition start = orbit.positionAt(0, 100);
        OrbitalPosition quarter = orbit.positionAt(25, 100);

        Assertions.assertEquals(2, quarter.distanceAu(), 1e-9);
        Assertions.assertEquals(0, start.longitudeDegrees(), 1e-9);
        Assertions.assertEquals(90, quarter.longitudeDegrees(), 1e-9);
        Assertions.assertEquals(0, orbit.positionAt(100, 100).distanceAu(start), 1e-9);
    }

    @Test
    @DisplayName("an eccentric orbit should reach its perihelion and aphelion half a period apart")
    void eccentric_orbit() {
        // starts at perihelion, pointing at 30°
        Orbit orbit = new Orbit(1, 0.5, 30, 30);

        Assertions.assertEquals(0.5, orbit.perihelionAu(), 1e-9);
        Assertions.assertEquals(1.5, orbit.aphelionAu(), 1e-9);
        Assertions.assertEquals(0.5, orbit.positionAt(0, 100).distanceAu(), 1e-9);
        Assertions.assertEquals(30, orbit.positionAt(0, 100).longitudeDegrees(), 1e-9);
        Assertions.assertEquals(1.5, orbit.positionAt(50, 100).distanceAu(), 1e-9);
        Assertions.assertEquals(210, orbit.positionAt(50, 100).longitudeDegrees(), 1e-9);
    }

    @Test
    @DisplayName("a polar orbit should rise above the reference plane a quarter period after its ascending node")
    void inclined_orbit() {
        Orbit orbit = new Orbit(1, 0, 90, 0, 0, 0);

        OrbitalPosition atNode = orbit.positionAt(0, 100);
        OrbitalPosition aQuarterLater = orbit.positionAt(25, 100);

        Assertions.assertEquals(0, atNode.latitudeDegrees(), 1e-9);
        Assertions.assertEquals(90, aQuarterLater.latitudeDegrees(), 1e-9);
        Assertions.assertEquals(1, aQuarterLater.getZAu(), 1e-9);
    }

    @Test
    @DisplayName("an orbit should not be open")
    void rejects_open_orbit() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Orbit(1, 1, 0, 0));
    }
}
