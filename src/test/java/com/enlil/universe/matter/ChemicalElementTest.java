package com.enlil.universe.matter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChemicalElementTest {

    @Test
    @DisplayName("should find an element from its atomic number or symbol")
    void lookup() {
        Assertions.assertEquals(ChemicalElement.IRON, ChemicalElement.fromAtomicNumber(26));
        Assertions.assertEquals(ChemicalElement.SODIUM, ChemicalElement.fromSymbol("Na"));
        Assertions.assertEquals("Hydrogen", ChemicalElement.HYDROGEN.getDisplayName());
    }

    @Test
    @DisplayName("should reject an unknown atomic number")
    void reject_unknown() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ChemicalElement.fromAtomicNumber(0));
    }

    @Test
    @DisplayName("a nucleus should sum its protons and neutrons")
    void nucleus_numbers() {
        Nucleus helium = new Nucleus(2, 2);

        Assertions.assertEquals(4, helium.getMassNumber());
        Assertions.assertEquals(2, helium.getCharge());
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Nucleus(0, 1));
    }

    @Test
    @DisplayName("an electron should carry one negative elementary charge")
    void electron_charge() {
        Assertions.assertEquals(-1.602_176_634e-19, SubatomicParticle.ELECTRON.getChargeCoulombs());
    }
}
