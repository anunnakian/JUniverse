package com.enlil.universe.life.human;

import lombok.Getter;

/**
 * Fitzpatrick skin phototypes, from the palest skin that always burns (I) to the darkest that never burns (VI).
 * Each comes with a typical minimal erythema dose (MED): the erythemally weighted UV dose that reddens the skin.
 * Published MED values vary between studies; these are the commonly cited ones.
 */
@Getter
public enum SkinType {
    I(200),
    II(250),
    III(300),
    IV(450),
    V(600),
    VI(1_000);

    /** Minimal erythema dose, in J/m² of erythemally weighted UV. */
    private final double minimalErythemaDoseJoulesPerSquareMeter;

    SkinType(double minimalErythemaDoseJoulesPerSquareMeter) {
        this.minimalErythemaDoseJoulesPerSquareMeter = minimalErythemaDoseJoulesPerSquareMeter;
    }
}
