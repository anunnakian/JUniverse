package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.polity.SystemOfGovernment;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

import java.util.OptionalInt;

/**
 * The Swiss Confederation, a federal state since 1848 despite its name: 9 million people (2024) on 41,285 km². Its
 * seven-member Federal Council heads the state, and chairs itself through a president rotating every year.
 * <p>
 * Same-sex couples have married since 2022, after a referendum approved it by 64 %.
 */
public class Switzerland extends Republic {

    public Switzerland() {
        super("Switzerland", "Bern", 9_000_000L, 41_285, TerritorialOrganization.FEDERAL, 1848,
                Currencies.SWISS_FRANC, SystemOfGovernment.DIRECTORIAL, "President of the Confederation", 1);
    }

    @Override
    public OptionalInt getSameSexMarriageYear() {
        return OptionalInt.of(2022);
    }
}
