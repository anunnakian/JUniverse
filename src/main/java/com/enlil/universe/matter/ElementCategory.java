package com.enlil.universe.matter;

/**
 * Chemical family of an element, as periodic tables color them.
 */
public enum ElementCategory {
    ALKALI_METAL,
    ALKALINE_EARTH_METAL,
    TRANSITION_METAL,
    POST_TRANSITION_METAL,
    METALLOID,
    REACTIVE_NONMETAL,
    HALOGEN,
    NOBLE_GAS,
    LANTHANIDE,
    ACTINIDE,
    /** Superheavy elements, made a few atoms at a time, whose chemistry has never been measured. */
    UNKNOWN;

    public boolean isMetal() {
        return switch (this) {
            case ALKALI_METAL, ALKALINE_EARTH_METAL, TRANSITION_METAL, POST_TRANSITION_METAL, LANTHANIDE, ACTINIDE ->
                    true;
            default -> false;
        };
    }
}
