package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Empire;
import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * The Roman Empire, from Augustus in 27 BCE to the fall of the West in 476, at its peak under Trajan in 117: about
 * 60 million people on 5 million km² (Taagepera). Its eastern half lived on until 1453.
 */
public class RomanEmpire extends Empire {

    public RomanEmpire() {
        super("Roman Empire", "Rome", 60_000_000L, 5_000_000, TerritorialOrganization.UNITARY, -27, 476,
                Currencies.DENARIUS, MonarchyType.ABSOLUTE);
    }
}
