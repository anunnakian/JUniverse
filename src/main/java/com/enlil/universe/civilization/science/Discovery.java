package com.enlil.universe.civilization.science;

import lombok.Getter;

/**
 * A discovery, or the founding work of a field, in a given science. Years are historical: negative for BCE, no
 * year 0, and approximate for antiquity.
 */
@Getter
public class Discovery {

    private final String name;
    private final Science science;
    private final String discoverer;
    private final int year;

    public Discovery(String name, Science science, String discoverer, int year) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("a discovery must have a name");
        }
        if (year == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        this.name = name;
        this.science = science;
        this.discoverer = discoverer;
        this.year = year;
    }

    @Override
    public String toString() {
        return name + " (" + discoverer + ", " + (year < 0 ? -year + " BCE" : year) + ")";
    }
}
