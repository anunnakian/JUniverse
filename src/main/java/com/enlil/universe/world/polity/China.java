package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.polity.SystemOfGovernment;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * The People's Republic of China, proclaimed in 1949: 1.408 billion people (2024) on 9,596,961 km², led by the
 * Communist Party.
 */
public class China extends Republic {

    public China() {
        super("China", "Beijing", 1_408_000_000L, 9_596_961, TerritorialOrganization.UNITARY, 1949,
                Currencies.RENMINBI, SystemOfGovernment.ONE_PARTY, 5);
    }
}
