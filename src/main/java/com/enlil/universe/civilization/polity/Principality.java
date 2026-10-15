package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;

/** A hereditary monarchy ruled by a prince, usually a small one (Monaco, Liechtenstein). */
public class Principality extends Monarchy {

    public Principality(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency, monarchyType,
                Succession.HEREDITARY);
    }

    public Principality(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, int dissolvedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, dissolvedYear, currency,
                monarchyType, Succession.HEREDITARY);
    }

    @Override
    public String getHeadOfStateTitle() {
        return "Prince";
    }
}
