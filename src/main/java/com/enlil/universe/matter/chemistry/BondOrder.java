package com.enlil.universe.matter.chemistry;

/**
 * How many electron pairs a covalent bond shares.
 */
public enum BondOrder {
    SINGLE(1), DOUBLE(2), TRIPLE(3);

    private final int order;

    BondOrder(int order) {
        this.order = order;
    }

    /** Number of shared electron pairs. */
    public int getOrder() {
        return order;
    }
}
