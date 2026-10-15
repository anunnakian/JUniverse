package com.enlil.universe.matter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StateOfMatterTest {

    private static final double ROOM_TEMPERATURE_KELVIN = 293.15;

    @Test
    @DisplayName("hydrogen, helium, nitrogen, oxygen and the noble gases should be gases at room temperature")
    void gases_at_room_temperature() {
        for (ChemicalElement element : new ChemicalElement[]{ChemicalElement.HYDROGEN, ChemicalElement.HELIUM,
                ChemicalElement.NITROGEN, ChemicalElement.OXYGEN, ChemicalElement.FLUORINE, ChemicalElement.NEON,
                ChemicalElement.CHLORINE, ChemicalElement.ARGON}) {
            Assertions.assertEquals(StateOfMatter.GAS, StateOfMatter.of(element, ROOM_TEMPERATURE_KELVIN), element.name());
        }
    }

    @Test
    @DisplayName("metals, carbon and sulfur should be solids at room temperature")
    void solids_at_room_temperature() {
        for (ChemicalElement element : new ChemicalElement[]{ChemicalElement.IRON, ChemicalElement.CARBON,
                ChemicalElement.SODIUM, ChemicalElement.ALUMINIUM, ChemicalElement.SULFUR}) {
            Assertions.assertEquals(StateOfMatter.SOLID, StateOfMatter.of(element, ROOM_TEMPERATURE_KELVIN), element.name());
        }
    }

    @Test
    @DisplayName("no modeled element should be liquid at room temperature (that's bromine and mercury)")
    void no_liquid_at_room_temperature() {
        for (ChemicalElement element : ChemicalElement.values()) {
            Assertions.assertNotEquals(StateOfMatter.LIQUID, StateOfMatter.of(element, ROOM_TEMPERATURE_KELVIN), element.name());
        }
    }

    @Test
    @DisplayName("iron should melt at 1811 K and boil at 3134 K")
    void iron_phases() {
        Assertions.assertEquals(StateOfMatter.SOLID, StateOfMatter.of(ChemicalElement.IRON, 1_800));
        Assertions.assertEquals(StateOfMatter.LIQUID, StateOfMatter.of(ChemicalElement.IRON, 2_000));
        Assertions.assertEquals(StateOfMatter.GAS, StateOfMatter.of(ChemicalElement.IRON, 3_200));
    }

    @Test
    @DisplayName("liquid nitrogen should exist between 63 K and 77 K")
    void liquid_nitrogen() {
        Assertions.assertEquals(StateOfMatter.LIQUID, StateOfMatter.of(ChemicalElement.NITROGEN, 70));
        Assertions.assertEquals(StateOfMatter.SOLID, StateOfMatter.of(ChemicalElement.NITROGEN, 60));
    }

    @Test
    @DisplayName("helium should never freeze at atmospheric pressure")
    void helium_never_freezes() {
        Assertions.assertEquals(StateOfMatter.LIQUID, StateOfMatter.of(ChemicalElement.HELIUM, 0.01));
        Assertions.assertTrue(Double.isNaN(ChemicalElement.HELIUM.getMeltingPointKelvin()));
    }

    @Test
    @DisplayName("carbon should sublimate without ever being liquid at atmospheric pressure")
    void carbon_sublimates() {
        Assertions.assertEquals(StateOfMatter.SOLID, StateOfMatter.of(ChemicalElement.CARBON, 3_900));
        Assertions.assertEquals(StateOfMatter.GAS, StateOfMatter.of(ChemicalElement.CARBON, 3_920));
    }

    @Test
    @DisplayName("only solids should keep their shape, and gases and plasmas should fill their container")
    void shape_and_volume() {
        Assertions.assertTrue(StateOfMatter.SOLID.hasDefiniteShape());
        Assertions.assertFalse(StateOfMatter.LIQUID.hasDefiniteShape());
        Assertions.assertTrue(StateOfMatter.LIQUID.hasDefiniteVolume());
        Assertions.assertFalse(StateOfMatter.GAS.hasDefiniteVolume());
        Assertions.assertFalse(StateOfMatter.PLASMA.hasDefiniteVolume());
    }

    @Test
    @DisplayName("should reject a temperature below absolute zero")
    void reject_negative_temperature() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> StateOfMatter.of(ChemicalElement.IRON, -1));
    }
}
