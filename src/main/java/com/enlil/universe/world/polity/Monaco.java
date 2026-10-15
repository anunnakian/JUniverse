package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.Principality;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * The Principality of Monaco, ruled by the Grimaldi family since 1297: 38,400 people (2023 census) on 2.08 km², the
 * densest country in the world.
 */
public class Monaco extends Principality {

    public Monaco() {
        super("Monaco", "Monaco", 38_400L, 2.08, TerritorialOrganization.UNITARY, 1297,
                Currencies.EURO, MonarchyType.CONSTITUTIONAL);
    }
}
