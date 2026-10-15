package com.enlil.universe.life.human;

/** The sex chromosomes of a cell, with the total chromosome count they make: 46 usually, 47 or 45 otherwise. */
public enum Karyotype {
    XX(46),
    XY(46),
    /** One extra X, as in Klinefelter syndrome. */
    XXY(47),
    /** A single X, as in Turner syndrome. */
    X(45);

    private final int chromosomes;

    Karyotype(int chromosomes) {
        this.chromosomes = chromosomes;
    }

    public int getChromosomes() {
        return chromosomes;
    }

    /** The cytogenetic notation, like "46,XX" or "45,X". */
    @Override
    public String toString() {
        return chromosomes + "," + name();
    }
}
