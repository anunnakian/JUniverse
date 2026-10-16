package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.StateOfMatter;
import com.enlil.universe.world.chemistry.Compounds;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompoundTest {

    private static Compound.CompoundBuilder acid() {
        return Compound.builder().name("Acid").formula(Formula.parse("HF")).standardState(StateOfMatter.LIQUID);
    }

    @Test
    @DisplayName("a compound cannot be both a weak and a strong acid")
    void reject_two_behaviours() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> acid().acidDissociationConstant(1e-5).strongAcid(true).build());
    }

    @Test
    @DisplayName("should reject a negative dissociation constant")
    void reject_negative_ka() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> acid().acidDissociationConstant(-1e-5).build());
    }

    @Test
    @DisplayName("compounds should be equal on name and formula, and expose their data")
    void equality_and_data() {
        Assertions.assertEquals(Compounds.WATER, Compounds.WATER);
        Assertions.assertNotEquals(Compounds.HYDROGEN_CHLORIDE, Compounds.HYDROCHLORIC_ACID);
        Assertions.assertEquals(Compounds.HYDROGEN_CHLORIDE.getFormula(), Compounds.HYDROCHLORIC_ACID.getFormula());
        Assertions.assertEquals(1.75e-5, Compounds.ACETIC_ACID.getAcidDissociationConstant().getAsDouble());
        Assertions.assertTrue(Compounds.WATER.getAcidDissociationConstant().isEmpty());
        Assertions.assertEquals(0, Compounds.OXYGEN_GAS.getEnthalpyOfFormationKjPerMole().getAsDouble());
        Assertions.assertEquals(44.01, Compounds.CARBON_DIOXIDE.molarMassGramsPerMole(), 0.01);
    }
}
