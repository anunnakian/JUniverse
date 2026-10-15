package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;

/** A hereditary monarchy ruled by an emperor, traditionally over many peoples and conquered lands. */
public class Empire extends Monarchy {

    public Empire(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency, monarchyType,
                Succession.HEREDITARY);
    }

    public Empire(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, int dissolvedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, dissolvedYear, currency,
                monarchyType, Succession.HEREDITARY);
    }

    @Override
    public String getHeadOfStateTitle() {
        return "Emperor";
    }
}
