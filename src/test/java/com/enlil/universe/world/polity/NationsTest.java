package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.economy.MonetaryStandard;
import com.enlil.universe.civilization.polity.Empire;
import com.enlil.universe.civilization.polity.Kingdom;
import com.enlil.universe.civilization.polity.Polity;
import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.polity.Succession;
import com.enlil.universe.civilization.polity.SystemOfGovernment;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.AncientRome;
import com.enlil.universe.world.Humanity;
import com.enlil.universe.world.economy.Currencies;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

public class NationsTest {

    private final List<Polity> nations = new Humanity().getPolities();

    @Test
    @DisplayName("France should hold about 106 people per km², the United States about 35")
    void population_densities() {
        Assertions.assertEquals(106, new France().populationDensityPerSquareKm(), 1);
        Assertions.assertEquals(35, new UnitedStates().populationDensityPerSquareKm(), 1);
        Assertions.assertEquals(147, new China().populationDensityPerSquareKm(), 1);
    }

    @Test
    @DisplayName("Monaco should be the densest country and Vatican City the smallest")
    void extremes() {
        Assertions.assertEquals("Monaco",
                nations.stream().max(Comparator.comparingDouble(Polity::populationDensityPerSquareKm)).orElseThrow().getName());
        Assertions.assertEquals("Vatican City",
                nations.stream().min(Comparator.comparingDouble(Polity::getAreaSquareKm)).orElseThrow().getName());
        Assertions.assertEquals("China",
                nations.stream().max(Comparator.comparingLong(Polity::getPopulation)).orElseThrow().getName());
    }

    @Test
    @DisplayName("Japan should be the last empire, under a constitution")
    void japan_is_the_last_empire() {
        List<Polity> empires = nations.stream().filter(Empire.class::isInstance).toList();

        Assertions.assertEquals(1, empires.size());
        Assertions.assertEquals("Japan", empires.get(0).getName());
        Assertions.assertFalse(new Japan().isAbsolute());
    }

    @Test
    @DisplayName("Saudi Arabia and Oman should be absolute monarchies, the United Kingdom a constitutional one")
    void monarchies() {
        Assertions.assertTrue(new SaudiArabia().isAbsolute());
        Assertions.assertTrue(new Oman().isAbsolute());
        Assertions.assertFalse(new UnitedKingdom().isAbsolute());
        Assertions.assertInstanceOf(Kingdom.class, new UnitedKingdom());
        Assertions.assertEquals("Sultan", new Oman().getHeadOfStateTitle());
        Assertions.assertEquals("Prince", new Monaco().getHeadOfStateTitle());
    }

    @Test
    @DisplayName("Malaysia should elect its king, and the cardinals the pope")
    void elected_monarchs() {
        Assertions.assertEquals(Succession.ELECTED, new Malaysia().getSuccession());
        Assertions.assertEquals(TerritorialOrganization.FEDERAL, new Malaysia().getTerritorialOrganization());
        Assertions.assertEquals(Succession.ELECTED, new VaticanCity().getSuccession());
        Assertions.assertEquals("Pope", new VaticanCity().getHeadOfStateTitle());
        Assertions.assertEquals("Catholicism", new VaticanCity().getReligion());
    }

    @Test
    @DisplayName("each republic should have its own system and term")
    void republics() {
        Assertions.assertEquals(SystemOfGovernment.PRESIDENTIAL, new UnitedStates().getSystemOfGovernment());
        Assertions.assertEquals(4, new UnitedStates().getHeadOfStateTermYears());
        Assertions.assertEquals(SystemOfGovernment.SEMI_PRESIDENTIAL, new France().getSystemOfGovernment());
        Assertions.assertEquals(SystemOfGovernment.PARLIAMENTARY, new Germany().getSystemOfGovernment());
        Assertions.assertEquals(SystemOfGovernment.ONE_PARTY, new China().getSystemOfGovernment());
        Assertions.assertEquals(1, new Switzerland().getHeadOfStateTermYears());
        Assertions.assertTrue(nations.stream().filter(Republic.class::isInstance)
                .allMatch(republic -> republic.getSuccession() == Succession.ELECTED));
    }

    @Test
    @DisplayName("the Roman Empire should have lasted 502 years and be gone, unlike every nation of today")
    void roman_empire() {
        RomanEmpire rome = new RomanEmpire();

        Assertions.assertEquals(OptionalInt.of(502), rome.durationYears());
        Assertions.assertEquals(List.of(rome.getName()),
                new AncientRome().politiesIn(117).stream().map(Polity::getName).toList());
        Assertions.assertTrue(new AncientRome().politiesIn(2026).isEmpty());
        Humanity humanity = new Humanity();
        Assertions.assertEquals(humanity.getPolities(), humanity.politiesIn(2026));
    }

    @Test
    @DisplayName("France, Germany, Monaco and Vatican City should share the euro, and Rome pay in silver")
    void currencies() {
        Humanity humanity = new Humanity();

        Assertions.assertSame(Currencies.EURO, new Monaco().getCurrency());
        Assertions.assertSame(new France().getCurrency(), new VaticanCity().getCurrency());
        Assertions.assertEquals(9, humanity.getCurrencies().size());
        Assertions.assertEquals(Set.of(MonetaryStandard.FIAT), humanity.getMonetaryStandards());
        Assertions.assertEquals(List.of(Currencies.DENARIUS), new AncientRome().getCurrencies());
        Assertions.assertEquals(Set.of(MonetaryStandard.COMMODITY), new AncientRome().getMonetaryStandards());
    }

    @Test
    @DisplayName("same-sex couples should marry in France since 2013, and their love be a crime in Saudi Arabia")
    void lgbtq_rights() {
        Assertions.assertEquals(OptionalInt.of(2013), new France().getSameSexMarriageYear());
        Assertions.assertEquals(OptionalInt.of(2015), new UnitedStates().getSameSexMarriageYear());
        Assertions.assertEquals(OptionalInt.empty(), new Japan().getSameSexMarriageYear());
        Assertions.assertTrue(new SaudiArabia().criminalizesHomosexuality());
        Assertions.assertTrue(new Malaysia().criminalizesHomosexuality());
        Assertions.assertFalse(new France().criminalizesHomosexuality());
        Assertions.assertEquals(5, nations.stream()
                .filter(nation -> nation.getSameSexMarriageYear().isPresent())
                .count());
    }

    @Test
    @DisplayName("the United States should be 250 years old in 2026, Monaco's Grimaldis 729")
    void ages() {
        Assertions.assertEquals(250, new UnitedStates().ageYears(2026));
        Assertions.assertEquals(729, new Monaco().ageYears(2026));
    }
}
