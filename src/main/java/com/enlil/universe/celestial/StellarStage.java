package com.enlil.universe.celestial;

/**
 * The stages of a star's life, from birth to death.
 */
public enum StellarStage {
    /** A collapsing cloud not yet fusing hydrogen; kept for completeness, as no pre-main-sequence model exists. */
    PROTOSTAR(false),
    /** Fusing hydrogen in its core, like the Sun today. */
    MAIN_SEQUENCE(false),
    /** A swollen star burning helium, the fate of stars below 8 solar masses. */
    RED_GIANT(false),
    /** The giant phase of massive stars. */
    RED_SUPERGIANT(false),
    WHITE_DWARF(true),
    NEUTRON_STAR(true),
    BLACK_HOLE(true);

    private final boolean remnant;

    StellarStage(boolean remnant) {
        this.remnant = remnant;
    }

    /** Whether this stage is what remains after the star dies. */
    public boolean isRemnant() {
        return remnant;
    }
}
