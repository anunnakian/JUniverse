package com.enlil.universe.world.history;

import com.enlil.universe.civilization.economy.Money;
import com.enlil.universe.civilization.history.HistoricalEvent;
import com.enlil.universe.civilization.polity.Polity;
import com.enlil.universe.civilization.polity.Succession;
import com.enlil.universe.world.economy.Currencies;
import com.enlil.universe.world.occupation.Musketeer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.OptionalInt;

public class HistoryOfFranceTest {

    private final List<Polity> regimes = HistoryOfFrance.regimes();

    @Test
    @DisplayName("each regime should start the year the previous one ends, but for the provisional government")
    void regimes_follow_one_another() {
        for (int i = 1; i < regimes.size(); i++) {
            int previousEnd = regimes.get(i - 1).getDissolvedYear().orElseThrow();
            int start = regimes.get(i).getFoundedYear();
            if (previousEnd == 1944) {
                Assertions.assertEquals(1946, start);
            } else {
                Assertions.assertEquals(previousEnd, start, regimes.get(i).getName());
            }
        }
        Assertions.assertEquals(HistoryOfFrance.CLOVIS_YEAR, regimes.get(0).getFoundedYear());
        Assertions.assertTrue(regimes.get(regimes.size() - 1).isExtant());
    }

    @Test
    @DisplayName("the Kingdom of France should have lasted 805 years, the Franks' 319, the Second Republic 4")
    void durations() {
        Assertions.assertEquals(OptionalInt.of(319), HistoryOfFrance.KINGDOM_OF_THE_FRANKS.durationYears());
        Assertions.assertEquals(OptionalInt.of(805), HistoryOfFrance.KINGDOM_OF_FRANCE.durationYears());
        Assertions.assertEquals(OptionalInt.of(4), HistoryOfFrance.SECOND_REPUBLIC.durationYears());
    }

    @Test
    @DisplayName("Vichy's French State should be neither monarchy nor republic, its chief appointed")
    void french_state() {
        Assertions.assertEquals(Succession.APPOINTED, HistoryOfFrance.FRENCH_STATE.getSuccession());
        Assertions.assertEquals("Vichy", HistoryOfFrance.FRENCH_STATE.getCapital());
        Assertions.assertTrue(HistoryOfFrance.regimes().stream().noneMatch(regime -> regime.existedIn(1945)));
    }

    @Test
    @DisplayName("France should have paid in solidi, livres, francs and euros, each in its time")
    void money() {
        Assertions.assertSame(Currencies.SOLIDUS, HistoryOfFrance.currencies().floorEntry(500).getValue());
        Assertions.assertSame(Currencies.LIVRE, HistoryOfFrance.currencies().floorEntry(1789).getValue());
        Assertions.assertSame(Currencies.FRANC_GERMINAL, HistoryOfFrance.currencies().floorEntry(1900).getValue());
        Assertions.assertSame(Currencies.FRENCH_FRANC, HistoryOfFrance.currencies().floorEntry(1999).getValue());
        Assertions.assertSame(Currencies.EURO, HistoryOfFrance.currencies().floorEntry(2026).getValue());
        Assertions.assertEquals(240, Currencies.LIVRE.getMinorUnitsPerUnit());
    }

    @Test
    @DisplayName("100 francs should have become 15.24 euros")
    void franc_to_euro() {
        Money francs = Money.of(Currencies.FRENCH_FRANC, 100);

        Assertions.assertEquals(Money.of(Currencies.EURO, 15.24),
                Money.of(Currencies.EURO, francs.getAmount() / HistoryOfFrance.FRANCS_PER_EURO));
    }

    @Test
    @DisplayName("events should be told in order, from Clovis on")
    void events_in_order() {
        List<HistoricalEvent> events = HistoryOfFrance.events();

        Assertions.assertEquals(HistoryOfFrance.CLOVIS_YEAR, events.get(0).getYear());
        for (int i = 1; i < events.size(); i++) {
            Assertions.assertTrue(events.get(i - 1).getYear() <= events.get(i).getYear(), events.get(i).toString());
        }
    }

    @Test
    @DisplayName("the musketeers should carry a musket and a sword, from 1622 to 1776")
    void musketeers() {
        Musketeer musketeer = new Musketeer();

        Assertions.assertTrue(musketeer.carriesFirearms());
        Assertions.assertTrue(musketeer.existedIn(1700));
        Assertions.assertFalse(musketeer.existedIn(1789));
    }
}
