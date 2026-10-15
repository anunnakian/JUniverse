package com.enlil.universe.civilization;

import com.enlil.universe.civilization.economy.Currency;
import com.enlil.universe.civilization.economy.MonetaryStandard;
import com.enlil.universe.civilization.occupation.Occupation;
import com.enlil.universe.civilization.polity.Polity;
import com.enlil.universe.civilization.science.Discovery;
import com.enlil.universe.civilization.science.Science;
import lombok.Getter;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A civilization: a population of some species harnessing a given power, living on the planet that holds it and
 * organized into polities (kingdoms, empires, republics...) that may follow one another through its history, and
 * knowing the discoveries it made or inherited, with the occupations its people work in.
 * Subclasses say which species built it and which technological era it reached; the energy physics is common to all
 * of them.
 */
@Getter
public abstract class Civilization {

    /** Reference power of Sagan's interpolation of the Kardashev scale, in watts (rating 0). */
    private static final double SAGAN_REFERENCE_POWER_WATTS = 1e6;

    private final String name;
    private final long population;
    private final double powerWatts;
    private final List<Polity> polities;
    /** What this civilization knows, in chronological order. */
    private final List<Discovery> discoveries;
    /** The jobs people do, or once did, in this civilization. */
    private final List<Occupation> occupations;

    protected Civilization(String name, long population, double powerWatts) {
        this(name, population, powerWatts, List.of());
    }

    protected Civilization(String name, long population, double powerWatts, List<Polity> polities) {
        this(name, population, powerWatts, polities, List.of());
    }

    protected Civilization(String name, long population, double powerWatts, List<Polity> polities,
                           List<Discovery> discoveries) {
        this(name, population, powerWatts, polities, discoveries, List.of());
    }

    protected Civilization(String name, long population, double powerWatts, List<Polity> polities,
                           List<Discovery> discoveries, List<Occupation> occupations) {
        if (population <= 0) {
            throw new IllegalArgumentException("population must be positive");
        }
        if (powerWatts <= 0) {
            throw new IllegalArgumentException("power must be positive");
        }
        this.name = name;
        this.population = population;
        this.powerWatts = powerWatts;
        this.polities = List.copyOf(polities);
        this.discoveries = discoveries.stream()
                .sorted(Comparator.comparingInt(Discovery::getYear))
                .toList();
        this.occupations = List.copyOf(occupations);
    }

    /** The species that built this civilization, as a binomial name. */
    public abstract String getSpecies();

    /** The technological era this civilization has reached. */
    public abstract TechnologicalEra getEra();

    /** The polities of this civilization that existed during the given year (negative for BCE). */
    public List<Polity> politiesIn(int year) {
        return polities.stream()
                .filter(polity -> polity.existedIn(year))
                .toList();
    }

    /** The occupations practised in this civilization during the given year (negative for BCE). */
    public List<Occupation> occupationsIn(int year) {
        return occupations.stream()
                .filter(occupation -> occupation.existedIn(year))
                .toList();
    }

    /** The currencies of this civilization's polities, each once, in the order of its polities. */
    public List<Currency> getCurrencies() {
        return polities.stream()
                .map(Polity::getCurrency)
                .distinct()
                .toList();
    }

    /**
     * What backs the money of this civilization: commodity coins, representative notes or fiat money. Empty for a
     * civilization without polities, living on barter.
     */
    public Set<MonetaryStandard> getMonetaryStandards() {
        return getCurrencies().stream()
                .map(Currency::getStandard)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(MonetaryStandard.class)));
    }

    /** The sciences this civilization has made at least one discovery in. */
    public Set<Science> getSciences() {
        return discoveries.stream()
                .map(Discovery::getScience)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Science.class)));
    }

    public List<Discovery> discoveriesIn(Science science) {
        return discoveries.stream()
                .filter(discovery -> discovery.getScience() == science)
                .toList();
    }

    /** What this civilization knew by the end of the given year (negative for BCE). */
    public List<Discovery> knownBy(int year) {
        return discoveries.stream()
                .filter(discovery -> discovery.getYear() <= year)
                .toList();
    }

    /** Continuous Kardashev rating from Sagan's formula K = (log10 P − 6) / 10, with P in watts. */
    public double kardashevRating() {
        return (Math.log10(powerWatts) - Math.log10(SAGAN_REFERENCE_POWER_WATTS)) / 10;
    }

    public KardashevType kardashevType() {
        return KardashevType.of(powerWatts);
    }

    /** Power harnessed per person, in watts. */
    public double powerPerCapitaWatts() {
        return powerWatts / population;
    }

    /**
     * Years of compound growth at the given annual rate (0.02 for 2 % a year) for this civilization's power to reach
     * the given Kardashev type; zero if it already has.
     */
    public double yearsToReach(KardashevType type, double annualGrowthRate) {
        if (annualGrowthRate <= 0) {
            throw new IllegalArgumentException("growth rate must be positive");
        }
        if (powerWatts >= type.getMinimumPowerWatts()) {
            return 0;
        }
        return Math.log(type.getMinimumPowerWatts() / powerWatts) / Math.log1p(annualGrowthRate);
    }
}
