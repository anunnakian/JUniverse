package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;

/** A hereditary monarchy ruled by a king or a queen. */
public class Kingdom extends Monarchy {

    public Kingdom(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency, monarchyType,
                Succession.HEREDITARY);
    }

    public Kingdom(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, int dissolvedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, dissolvedYear, currency,
                monarchyType, Succession.HEREDITARY);
    }

    @Override
    public String getHeadOfStateTitle() {
        return "King";
    }
}
