package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.StateOfMatter;
import com.enlil.universe.world.chemistry.Compounds;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoleculeTest {

    @Test
    @DisplayName("carbon dioxide should have polar bonds, hydrogen a nonpolar one")
    void bond_polarity() {
        Compounds.CARBON_DIOXIDE.getBonds().forEach(bond ->
                Assertions.assertEquals(BondType.POLAR_COVALENT, bond.getType()));
        Assertions.assertEquals(BondType.NONPOLAR_COVALENT, Compounds.HYDROGEN_GAS.getBonds().get(0).getType());
    }

    @Test
    @DisplayName("a molecule should derive its formula from its atoms")
    void derived_formula() {
        Assertions.assertEquals(Formula.parse("C2H6O"), Compounds.ETHANOL.getFormula());
        Assertions.assertEquals(Formula.parse("CO2"), Compounds.CARBON_DIOXIDE.getFormula());
    }

    @Test
    @DisplayName("should reject a carbon with five bonds")
    void reject_pentavalent_carbon() {
        Molecule.Builder builder = Molecule.builder().name("Impossible").standardState(StateOfMatter.GAS)
                .atom("C").atom("H1").atom("H2").atom("H3").atom("H4").atom("H5")
                .bond("C", "H1", BondOrder.SINGLE).bond("C", "H2", BondOrder.SINGLE)
                .bond("C", "H3", BondOrder.SINGLE).bond("C", "H4", BondOrder.SINGLE)
                .bond("C", "H5", BondOrder.SINGLE);

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    @DisplayName("should reject a molecule in two pieces")
    void reject_disconnected() {
        Molecule.Builder builder = Molecule.builder().name("Two hydrogens").standardState(StateOfMatter.GAS)
                .atom("H1").atom("H2").atom("H3").atom("H4")
                .bond("H1", "H2", BondOrder.SINGLE).bond("H3", "H4", BondOrder.SINGLE);

        Assertions.assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    @DisplayName("should reject unknown atoms, duplicate labels and a molecule given a formula")
    void reject_bad_structure() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Molecule.builder().atom("H").atom("H"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Molecule.builder().name("x")
                .standardState(StateOfMatter.GAS).atom("H1").bond("H1", "H2", BondOrder.SINGLE).build());
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> Molecule.builder().formula(Formula.parse("H2")));
    }

    @Test
    @DisplayName("breaking methane apart should take four C–H bonds, 4 × 413 kJ/mol")
    void methane_atomization() {
        Assertions.assertEquals(4 * 413, Compounds.METHANE.atomizationEnergyKjPerMole(), 1e-9);
    }
}
