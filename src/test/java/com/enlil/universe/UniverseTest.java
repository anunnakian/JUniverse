package com.enlil.universe;

import com.enlil.universe.world.Earth;
import com.enlil.universe.world.Humanity;
import com.enlil.universe.world.MilkyWay;
import com.enlil.universe.world.Sun;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UniverseTest {

    @Test
    @DisplayName("should display the name of an universe")
    void display_universe() {
        Universe universe = Universe.builder().build();

        String text = universe.toString();

        Assertions.assertEquals("Universe(galaxies=[])", text);
    }

    @Test
    @DisplayName("should find its stars and planets through its galaxies")
    void derive_stars_and_planets() {
        Earth earth = new Earth();
        Sun sun = new Sun(List.of(earth));

        Universe universe = Universe.builder().galaxy(new MilkyWay(List.of(sun))).build();

        Assertions.assertEquals(List.of(sun), universe.getStars());
        Assertions.assertEquals(List.of(earth), universe.getPlanets());
    }

    @Test
    @DisplayName("should find its civilizations on the planets of its galaxies")
    void derive_civilizations() {
        Humanity humanity = new Humanity();
        Earth earth = new Earth(List.of(), List.of(humanity));

        Universe universe = Universe.builder().galaxy(new MilkyWay(List.of(new Sun(List.of(earth))))).build();

        Assertions.assertEquals(List.of(humanity), universe.getCivilizations());
        Assertions.assertTrue(new Earth().getCivilizations().isEmpty());
    }
}
