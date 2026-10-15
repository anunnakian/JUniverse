package com.enlil.universe.matter;

/**
 * The four fundamental states of matter.
 */
public enum StateOfMatter {
    /** Keeps its shape and its volume. */
    SOLID(true, true),
    /** Takes the shape of its container but keeps its volume. */
    LIQUID(false, true),
    /** Expands to fill its container. */
    GAS(false, false),
    /** A gas so hot that its atoms are ionized: stars, lightning, neon signs. */
    PLASMA(false, false);

    private final boolean definiteShape;
    private final boolean definiteVolume;

    StateOfMatter(boolean definiteShape, boolean definiteVolume) {
        this.definiteShape = definiteShape;
        this.definiteVolume = definiteVolume;
    }

    /**
     * State of a pure element at a given temperature and atmospheric pressure, from its melting and boiling points.
     * Ionization is not modeled, so this never returns {@link #PLASMA}.
     *
     * @param temperatureKelvin temperature, in kelvins
     */
    public static StateOfMatter of(ChemicalElement element, double temperatureKelvin) {
        if (temperatureKelvin < 0) {
            throw new IllegalArgumentException("temperature must not be below absolute zero");
        }
        // a NaN melting point (helium) never compares lower, so the element is never solid
        if (temperatureKelvin < element.getMeltingPointKelvin()) {
            return SOLID;
        }
        if (temperatureKelvin < element.getBoilingPointKelvin()) {
            return LIQUID;
        }
        return GAS;
    }

    public boolean hasDefiniteShape() {
        return definiteShape;
    }

    public boolean hasDefiniteVolume() {
        return definiteVolume;
    }
}
