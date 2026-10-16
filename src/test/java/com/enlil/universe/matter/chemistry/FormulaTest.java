package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.ChemicalElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FormulaTest {

    @Test
    @DisplayName("water should weigh 18.015 g/mol")
    void water_molar_mass() {
        Formula water = Formula.parse("H2O");

        Assertions.assertEquals(18.015, water.molarMassGramsPerMole(), 0.001);
        Assertions.assertEquals(2, water.count(ChemicalElement.HYDROGEN));
        Assertions.assertEquals(0.888, water.massFraction(ChemicalElement.OXYGEN), 0.001);
    }

    @Test
    @DisplayName("groups and hydrates should multiply their atoms")
    void groups_and_hydrates() {
        Assertions.assertEquals(74.09, Formula.parse("Ca(OH)2").molarMassGramsPerMole(), 0.01);
        Assertions.assertEquals(249.69, Formula.parse("CuSO4·5H2O").molarMassGramsPerMole(), 0.02);
        Assertions.assertEquals(Formula.parse("CuSO4·5H2O"), Formula.parse("CuSO4*5H2O"));
        Assertions.assertEquals(6, Formula.parse("K4[Fe(CN)6]").count(ChemicalElement.CARBON));
    }

    @Test
    @DisplayName("an ion should carry its charge")
    void ions() {
        Formula sulfate = Formula.parse("SO4^2-");

        Assertions.assertEquals(-2, sulfate.getCharge());
        Assertions.assertEquals("O4S^2-", sulfate.toString());
        Assertions.assertEquals(1, Formula.parse("NH4^+").getCharge());
    }

    @Test
    @DisplayName("formulas should print in Hill order")
    void hill_order() {
        Assertions.assertEquals("C2H6O", Formula.parse("CH3CH2OH").toString());
        Assertions.assertEquals("H2O", Formula.parse("OH2").toString());
        Assertions.assertEquals("ClNa", Formula.parse("NaCl").toString());
    }

    @Test
    @DisplayName("the order atoms are written in should not matter")
    void equality_on_counts() {
        Assertions.assertEquals(Formula.parse("H2O"), Formula.parse("OH2"));
        Assertions.assertEquals(Formula.parse("H2O").hashCode(), Formula.parse("OH2").hashCode());
        Assertions.assertNotEquals(Formula.parse("OH^-"), Formula.parse("OH"));
    }

    @Test
    @DisplayName("should reject malformed formulas")
    void reject_syntax_errors() {
        for (String text : new String[]{"", "Xx2", "H0", "Ca(OH2", "Ca(OH]2", "H2O!", "h2o", "SO4^2"}) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> Formula.parse(text), text);
        }
    }
}
