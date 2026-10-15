package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;
import lombok.Getter;

/** A polity ruled by a religious authority in the name of a religion, like the Holy See over Vatican City. */
@Getter
public class Theocracy extends Polity {

    private final String religion;
    private final String headOfStateTitle;
    private final Succession succession;

    public Theocracy(String name, String capital, long population, double areaSquareKm,
                     TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
                     String religion, String headOfStateTitle, Succession succession) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency);
        this.religion = religion;
        this.headOfStateTitle = headOfStateTitle;
        this.succession = succession;
    }
}
