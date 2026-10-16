package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.ChemicalElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BondTest {

    @Test
    @DisplayName("a sodium–chlorine bond should be ionic, with a difference of 2.23")
    void sodium_chloride_is_ionic() {
        Bond bond = new Bond("Na", "Cl", BondOrder.SINGLE);

        Assertions.assertEquals(2.23, bond.electronegativityDifference(), 1e-9);
        Assertions.assertEquals(BondType.IONIC, bond.getType());
        Assertions.assertTrue(bond.averageEnergyKjPerMole().isEmpty());
    }

    @Test
    @DisplayName("a bond to neon should have no electronegativity difference")
    void neon_has_no_electronegativity() {
        Bond bond = new Bond("Ne", "H", BondOrder.SINGLE);

        Assertions.assertThrows(IllegalStateException.class, bond::electronegativityDifference);
    }

    @Test
    @DisplayName("labels should name their element and look up average energies either way round")
    void labels_and_energies() {
        Assertions.assertEquals(ChemicalElement.CHLORINE, Bond.elementOf("Cl2"));
        Assertions.assertEquals(ChemicalElement.OXYGEN, Bond.elementOf("O1"));
        Assertions.assertEquals(413, new Bond("H1", "C", BondOrder.SINGLE).averageEnergyKjPerMole().getAsDouble());
        Assertions.assertEquals(945, new Bond("N1", "N2", BondOrder.TRIPLE).averageEnergyKjPerMole().getAsDouble());
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Bond("C", "C", BondOrder.SINGLE));
    }
}
