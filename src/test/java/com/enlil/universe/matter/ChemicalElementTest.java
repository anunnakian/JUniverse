package com.enlil.universe.matter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.OptionalInt;

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
        Assertions.assertThrows(IllegalArgumentException.class, () -> ChemicalElement.fromAtomicNumber(119));
    }

    @Test
    @DisplayName("the periodic table should hold 118 elements, numbered 1 to 118 without a gap")
    void full_periodic_table() {
        ChemicalElement[] elements = ChemicalElement.values();

        Assertions.assertEquals(118, elements.length);
        for (int i = 0; i < elements.length; i++) {
            Assertions.assertEquals(i + 1, elements[i].getAtomicNumber());
            Assertions.assertEquals(elements[i], ChemicalElement.fromSymbol(elements[i].getSymbol()));
        }
    }

    @Test
    @DisplayName("gold should weigh 196.97 Da and oganesson 294")
    void standard_atomic_weights() {
        Assertions.assertEquals(196.97, ChemicalElement.GOLD.getStandardAtomicWeight(), 0.01);
        Assertions.assertEquals(294, ChemicalElement.OGANESSON.getStandardAtomicWeight());
    }

    @Test
    @DisplayName("fluorine should be the most electronegative element, at 3.98")
    void fluorine_most_electronegative() {
        ChemicalElement most = Arrays.stream(ChemicalElement.values())
                .filter(element -> element.getElectronegativity().isPresent())
                .max(Comparator.comparingDouble(element -> element.getElectronegativity().getAsDouble()))
                .orElseThrow();

        Assertions.assertEquals(ChemicalElement.FLUORINE, most);
        Assertions.assertEquals(3.98, most.getElectronegativity().getAsDouble());
    }

    @Test
    @DisplayName("helium, neon and argon should have no electronegativity")
    void light_noble_gases_without_electronegativity() {
        Assertions.assertTrue(ChemicalElement.HELIUM.getElectronegativity().isEmpty());
        Assertions.assertTrue(ChemicalElement.NEON.getElectronegativity().isEmpty());
        Assertions.assertTrue(ChemicalElement.ARGON.getElectronegativity().isEmpty());
        Assertions.assertEquals(ElementCategory.NOBLE_GAS, ChemicalElement.ARGON.getCategory());
    }

    @Test
    @DisplayName("iron should be a transition metal of period 4, group 8, in the d-block")
    void iron_place_in_table() {
        Assertions.assertEquals(ElementCategory.TRANSITION_METAL, ChemicalElement.IRON.getCategory());
        Assertions.assertEquals(4, ChemicalElement.IRON.getPeriod());
        Assertions.assertEquals(OptionalInt.of(8), ChemicalElement.IRON.getGroup());
        Assertions.assertEquals(Block.D, ChemicalElement.IRON.getBlock());
    }

    @Test
    @DisplayName("blocks should follow the last subshell filled; lanthanides have no group")
    void blocks_and_groups() {
        Assertions.assertEquals(Block.S, ChemicalElement.HELIUM.getBlock());
        Assertions.assertEquals(Block.P, ChemicalElement.OXYGEN.getBlock());
        Assertions.assertEquals(Block.F, ChemicalElement.URANIUM.getBlock());
        Assertions.assertTrue(ChemicalElement.NEODYMIUM.getGroup().isEmpty());
        Assertions.assertEquals(ElementCategory.LANTHANIDE, ChemicalElement.NEODYMIUM.getCategory());
        Assertions.assertEquals(7, ChemicalElement.OGANESSON.getPeriod());
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
