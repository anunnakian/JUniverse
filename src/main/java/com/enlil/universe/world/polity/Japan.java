package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Empire;
import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * Japan, the last country with a reigning emperor, as set by its 1947 constitution: 123.8 million people (2024) on
 * 377,975 km². The imperial house traces itself back to the legendary emperor Jimmu, in 660 BCE.
 */
public class Japan extends Empire {

    public Japan() {
        super("Japan", "Tokyo", 123_800_000L, 377_975, TerritorialOrganization.UNITARY, 1947,
                Currencies.YEN, MonarchyType.CONSTITUTIONAL);
    }
}
