package com.enlil.universe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PrimitiveAtomTest {

    @Test
    @DisplayName("should create a universe")
    void create_universe() {
        PrimitiveAtom primitiveAtom = new PrimitiveAtom();

        Universe universe = primitiveAtom.bigBand();

        Assertions.assertNotNull(universe);
    }

    @Test
    @DisplayName("the universe born from the primeval atom should know it is 13.8 billion years old")
    void universe_knows_its_age() {
        Universe universe = new PrimitiveAtom().bigBand();

        Assertions.assertEquals(13.8e9, universe.getCosmology().orElseThrow().ageYears(), 0.05e9);
        Assertions.assertTrue(Universe.builder().build().getCosmology().isEmpty());
    }
}
