package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;
import lombok.Getter;

/** A polity whose head of state is not a monarch but is elected for a fixed term. */
@Getter
public class Republic extends Polity {

    private final SystemOfGovernment systemOfGovernment;
    private final String headOfStateTitle;
    private final int headOfStateTermYears;

    /** A republic headed by a president. */
    public Republic(String name, String capital, long population, double areaSquareKm,
                    TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
                    SystemOfGovernment systemOfGovernment, int headOfStateTermYears) {
        this(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency,
                systemOfGovernment, "President", headOfStateTermYears);
    }

    public Republic(String name, String capital, long population, double areaSquareKm,
                    TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
                    SystemOfGovernment systemOfGovernment, String headOfStateTitle, int headOfStateTermYears) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency);
        if (headOfStateTermYears <= 0) {
            throw new IllegalArgumentException("term must be positive");
        }
        this.systemOfGovernment = systemOfGovernment;
        this.headOfStateTitle = headOfStateTitle;
        this.headOfStateTermYears = headOfStateTermYears;
    }

    /** A republic that was dissolved in the given year. */
    public Republic(String name, String capital, long population, double areaSquareKm,
                    TerritorialOrganization territorialOrganization, int foundedYear, int dissolvedYear,
                    Currency currency, SystemOfGovernment systemOfGovernment, String headOfStateTitle,
                    int headOfStateTermYears) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, dissolvedYear, currency);
        if (headOfStateTermYears <= 0) {
            throw new IllegalArgumentException("term must be positive");
        }
        this.systemOfGovernment = systemOfGovernment;
        this.headOfStateTitle = headOfStateTitle;
        this.headOfStateTermYears = headOfStateTermYears;
    }

    @Override
    public Succession getSuccession() {
        return Succession.ELECTED;
    }
}
