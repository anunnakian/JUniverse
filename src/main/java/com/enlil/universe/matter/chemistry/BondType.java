package com.enlil.universe.matter.chemistry;

/**
 * Character of a bond, from the electronegativity difference between its two atoms.
 */
public enum BondType {
    /** Δχ below 0.4: electrons shared evenly. */
    NONPOLAR_COVALENT,
    /** Δχ from 0.4 to 1.7: electrons pulled towards one atom. */
    POLAR_COVALENT,
    /** Δχ of 1.7 or more: an electron effectively handed over. */
    IONIC;

    public static BondType fromElectronegativityDifference(double difference) {
        if (difference < 0.4) {
            return NONPOLAR_COVALENT;
        }
        return difference < 1.7 ? POLAR_COVALENT : IONIC;
    }
}
