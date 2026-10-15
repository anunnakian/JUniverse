package com.enlil.universe.matter;

/**
 * The four classical elements of Empedocles, with the qualities Aristotle gave them, mapped onto the state of
 * matter each one stands for today. Fire as plasma is a loose reading: an ordinary flame is a hot gas, only weakly
 * ionized.
 */
public enum ClassicalElement {
    EARTH(StateOfMatter.SOLID, false, true),
    WATER(StateOfMatter.LIQUID, false, false),
    AIR(StateOfMatter.GAS, true, false),
    FIRE(StateOfMatter.PLASMA, true, true);

    private final StateOfMatter stateOfMatter;
    private final boolean hot;
    private final boolean dry;

    ClassicalElement(StateOfMatter stateOfMatter, boolean hot, boolean dry) {
        this.stateOfMatter = stateOfMatter;
        this.hot = hot;
        this.dry = dry;
    }

    public static ClassicalElement fromStateOfMatter(StateOfMatter stateOfMatter) {
        for (ClassicalElement element : values()) {
            if (element.stateOfMatter == stateOfMatter) {
                return element;
            }
        }
        throw new IllegalArgumentException("no classical element for " + stateOfMatter);
    }

    public StateOfMatter getStateOfMatter() {
        return stateOfMatter;
    }

    /** Hot, as opposed to cold. */
    public boolean isHot() {
        return hot;
    }

    /** Dry, as opposed to wet. */
    public boolean isDry() {
        return dry;
    }

    /** The element sharing none of this one's qualities: fire and water, air and earth. */
    public ClassicalElement getOpposite() {
        for (ClassicalElement element : values()) {
            if (element.hot != hot && element.dry != dry) {
                return element;
            }
        }
        throw new IllegalStateException("no opposite for " + this);
    }
}
