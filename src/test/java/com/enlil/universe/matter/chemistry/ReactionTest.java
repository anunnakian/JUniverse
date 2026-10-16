package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.StateOfMatter;
import com.enlil.universe.world.chemistry.Reactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.enlil.universe.world.chemistry.Compounds.*;

public class ReactionTest {

    @Test
    @DisplayName("methane should burn as CH4 + 2 O2 -> CO2 + 2 H2O, releasing 890 kJ/mol")
    void methane_combustion() {
        Reaction combustion = Reactions.METHANE_COMBUSTION;

        Assertions.assertEquals(Map.of(METHANE, 1, OXYGEN_GAS, 2), combustion.getReactants());
        Assertions.assertEquals(Map.of(CARBON_DIOXIDE, 1, WATER, 2), combustion.getProducts());
        Assertions.assertEquals(-890, combustion.enthalpyKjPerMole(), 1);
        Assertions.assertTrue(combustion.isExothermic());
        Assertions.assertEquals("CH4 + 2 O2 -> CO2 + 2 H2O", combustion.toString());
    }

    @Test
    @DisplayName("glucose should burn with six of each, and photosynthesis take the heat back")
    void glucose_and_photosynthesis() {
        Reaction respiration = Reactions.GLUCOSE_COMBUSTION;

        Assertions.assertEquals(6, respiration.coefficientOf(OXYGEN_GAS));
        Assertions.assertEquals(6, respiration.coefficientOf(CARBON_DIOXIDE));
        Assertions.assertEquals(6, respiration.coefficientOf(WATER));
        Assertions.assertEquals(-respiration.enthalpyKjPerMole(), Reactions.PHOTOSYNTHESIS.enthalpyKjPerMole(), 1e-9);
        Assertions.assertFalse(Reactions.PHOTOSYNTHESIS.isExothermic());
    }

    @Test
    @DisplayName("the Haber process should balance to N2 + 3 H2 -> 2 NH3, and rust to 4 Fe + 3 O2 -> 2 Fe2O3")
    void haber_and_rust() {
        Assertions.assertEquals("N2 + 3 H2 -> 2 H3N", Reactions.HABER_PROCESS.toString());
        Assertions.assertEquals("4 Fe + 3 O2 -> 2 Fe2O3", Reactions.RUSTING.toString());
        Assertions.assertEquals(1, Reactions.NEUTRALIZATION.coefficientOf(WATER));
    }

    @Test
    @DisplayName("should refuse an impossible reaction and an ambiguous one")
    void reject_unbalanceable() {
        Compound peroxide = Compound.builder().name("Hydrogen peroxide").formula(Formula.parse("H2O2"))
                .standardState(StateOfMatter.LIQUID).build();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Reaction.balance(List.of(HYDROGEN_GAS), List.of(OXYGEN_GAS)));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Reaction.balance(List.of(HYDROGEN_GAS, OXYGEN_GAS), List.of(WATER, peroxide)));
    }

    @Test
    @DisplayName("should refuse explicit coefficients that don't conserve atoms")
    void reject_unbalanced_coefficients() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Reaction(Map.of(HYDROGEN_GAS, 1, OXYGEN_GAS, 1), Map.of(WATER, 1)));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Reaction(Map.of(HYDROGEN_GAS, 0, OXYGEN_GAS, 1), Map.of(WATER, 2)));
    }

    @Test
    @DisplayName("burning 1 kg of methane should give 2 743 g of carbon dioxide")
    void stoichiometric_mass() {
        Assertions.assertEquals(2_743, Reactions.METHANE_COMBUSTION.massGrams(METHANE, 1_000, CARBON_DIOXIDE), 1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Reactions.METHANE_COMBUSTION.massGrams(METHANE, 1_000, AMMONIA));
    }

    @Test
    @DisplayName("a reaction with a compound lacking data should have no enthalpy")
    void missing_enthalpy() {
        Compound mystery = Compound.builder().name("Mystery").formula(Formula.parse("H2O2"))
                .standardState(StateOfMatter.LIQUID).build();
        Reaction decomposition = Reaction.balance(List.of(mystery), List.of(WATER, OXYGEN_GAS));

        Assertions.assertEquals("2 H2O2 -> 2 H2O + O2", decomposition.toString());
        Assertions.assertThrows(IllegalStateException.class, decomposition::enthalpyKjPerMole);
    }
}
