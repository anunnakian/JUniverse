package com.enlil.universe.celestial;

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
    @DisplayName("should reject a body without mass")
    void reject_massless_body() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Planet("ghost", 0, 1e6, 1));
    }
}
