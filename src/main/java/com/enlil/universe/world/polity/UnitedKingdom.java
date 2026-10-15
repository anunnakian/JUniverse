package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Kingdom;
import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

import java.util.OptionalInt;

/**
 * The United Kingdom, formed in 1801 by the union of Great Britain and Ireland: 69.3 million people (2024) on
 * 242,495 km². Unitary, with powers devolved to Scotland, Wales and Northern Ireland.
 * <p>
 * Same-sex couples married in England, Wales and Scotland from 2014, and in Northern Ireland from 2020.
 */
public class UnitedKingdom extends Kingdom {

    public UnitedKingdom() {
        super("United Kingdom", "London", 69_300_000L, 242_495, TerritorialOrganization.UNITARY, 1801,
                Currencies.POUND_STERLING, MonarchyType.CONSTITUTIONAL);
    }

    @Override
    public OptionalInt getSameSexMarriageYear() {
        return OptionalInt.of(2020);
    }
}
