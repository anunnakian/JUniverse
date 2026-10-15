package com.enlil.universe.matter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AtomTest {

    @Test
    @DisplayName("should build a neutral atom of the most abundant isotope")
    void most_abundant_isotope() {
        Atom carbon = Atom.of(ChemicalElement.CARBON);

        Assertions.assertEquals(6, carbon.getProtons());
        Assertions.assertEquals(6, carbon.getNeutrons());
        Assertions.assertEquals(6, carbon.getElectrons());
        Assertions.assertEquals(12, carbon.getMassNumber());
        Assertions.assertFalse(carbon.isIon());
        Assertions.assertEquals("Carbon-12", carbon.getIsotopeName());
    }

    @Test
    @DisplayName("carbon-14 should be an isotope of carbon-12")
    void carbon_isotopes() {
        Atom carbon12 = Atom.of(ChemicalElement.CARBON);
        Atom carbon14 = Atom.isotope(ChemicalElement.CARBON, 14);

        Assertions.assertEquals(8, carbon14.getNeutrons());
        Assertions.assertTrue(carbon14.isIsotopeOf(carbon12));
        Assertions.assertFalse(carbon12.isIsotopeOf(carbon12));
        Assertions.assertFalse(carbon12.isIsotopeOf(Atom.of(ChemicalElement.NITROGEN)));
    }

    @Test
    @DisplayName("sodium should become a cation when losing an electron")
    void sodium_cation() {
        Atom sodiumIon = Atom.of(ChemicalElement.SODIUM).ionize();

        Assertions.assertEquals(1, sodiumIon.getCharge());
        Assertions.assertTrue(sodiumIon.isCation());
        Assertions.assertEquals("Na+", sodiumIon.getIonSymbol());
        Assertions.assertEquals("1s2 2s2 2p6", sodiumIon.getElectronConfiguration().toString());
    }

    @Test
    @DisplayName("chlorine should become an anion when gaining an electron")
    void chlorine_anion() {
        Atom chloride = Atom.of(ChemicalElement.CHLORINE).gainElectron();

        Assertions.assertTrue(chloride.isAnion());
        Assertions.assertEquals("Cl-", chloride.getIonSymbol());
        Assertions.assertTrue(chloride.getElectronConfiguration().isClosedShell());
    }

    @Test
    @DisplayName("should show the magnitude of a charge above one")
    void magnesium_ion_symbol() {
        Atom magnesiumIon = Atom.of(ChemicalElement.MAGNESIUM).ionize().ionize();

        Assertions.assertEquals("Mg2+", magnesiumIon.getIonSymbol());
        Assertions.assertEquals("Magnesium-24 (Mg2+)", magnesiumIon.toString());
    }

    @Test
    @DisplayName("losing then gaining an electron should give back the same atom")
    void ionize_and_recombine() {
        Atom oxygen = Atom.of(ChemicalElement.OXYGEN);

        Assertions.assertEquals(oxygen, oxygen.ionize().gainElectron());
    }

    @Test
    @DisplayName("should not ionize a bare nucleus")
    void reject_ionizing_bare_nucleus() {
        Atom proton = Atom.of(ChemicalElement.HYDROGEN).ionize();

        Assertions.assertThrows(IllegalStateException.class, proton::ionize);
    }

    @Test
    @DisplayName("should weigh close to its mass number in daltons")
    void mass_in_daltons() {
        // Free nucleons weigh slightly more than the bound atom, so allow a 1% margin
        Assertions.assertEquals(12, Atom.of(ChemicalElement.CARBON).getMassDaltons(), 0.12);
        Assertions.assertEquals(56, Atom.of(ChemicalElement.IRON).getMassDaltons(), 0.56);
    }

    @Test
    @DisplayName("should count the electrons of the outermost shell")
    void valence_electrons() {
        Assertions.assertEquals(4, Atom.of(ChemicalElement.CARBON).getValenceElectrons());
        Assertions.assertEquals(1, Atom.of(ChemicalElement.SODIUM).getValenceElectrons());
        Assertions.assertEquals(8, Atom.of(ChemicalElement.ARGON).getValenceElectrons());
    }

    @Test
    @DisplayName("should reject a nucleus of an unknown element")
    void reject_unknown_element() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Atom(new Nucleus(118, 176), 118));
    }
}
