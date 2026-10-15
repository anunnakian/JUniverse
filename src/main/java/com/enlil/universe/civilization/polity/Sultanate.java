package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;

/** A hereditary monarchy ruled by a sultan, in the Islamic world (Oman, Brunei). */
public class Sultanate extends Monarchy {

    public Sultanate(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency, monarchyType,
                Succession.HEREDITARY);
    }

    public Sultanate(String name, String capital, long population, double areaSquareKm,
            TerritorialOrganization territorialOrganization, int foundedYear, int dissolvedYear, Currency currency,
            MonarchyType monarchyType) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, dissolvedYear, currency,
                monarchyType, Succession.HEREDITARY);
    }

    @Override
    public String getHeadOfStateTitle() {
        return "Sultan";
    }
}
