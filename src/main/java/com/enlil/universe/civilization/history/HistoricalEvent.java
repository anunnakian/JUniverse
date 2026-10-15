package com.enlil.universe.civilization.history;

import lombok.Getter;

/** Something that happened in a given year: a battle, a coronation, a law. Negative years are BCE; there is no 0. */
@Getter
public class HistoricalEvent {

    private final int year;
    private final String description;

    public HistoricalEvent(int year, String description) {
        if (year == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("an event must say what happened");
        }
        this.year = year;
        this.description = description;
    }

    @Override
    public String toString() {
        return (year < 0 ? -year + " BCE" : String.valueOf(year)) + ": " + description;
    }
}
