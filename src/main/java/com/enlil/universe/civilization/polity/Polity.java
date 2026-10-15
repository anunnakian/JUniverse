package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;
import java.util.OptionalInt;

/**
 * A political entity ruling over a territory and its people: a kingdom, an empire, a republic... Subclasses say who
 * heads it and how that head is chosen. Each polity issues or adopts a currency. Years follow the historical
 * calendar: negative years are BCE, and there is no year 0, so 1 BCE is followed by 1 CE.
 */
@Getter
public abstract class Polity {

    private final String name;
    private final String capital;
    private final long population;
    private final double areaSquareKm;
    private final TerritorialOrganization territorialOrganization;
    private final int foundedYear;
    /** Null while the polity still exists. */
    @Getter(AccessLevel.NONE)
    private final Integer dissolvedYear;
    private final Currency currency;

    /** A polity that still exists. */
    protected Polity(String name, String capital, long population, double areaSquareKm,
                     TerritorialOrganization territorialOrganization, int foundedYear, Currency currency) {
        this(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, (Integer) null,
                currency);
    }

    /** A polity that was dissolved in the given year. */
    protected Polity(String name, String capital, long population, double areaSquareKm,
                     TerritorialOrganization territorialOrganization, int foundedYear, int dissolvedYear,
                     Currency currency) {
        this(name, capital, population, areaSquareKm, territorialOrganization, foundedYear,
                Integer.valueOf(dissolvedYear), currency);
    }

    private Polity(String name, String capital, long population, double areaSquareKm,
                   TerritorialOrganization territorialOrganization, int foundedYear, Integer dissolvedYear,
                   Currency currency) {
        if (population <= 0) {
            throw new IllegalArgumentException("population must be positive");
        }
        if (areaSquareKm <= 0) {
            throw new IllegalArgumentException("area must be positive");
        }
        if (foundedYear == 0 || dissolvedYear != null && dissolvedYear == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        if (dissolvedYear != null && dissolvedYear < foundedYear) {
            throw new IllegalArgumentException("a polity cannot be dissolved before it is founded");
        }
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.areaSquareKm = areaSquareKm;
        this.territorialOrganization = territorialOrganization;
        this.foundedYear = foundedYear;
        this.dissolvedYear = dissolvedYear;
        this.currency = Objects.requireNonNull(currency, "currency");
    }

    /** The title of the head of state: king, emperor, president... */
    public abstract String getHeadOfStateTitle();

    /** How the head of state comes to power. */
    public abstract Succession getSuccession();

    /** The year this polity was dissolved; empty if it still exists. */
    public OptionalInt getDissolvedYear() {
        return dissolvedYear == null ? OptionalInt.empty() : OptionalInt.of(dissolvedYear);
    }

    /** The year same-sex couples could first marry throughout this polity; empty where they still cannot. */
    public OptionalInt getSameSexMarriageYear() {
        return OptionalInt.empty();
    }

    /** Whether this polity's law punishes same-sex relations, as some 60 countries still did in 2024. */
    public boolean criminalizesHomosexuality() {
        return false;
    }

    /** People per square kilometre. */
    public double populationDensityPerSquareKm() {
        return population / areaSquareKm;
    }

    public boolean isExtant() {
        return dissolvedYear == null;
    }

    /** Whether this polity existed during the given year. */
    public boolean existedIn(int year) {
        return year >= foundedYear && (dissolvedYear == null || year <= dissolvedYear);
    }

    /** Years elapsed from this polity's founding to the given year. */
    public int ageYears(int year) {
        if (year < foundedYear) {
            throw new IllegalArgumentException(name + " was not founded yet in " + year);
        }
        return yearsBetween(foundedYear, year);
    }

    /** Years this polity lasted, from its founding to its dissolution; empty if it still exists. */
    public OptionalInt durationYears() {
        return isExtant() ? OptionalInt.empty() : OptionalInt.of(yearsBetween(foundedYear, dissolvedYear));
    }

    /** Years between two historical years, skipping the missing year 0. */
    static int yearsBetween(int fromYear, int toYear) {
        return toYear - fromYear - (fromYear < 0 && toYear > 0 ? 1 : 0);
    }
}
