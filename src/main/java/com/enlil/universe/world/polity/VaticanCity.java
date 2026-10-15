package com.enlil.universe.world.polity;

import com.enlil.universe.civilization.polity.Succession;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.civilization.polity.Theocracy;
import com.enlil.universe.world.economy.Currencies;

/**
 * Vatican City, created by the Lateran Treaty in 1929: about 800 residents on 0.44 km², the smallest country in the
 * world, ruled by the pope, whom the cardinals elect in conclave.
 */
public class VaticanCity extends Theocracy {

    public VaticanCity() {
        super("Vatican City", "Vatican City", 800L, 0.44, TerritorialOrganization.UNITARY, 1929,
                Currencies.EURO, "Catholicism", "Pope", Succession.ELECTED);
    }
}
