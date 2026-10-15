package com.enlil.universe.civilization;

import com.enlil.universe.civilization.polity.Kingdom;
import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.Humanity;
import com.enlil.universe.world.economy.Currencies;
import com.enlil.universe.world.AncientRome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CivilizationTest {

    /** A civilization of nothing in particular, to check the rules every civilization follows. */
    private static Civilization civilization(long population, double powerWatts) {
        return new Civilization("Test", population, powerWatts) {
            @Override
            public String getSpecies() {
                return "Testus testus";
            }

            @Override
            public TechnologicalEra getEra() {
                return TechnologicalEra.STONE_AGE;
            }
        };
    }

    @Test
    @DisplayName("humanity should rate about 0.73 on the Kardashev scale, still a type 0")
    void humanity_kardashev_rating() {
        Humanity humanity = new Humanity();

        Assertions.assertEquals(0.73, humanity.kardashevRating(), 0.01);
        Assertions.assertEquals(KardashevType.TYPE_0, humanity.kardashevType());
    }

    @Test
    @DisplayName("each human should harness about 2.4 kW, a Roman about 1.5 kW")
    void power_per_capita() {
        Assertions.assertEquals(2_426, new Humanity().powerPerCapitaWatts(), 10);
        Assertions.assertEquals(1_500, new AncientRome().powerPerCapitaWatts(), 10);
    }

    @Test
    @DisplayName("ancient Rome should rate about 0.50 on the Kardashev scale")
    void ancient_rome_kardashev_rating() {
        Assertions.assertEquals(0.50, new AncientRome().kardashevRating(), 0.01);
    }

    @Test
    @DisplayName("humanity should take about 315 years to reach type I at 2 % growth a year")
    void humanity_years_to_type_one() {
        Assertions.assertEquals(315, new Humanity().yearsToReach(KardashevType.TYPE_I, 0.02), 2);
    }

    @Test
    @DisplayName("a civilization should need no time to reach a type it already has")
    void already_reached() {
        Civilization civilization = civilization(1, 1e17);

        Assertions.assertEquals(0, civilization.yearsToReach(KardashevType.TYPE_I, 0.02));
    }

    @Test
    @DisplayName("a civilization should have people and some power")
    void rejects_invalid_civilizations() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> civilization(0, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> civilization(1, 0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> civilization(1, 1).yearsToReach(KardashevType.TYPE_I, 0));
    }

    @Test
    @DisplayName("a civilization should find the polities standing in a given year")
    void polities_in_a_year() {
        Kingdom england = new Kingdom("Kingdom of England", "London", 5_000_000L, 130_279,
                TerritorialOrganization.UNITARY, 927, 1707, Currencies.POUND_STERLING, MonarchyType.ABSOLUTE);
        Kingdom greatBritain = new Kingdom("Kingdom of Great Britain", "London", 8_000_000L, 209_331,
                TerritorialOrganization.UNITARY, 1707, 1800, Currencies.POUND_STERLING,
                MonarchyType.CONSTITUTIONAL);
        Civilization civilization = new Civilization("Test", 1, 1, List.of(england, greatBritain)) {
            @Override
            public String getSpecies() {
                return "Homo sapiens";
            }

            @Override
            public TechnologicalEra getEra() {
                return TechnologicalEra.INDUSTRIAL;
            }
        };

        Assertions.assertEquals(List.of(england), civilization.politiesIn(1066));
        Assertions.assertEquals(List.of(england, greatBritain), civilization.politiesIn(1707));
        Assertions.assertEquals(List.of(), civilization.politiesIn(1900));
    }

    @Test
    @DisplayName("the information era should come after the iron age")
    void eras_are_ordered() {
        Assertions.assertTrue(new Humanity().getEra().isAtLeast(new AncientRome().getEra()));
        Assertions.assertFalse(TechnologicalEra.IRON_AGE.isAtLeast(TechnologicalEra.INDUSTRIAL));
    }
}
