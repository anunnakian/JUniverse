package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;
import com.enlil.universe.world.economy.Currencies;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

public class PolityTest {

    private static Empire westernRomanEmpire() {
        return new Empire("Western Roman Empire", "Rome", 60_000_000L, 5_000_000, TerritorialOrganization.UNITARY,
                -27, 476, Currencies.DENARIUS, MonarchyType.ABSOLUTE);
    }

    private static Republic switzerland() {
        return new Republic("Switzerland", "Bern", 9_000_000L, 41_285, TerritorialOrganization.FEDERAL, 1848,
                Currencies.SWISS_FRANC, SystemOfGovernment.DIRECTORIAL, "President of the Confederation", 1);
    }

    @Test
    @DisplayName("the Roman Empire should have lasted 502 years, from 27 BCE to 476, with no year 0")
    void duration_skips_year_zero() {
        Empire rome = westernRomanEmpire();

        Assertions.assertEquals(OptionalInt.of(502), rome.durationYears());
        Assertions.assertFalse(rome.isExtant());
        Assertions.assertEquals(1, Polity.yearsBetween(-1, 1));
        Assertions.assertEquals(10, Polity.yearsBetween(1990, 2000));
    }

    @Test
    @DisplayName("a polity should exist only between its founding and its dissolution")
    void existed_in() {
        Empire rome = westernRomanEmpire();

        Assertions.assertTrue(rome.existedIn(117));
        Assertions.assertFalse(rome.existedIn(-44));
        Assertions.assertFalse(rome.existedIn(1453));
        Assertions.assertTrue(switzerland().existedIn(2026));
        Assertions.assertEquals(OptionalInt.empty(), switzerland().durationYears());
    }

    @Test
    @DisplayName("a monarchy should be hereditary and headed by its own kind of monarch")
    void monarchies() {
        Kingdom kingdom = new Kingdom("Some Kingdom", "Capital", 1_000, 10, TerritorialOrganization.UNITARY,
                1000, Currencies.EURO, MonarchyType.CONSTITUTIONAL);

        Assertions.assertEquals("Emperor", westernRomanEmpire().getHeadOfStateTitle());
        Assertions.assertTrue(westernRomanEmpire().isAbsolute());
        Assertions.assertEquals("King", kingdom.getHeadOfStateTitle());
        Assertions.assertFalse(kingdom.isAbsolute());
        Assertions.assertEquals(Succession.HEREDITARY, kingdom.getSuccession());
    }

    @Test
    @DisplayName("a republic should elect its head of state, whatever the title")
    void republics() {
        Republic switzerland = switzerland();

        Assertions.assertEquals(Succession.ELECTED, switzerland.getSuccession());
        Assertions.assertEquals("President of the Confederation", switzerland.getHeadOfStateTitle());
        Assertions.assertEquals(218, switzerland.populationDensityPerSquareKm(), 1);
    }

    @Test
    @DisplayName("a polity should have people, land, and a history in the right order")
    void rejects_invalid_polities() {
        TerritorialOrganization unitary = TerritorialOrganization.UNITARY;
        MonarchyType absolute = MonarchyType.ABSOLUTE;
        Currency euro = Currencies.EURO;

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Kingdom("Empty", "-", 0, 1, unitary, 1000, euro, absolute));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Kingdom("Landless", "-", 1, 0, unitary, 1000, euro, absolute));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Kingdom("Year zero", "-", 1, 1, unitary, 0, euro, absolute));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Kingdom("Backwards", "-", 1, 1, unitary, 1000, 900, euro, absolute));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Republic("Termless", "-", 1, 1, unitary, 1000, euro, SystemOfGovernment.PRESIDENTIAL, 0));
        Assertions.assertThrows(NullPointerException.class,
                () -> new Kingdom("Moneyless", "-", 1, 1, unitary, 1000, null, absolute));
        Assertions.assertThrows(IllegalArgumentException.class, () -> westernRomanEmpire().ageYears(-100));
    }
}
