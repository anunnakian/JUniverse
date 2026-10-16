package com.enlil.universe.geoscience;

import lombok.Getter;

/**
 * A mass extinction: a geologically brief loss of a large share of the species alive.
 */
@Getter
public class MassExtinction {

    private final String name;
    private final double millionYearsAgo;
    /** Share of the species that disappeared, strictly between 0 and 1. */
    private final double speciesLostFraction;

    public MassExtinction(String name, double millionYearsAgo, double speciesLostFraction) {
        if (millionYearsAgo < 0) {
            throw new IllegalArgumentException("an extinction happened in the past");
        }
        if (speciesLostFraction <= 0 || speciesLostFraction >= 1) {
            throw new IllegalArgumentException("the share of species lost must be strictly between 0 and 1");
        }
        this.name = name;
        this.millionYearsAgo = millionYearsAgo;
        this.speciesLostFraction = speciesLostFraction;
    }

    @Override
    public String toString() {
        return name;
    }
}
