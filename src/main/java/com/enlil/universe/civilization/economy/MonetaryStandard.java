package com.enlil.universe.civilization.economy;

/** What gives a currency its value. */
public enum MonetaryStandard {
    /** The money is itself valuable: coins of gold, silver or bronze worth their metal (the Roman denarius). */
    COMMODITY,
    /** Notes redeemable for a commodity held in reserve, as under the gold standard. */
    REPRESENTATIVE,
    /** Money by decree, backed by trust in its issuer (every major currency since the dollar left gold in 1971). */
    FIAT
}
