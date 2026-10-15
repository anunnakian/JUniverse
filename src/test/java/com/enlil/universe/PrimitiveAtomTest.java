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
}