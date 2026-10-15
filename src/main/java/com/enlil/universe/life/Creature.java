package com.enlil.universe.life;

import lombok.Getter;

/**
 * A living thing, identified by its species and kingdom.
 */
@Getter
public class Creature {

    private final String species;
    private final Kingdom kingdom;
    private final double massKg;

    public Creature(String species, Kingdom kingdom, double massKg) {
        if (massKg <= 0) {
            throw new IllegalArgumentException("mass must be positive");
        }
        this.species = species;
        this.kingdom = kingdom;
        this.massKg = massKg;
    }

    /** Weight under the given surface gravity (in m/s²), in newtons. */
    public double weightNewtons(double surfaceGravity) {
        return massKg * surfaceGravity;
    }
}
