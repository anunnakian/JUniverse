package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.polity.SystemOfGovernment;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

import java.util.OptionalInt;

/**
 * The Federal Republic of Germany, founded in 1949 and reunified in 1990: 83.6 million people (2024) on
 * 357,592 km². Its Federal President is elected by the Federal Convention.
 * <p>
 * Same-sex couples have married since 2017.
 */
public class Germany extends Republic {

    public Germany() {
        super("Germany", "Berlin", 83_600_000L, 357_592, TerritorialOrganization.FEDERAL, 1949,
                Currencies.EURO, SystemOfGovernment.PARLIAMENTARY, "Federal President", 5);
    }

    @Override
    public OptionalInt getSameSexMarriageYear() {
        return OptionalInt.of(2017);
    }
}
