package com.enlil.universe.matter.chemistry;

/**
 * Whether an aqueous solution is acidic, neutral or basic at 25 °C.
 */
public enum Acidity {
    /** pH below 6.99. */
    ACIDIC,
    NEUTRAL,
    /** pH above 7.01. */
    BASIC;

    public static Acidity fromPh(double ph) {
        if (ph < 6.99) {
            return ACIDIC;
        }
        return ph > 7.01 ? BASIC : NEUTRAL;
    }
}
