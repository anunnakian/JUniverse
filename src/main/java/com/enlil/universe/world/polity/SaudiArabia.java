package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Kingdom;
import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * The Kingdom of Saudi Arabia, unified in 1932: 35.3 million people (2024) on 2,149,690 km², ruled by the House of
 * Saud without a written constitution beyond the Basic Law.
 * <p>
 * Its law still punishes same-sex relations.
 */
public class SaudiArabia extends Kingdom {

    public SaudiArabia() {
        super("Saudi Arabia", "Riyadh", 35_300_000L, 2_149_690, TerritorialOrganization.UNITARY, 1932,
                Currencies.SAUDI_RIYAL, MonarchyType.ABSOLUTE);
    }

    @Override
    public boolean criminalizesHomosexuality() {
        return true;
    }
}
