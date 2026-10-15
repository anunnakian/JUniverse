package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.polity.SystemOfGovernment;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

import java.util.OptionalInt;

/**
 * The French Republic, as founded by the constitution of the Fifth Republic in 1958: 68.4 million people (2024,
 * overseas regions included) on 643,801 km².
 * <p>
 * Same-sex couples have married since the "marriage for all" law of 2013.
 */
public class France extends Republic {

    public France() {
        super("France", "Paris", 68_400_000L, 643_801, TerritorialOrganization.UNITARY, 1958,
                Currencies.EURO, SystemOfGovernment.SEMI_PRESIDENTIAL, 5);
    }

    @Override
    public OptionalInt getSameSexMarriageYear() {
        return OptionalInt.of(2013);
    }
}
