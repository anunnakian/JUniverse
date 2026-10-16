package com.enlil.universe.geoscience;

import lombok.Getter;

import java.util.List;

/**
 * A rock: an aggregate of minerals, formed by one of the processes of the rock cycle.
 */
@Getter
public class Rock {

    private final String name;
    private final RockType type;
    private final List<Mineral> minerals;

    public Rock(String name, RockType type, List<Mineral> minerals) {
        if (minerals.isEmpty()) {
            throw new IllegalArgumentException("a rock is made of at least one mineral");
        }
        this.name = name;
        this.type = type;
        this.minerals = List.copyOf(minerals);
    }

    @Override
    public String toString() {
        return name;
    }
}
