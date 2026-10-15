package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Monarchy;
import com.enlil.universe.civilization.polity.MonarchyType;
import com.enlil.universe.civilization.polity.Succession;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * Malaysia, a federation since 1963: 34.1 million people (2024) on 330,803 km². Its king is elected every five years
 * by the Conference of Rulers, among the nine hereditary sultans of its states: neither kingdom nor republic, but
 * an elective monarchy.
 * <p>
 * Its law still punishes same-sex relations.
 */
public class Malaysia extends Monarchy {

    public Malaysia() {
        super("Malaysia", "Kuala Lumpur", 34_100_000L, 330_803, TerritorialOrganization.FEDERAL, 1963,
                Currencies.RINGGIT, MonarchyType.CONSTITUTIONAL, Succession.ELECTED);
    }

    @Override
    public String getHeadOfStateTitle() {
        return "Yang di-Pertuan Agong";
    }

    @Override
    public boolean criminalizesHomosexuality() {
        return true;
    }
}
