package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.polity.SystemOfGovernment;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

import java.util.OptionalInt;

/**
 * The United States of America, independent since 1776: 340.1 million people (2024) on 9,833,520 km².
 * <p>
 * Same-sex couples have married nationwide since Obergefell v. Hodges, in 2015.
 */
public class UnitedStates extends Republic {

    public UnitedStates() {
        super("United States", "Washington, D.C.", 340_100_000L, 9_833_520, TerritorialOrganization.FEDERAL, 1776,
                Currencies.US_DOLLAR, SystemOfGovernment.PRESIDENTIAL, 4);
    }

    @Override
    public OptionalInt getSameSexMarriageYear() {
        return OptionalInt.of(2015);
    }
}
