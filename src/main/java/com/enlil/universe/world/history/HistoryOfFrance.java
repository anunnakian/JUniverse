package com.enlil.universe.world.history;

import com.enlil.universe.civilization.economy.Currency;
import com.enlil.universe.civilization.history.HistoricalEvent;
import com.enlil.universe.civilization.occupation.Occupation;
import com.enlil.universe.civilization.polity.Empire;
import com.enlil.universe.civilization.polity.Kingdom;
import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.Polity;
import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.polity.SystemOfGovernment;
import com.enlil.universe.civilization.science.Discovery;
import com.enlil.universe.world.economy.Currencies;
import com.enlil.universe.world.occupation.Doctor;
import com.enlil.universe.world.occupation.Engineer;
import com.enlil.universe.world.occupation.Firefighter;
import com.enlil.universe.world.occupation.Knight;
import com.enlil.universe.world.occupation.Musketeer;
import com.enlil.universe.world.occupation.Soldier;
import com.enlil.universe.world.occupation.Teacher;
import com.enlil.universe.world.polity.France;
import com.enlil.universe.world.science.Discoveries;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static com.enlil.universe.civilization.polity.TerritorialOrganization.UNITARY;

/**
 * The history of France since Clovis, as data: its fourteen regimes, from the Kingdom of the Franks to the Fifth
 * Republic, the events that made and unmade them, its discoveries and some of its people's occupations. Each regime's
 * population and area are taken near its end or peak; before the first censuses they are rough historians' estimates.
 * The Provisional Government of 1944–1946 is not modeled.
 */
public final class HistoryOfFrance {

    /** Clovis becomes king of the Salian Franks. */
    public static final int CLOVIS_YEAR = 481;
    /** Francs for one euro, irrevocably fixed on 31 December 1998. */
    public static final double FRANCS_PER_EURO = 6.559_57;

    public static final Kingdom KINGDOM_OF_THE_FRANKS = new Kingdom("Kingdom of the Franks", "Paris", 6_000_000L,
            500_000, UNITARY, 481, 800, Currencies.SOLIDUS, MonarchyType.ABSOLUTE);
    public static final Empire CAROLINGIAN_EMPIRE = new Empire("Carolingian Empire", "Aachen", 15_000_000L,
            1_112_000, UNITARY, 800, 843, Currencies.LIVRE, MonarchyType.ABSOLUTE);
    public static final Kingdom WEST_FRANCIA = new Kingdom("West Francia", "Paris", 9_000_000L,
            470_000, UNITARY, 843, 987, Currencies.LIVRE, MonarchyType.ABSOLUTE);
    public static final Kingdom KINGDOM_OF_FRANCE = new Kingdom("Kingdom of France", "Paris", 28_000_000L,
            530_000, UNITARY, 987, 1792, Currencies.LIVRE, MonarchyType.ABSOLUTE);
    /** Under the Directory (1795–1799), five directors held executive power, for five years each. */
    public static final Republic FIRST_REPUBLIC = new Republic("French First Republic", "Paris", 29_000_000L,
            550_000, UNITARY, 1792, 1804, Currencies.FRANC_GERMINAL, SystemOfGovernment.DIRECTORIAL, "Director", 5);
    /** At its 1812 peak, with 130 departments from Hamburg to Rome. */
    public static final Empire FIRST_EMPIRE = new Empire("First French Empire", "Paris", 44_000_000L,
            860_000, UNITARY, 1804, 1814, Currencies.FRANC_GERMINAL, MonarchyType.ABSOLUTE);
    public static final Kingdom RESTORATION = new Kingdom("Bourbon Restoration", "Paris", 32_500_000L,
            528_000, UNITARY, 1814, 1830, Currencies.FRANC_GERMINAL, MonarchyType.CONSTITUTIONAL);
    public static final Kingdom JULY_MONARCHY = new Kingdom("July Monarchy", "Paris", 35_400_000L,
            528_000, UNITARY, 1830, 1848, Currencies.FRANC_GERMINAL, MonarchyType.CONSTITUTIONAL);
    public static final Republic SECOND_REPUBLIC = new Republic("French Second Republic", "Paris", 35_800_000L,
            528_000, UNITARY, 1848, 1852, Currencies.FRANC_GERMINAL, SystemOfGovernment.PRESIDENTIAL, "President", 4);
    public static final Empire SECOND_EMPIRE = new Empire("Second French Empire", "Paris", 38_000_000L,
            543_000, UNITARY, 1852, 1870, Currencies.FRANC_GERMINAL, MonarchyType.ABSOLUTE);
    public static final Republic THIRD_REPUBLIC = new Republic("French Third Republic", "Paris", 41_500_000L,
            551_500, UNITARY, 1870, 1940, Currencies.FRANC_GERMINAL, SystemOfGovernment.PARLIAMENTARY,
            "President", 7);
    public static final FrenchState FRENCH_STATE = new FrenchState();
    public static final Republic FOURTH_REPUBLIC = new Republic("French Fourth Republic", "Paris", 42_800_000L,
            551_500, UNITARY, 1946, 1958, Currencies.FRENCH_FRANC, SystemOfGovernment.PARLIAMENTARY, "President", 7);
    public static final France FIFTH_REPUBLIC = new France();

    private HistoryOfFrance() {
    }

    /** The regimes of France, in order. */
    public static List<Polity> regimes() {
        return List.of(KINGDOM_OF_THE_FRANKS, CAROLINGIAN_EMPIRE, WEST_FRANCIA, KINGDOM_OF_FRANCE, FIRST_REPUBLIC,
                FIRST_EMPIRE, RESTORATION, JULY_MONARCHY, SECOND_REPUBLIC, SECOND_EMPIRE, THIRD_REPUBLIC,
                FRENCH_STATE, FOURTH_REPUBLIC, FIFTH_REPUBLIC);
    }

    /**
     * The money of France by the year it came in: the Merovingian gold solidus, the livre of Charlemagne's reform
     * (781), the decimal franc (1795), the franc off gold when the Great War suspended its convertibility (1914), and
     * the euro, in cash from 2002. A regime keeps the last currency it used, so these dates are finer than regimes.
     */
    public static NavigableMap<Integer, Currency> currencies() {
        return Collections.unmodifiableNavigableMap(new TreeMap<>(Map.of(
                481, Currencies.SOLIDUS,
                781, Currencies.LIVRE,
                1795, Currencies.FRANC_GERMINAL,
                1914, Currencies.FRENCH_FRANC,
                2002, Currencies.EURO)));
    }

    /** Discoveries made in France, in order. */
    public static List<Discovery> discoveries() {
        return List.of(Discoveries.ANALYTIC_GEOMETRY, Discoveries.MECHANICAL_CALCULATOR,
                Discoveries.CONSERVATION_OF_MASS, Discoveries.RABIES_VACCINE, Discoveries.SOCIAL_FACTS,
                Discoveries.RADIOACTIVITY, Discoveries.INTELLIGENCE_TEST);
    }

    /** Some occupations of the French through their history, from the knight to the musketeer. */
    public static List<Occupation> occupations() {
        return List.of(new Soldier(), new Doctor(), new Engineer(), new Teacher(), new Firefighter(), new Knight(),
                new Musketeer());
    }

    /** The events of the history of France, in order. */
    public static List<HistoricalEvent> events() {
        return List.of(
                new HistoricalEvent(481, "Clovis becomes king of the Salian Franks, at about fifteen"),
                new HistoricalEvent(496, "Clovis is baptized at Reims, by tradition on Christmas Day"),
                new HistoricalEvent(508, "Clovis makes Paris his capital"),
                new HistoricalEvent(732, "Charles Martel stops the Umayyad army between Tours and Poitiers"),
                new HistoricalEvent(800, "Charlemagne is crowned emperor in Rome on Christmas Day"),
                new HistoricalEvent(843, "The Treaty of Verdun splits the empire among Charlemagne's grandsons"),
                new HistoricalEvent(987, "Hugh Capet is elected king: his dynasty will reign for eight centuries"),
                new HistoricalEvent(1163, "Building begins on Notre-Dame de Paris"),
                new HistoricalEvent(1214, "Philip II Augustus wins the battle of Bouvines"),
                new HistoricalEvent(1337, "The Hundred Years' War with England begins"),
                new HistoricalEvent(1429, "Joan of Arc lifts the siege of Orleans"),
                new HistoricalEvent(1453, "The Hundred Years' War ends"),
                new HistoricalEvent(1539, "The ordinance of Villers-Cotterets makes French the language of the law"),
                new HistoricalEvent(1598, "Henry IV signs the Edict of Nantes, granting tolerance to Protestants"),
                new HistoricalEvent(1643, "Louis XIV comes to the throne at four, for a 72-year reign"),
                new HistoricalEvent(1682, "The court settles at Versailles"),
                new HistoricalEvent(1789, "The storming of the Bastille, and the Declaration of the Rights of Man"),
                new HistoricalEvent(1791, "The new penal code drops the crime of sodomy, one of the first to do so"),
                new HistoricalEvent(1792, "The monarchy is abolished and the Republic proclaimed"),
                new HistoricalEvent(1793, "Louis XVI is guillotined"),
                new HistoricalEvent(1799, "Napoleon Bonaparte seizes power on 18 Brumaire"),
                new HistoricalEvent(1804, "Napoleon crowns himself emperor; the Civil Code is enacted"),
                new HistoricalEvent(1815, "Waterloo ends the Hundred Days"),
                new HistoricalEvent(1848, "Universal male suffrage, and the abolition of slavery"),
                new HistoricalEvent(1870, "Defeat at Sedan; the Republic is proclaimed again"),
                new HistoricalEvent(1889, "The Eiffel Tower opens for the World's Fair"),
                new HistoricalEvent(1905, "The law separating the Churches and the State"),
                new HistoricalEvent(1914, "The First World War begins; the franc is no longer convertible to gold"),
                new HistoricalEvent(1918, "The armistice of 11 November"),
                new HistoricalEvent(1940, "Defeat; Petain is granted full powers at Vichy"),
                new HistoricalEvent(1944, "The liberation of Paris; a provisional government takes over"),
                new HistoricalEvent(1945, "Women vote for the first time"),
                new HistoricalEvent(1958, "The Fifth Republic, under Charles de Gaulle"),
                new HistoricalEvent(1960, "The new franc, worth 100 old francs"),
                new HistoricalEvent(1968, "The strikes and protests of May 68"),
                new HistoricalEvent(1981, "The death penalty is abolished"),
                new HistoricalEvent(1982, "The last law discriminating against homosexuals is repealed"),
                new HistoricalEvent(1999, "The civil solidarity pact opens a legal union to all couples"),
                new HistoricalEvent(2002, "Euro notes and coins replace the franc, at 6.55957 francs a euro"),
                new HistoricalEvent(2013, "Marriage for all: same-sex couples may marry"));
    }
}
