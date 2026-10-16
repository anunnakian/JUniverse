package com.enlil.universe.celestial;

import com.enlil.universe.world.CrabPulsar;
import com.enlil.universe.world.CygnusX1;
import com.enlil.universe.world.MilkyWay;
import com.enlil.universe.world.SiriusB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GalaxyTest {

    @Test
    @DisplayName("should gather the planets of all its stars")
    void gather_planets() {
        Planet a = new Planet("a", 1e24, 1e6, 1);
        Planet b = new Planet("b", 1e24, 1e6, 2);
        Planet c = new Planet("c", 1e24, 1e6, 3);
        Star first = new Star("first", 1e30, 1e8, 6_000, List.of(a, b));
        Star second = new Star("second", 1e30, 1e8, 4_000, List.of(c));

        Galaxy galaxy = new Galaxy("galaxy", GalaxyType.SPIRAL, List.of(first, second));

        Assertions.assertEquals(List.of(a, b, c), galaxy.getPlanets());
    }

    @Test
    @DisplayName("the Milky Way's black holes should include Sagittarius A* and Cygnus X-1")
    void milky_way_black_holes() {
        MilkyWay milkyWay = new MilkyWay(List.of(), List.of(new SiriusB(), new CrabPulsar(), new CygnusX1()));

        Assertions.assertEquals(List.of("Sagittarius A*", "Cygnus X-1"),
                milkyWay.getBlackHoles().stream().map(BlackHole::getName).toList());
        Assertions.assertEquals(3, milkyWay.getRemnants().size());
        Assertions.assertEquals("Sagittarius A*", milkyWay.getCentralBlackHole().orElseThrow().getName());
    }

    @Test
    @DisplayName("a galaxy built without remnants should have no black hole")
    void plain_galaxy_has_no_black_hole() {
        Galaxy galaxy = new Galaxy("galaxy", GalaxyType.ELLIPTICAL, List.of());

        Assertions.assertTrue(galaxy.getBlackHoles().isEmpty());
        Assertions.assertTrue(galaxy.getCentralBlackHole().isEmpty());
    }

    @Test
    @DisplayName("should reject a body without mass")
    void reject_massless_body() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Planet("ghost", 0, 1e6, 1));
    }
}
