package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.Sultanate;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * The Sultanate of Oman, ruled by the Al Said dynasty since 1744: 5.3 million people (2024) on 309,500 km².
 * <p>
 * Its law still punishes same-sex relations.
 */
public class Oman extends Sultanate {

    public Oman() {
        super("Oman", "Muscat", 5_300_000L, 309_500, TerritorialOrganization.UNITARY, 1744,
                Currencies.OMANI_RIAL, MonarchyType.ABSOLUTE);
    }

    @Override
    public boolean criminalizesHomosexuality() {
        return true;
    }
}
