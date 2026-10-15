package com.enlil.universe.civilization.polity;

import com.enlil.universe.civilization.economy.Currency;
import lombok.Getter;

/**
 * A polity headed by a monarch who reigns for life. Most monarchies are hereditary; a few elect their monarch, like
 * Malaysia among its nine hereditary rulers.
 */
@Getter
public abstract class Monarchy extends Polity {

    private final MonarchyType monarchyType;
    private final Succession succession;

    protected Monarchy(String name, String capital, long population, double areaSquareKm,
                       TerritorialOrganization territorialOrganization, int foundedYear, Currency currency,
                       MonarchyType monarchyType, Succession succession) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, currency);
        this.monarchyType = monarchyType;
        this.succession = succession;
    }

    protected Monarchy(String name, String capital, long population, double areaSquareKm,
                       TerritorialOrganization territorialOrganization, int foundedYear, int dissolvedYear,
                       Currency currency, MonarchyType monarchyType, Succession succession) {
        super(name, capital, population, areaSquareKm, territorialOrganization, foundedYear, dissolvedYear, currency);
        this.monarchyType = monarchyType;
        this.succession = succession;
    }

    public boolean isAbsolute() {
        return monarchyType == MonarchyType.ABSOLUTE;
    }
}
