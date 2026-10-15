package com.enlil.universe.civilization;

/**
 * The broad technological eras a civilization goes through, in order. Declaration order is chronological, so eras
 * compare with {@link #compareTo}.
 */
public enum TechnologicalEra {
    STONE_AGE,
    BRONZE_AGE,
    IRON_AGE,
    INDUSTRIAL,
    INFORMATION,
    INTERPLANETARY,
    INTERSTELLAR;

    public boolean isAtLeast(TechnologicalEra other) {
        return compareTo(other) >= 0;
    }
}
