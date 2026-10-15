package com.enlil.universe.world.history;

import com.enlil.universe.civilization.polity.Polity;
import com.enlil.universe.civilization.polity.Succession;
import com.enlil.universe.civilization.polity.TerritorialOrganization;
import com.enlil.universe.world.economy.Currencies;

/**
 * The French State of Vichy (1940–1944), neither monarchy nor republic: on 10 July 1940 the National Assembly granted
 * Marshal Pétain full powers by 569 votes to 80, and he made himself Chief of the French State.
 */
public class FrenchState extends Polity {

    public FrenchState() {
        super("French State", "Vichy", 39_800_000L, 551_500, TerritorialOrganization.UNITARY, 1940, 1944,
                Currencies.FRENCH_FRANC);
    }

    @Override
    public String getHeadOfStateTitle() {
        return "Chief of the French State";
    }

    @Override
    public Succession getSuccession() {
        return Succession.APPOINTED;
    }
}
