package com.enlil.universe.life.human;

/**
 * ABO blood group, defined by the A and B antigens carried by the red cells.
 */
public enum AboGroup {
    A(true, false),
    B(false, true),
    AB(true, true),
    O(false, false);

    private final boolean antigenA;
    private final boolean antigenB;

    AboGroup(boolean antigenA, boolean antigenB) {
        this.antigenA = antigenA;
        this.antigenB = antigenB;
    }

    /** Whether red cells of this group can go to the recipient group: it must carry all of this group's antigens. */
    public boolean canDonateTo(AboGroup recipient) {
        return (!antigenA || recipient.antigenA) && (!antigenB || recipient.antigenB);
    }
}
