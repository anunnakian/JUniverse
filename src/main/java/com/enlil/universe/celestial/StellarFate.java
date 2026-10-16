package com.enlil.universe.celestial;

/**
 * What a star becomes when it dies, depending on its initial mass.
 */
public enum StellarFate {
    /** Below 8 solar masses: the core is left as a white dwarf after the outer layers drift away. */
    WHITE_DWARF(StellarStage.WHITE_DWARF),
    /** From 8 to 20 solar masses: a supernova leaves a neutron star. */
    NEUTRON_STAR(StellarStage.NEUTRON_STAR),
    /** From 20 solar masses: the core collapses into a black hole. */
    BLACK_HOLE(StellarStage.BLACK_HOLE);

    private final StellarStage remnant;

    StellarFate(StellarStage remnant) {
        this.remnant = remnant;
    }

    /** The fate of a star of a given initial mass, in solar masses. */
    public static StellarFate fromInitialMass(double solarMasses) {
        if (solarMasses < 8) {
            return WHITE_DWARF;
        }
        return solarMasses < 20 ? NEUTRON_STAR : BLACK_HOLE;
    }

    /** The stage the star ends in. */
    public StellarStage getRemnant() {
        return remnant;
    }
}
