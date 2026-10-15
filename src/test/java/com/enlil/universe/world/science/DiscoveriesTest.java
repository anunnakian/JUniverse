package com.enlil.universe.world.science;

import com.enlil.universe.civilization.science.Science;
import com.enlil.universe.world.AncientRome;
import com.enlil.universe.world.Humanity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

public class DiscoveriesTest {

    @Test
    @DisplayName("humanity should know every modeled science")
    void humanity_knows_every_science() {
        Assertions.assertEquals(EnumSet.allOf(Science.class), new Humanity().getSciences());
    }

    @Test
    @DisplayName("ancient Rome should know logic, geometry, engineering and astronomy, but no biology")
    void ancient_rome_sciences() {
        Assertions.assertEquals(EnumSet.of(Science.MATHEMATICS, Science.LOGIC, Science.ASTRONOMY, Science.ENGINEERING),
                new AncientRome().getSciences());
        Assertions.assertTrue(new AncientRome().discoveriesIn(Science.BIOLOGY).isEmpty());
    }

    @Test
    @DisplayName("Kepler should come before Newton, and both before Einstein")
    void physics_and_astronomy_in_order() {
        Humanity humanity = new Humanity();

        Assertions.assertEquals(List.of(Discoveries.UNIVERSAL_GRAVITATION, Discoveries.RADIOACTIVITY,
                Discoveries.BOHR_ATOM, Discoveries.GENERAL_RELATIVITY), humanity.discoveriesIn(Science.PHYSICS));
        Assertions.assertEquals(Discoveries.KEPLER_LAWS, humanity.knownBy(1636).get(humanity.knownBy(1636).size() - 1));
    }

    @Test
    @DisplayName("before Copernicus, humanity should only have known the four discoveries of antiquity")
    void known_by_year() {
        Assertions.assertEquals(List.of(Discoveries.SYLLOGISM, Discoveries.EUCLID_ELEMENTS, Discoveries.DE_ARCHITECTURA,
                Discoveries.ALMAGEST), new Humanity().knownBy(1542));
    }
}
