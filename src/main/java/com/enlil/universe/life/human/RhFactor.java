package com.enlil.universe.life.human;

/**
 * Rhesus factor: whether the red cells carry the RhD antigen.
 */
public enum RhFactor {
    POSITIVE,
    NEGATIVE;

    /** Whether red cells with this factor can go to the recipient: Rh-negative recipients only take Rh-negative blood. */
    public boolean canDonateTo(RhFactor recipient) {
        return this == NEGATIVE || recipient == POSITIVE;
    }
}
