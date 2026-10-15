package com.enlil.universe.society;

import lombok.Getter;

/**
 * A company people work in, known by its name and the year it was founded (historical: negative for BCE, no year 0).
 * It does not list its employees: their employments point to it.
 */
@Getter
public class Company {

    private final String name;
    private final int foundedYear;

    public Company(String name, int foundedYear) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("a company must have a name");
        }
        if (foundedYear == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        this.name = name;
        this.foundedYear = foundedYear;
    }

    @Override
    public String toString() {
        return name;
    }
}
