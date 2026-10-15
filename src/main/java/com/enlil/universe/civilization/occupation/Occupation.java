package com.enlil.universe.civilization.occupation;

import com.enlil.universe.civilization.science.Science;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.EnumSet;
import java.util.OptionalInt;
import java.util.Set;

/**
 * A job people do within a civilization, classified in its ISCO-08 major group, relying on some sciences, and
 * practised over a period of history: some since antiquity, some, like the samurai, long gone. Years are historical:
 * negative for BCE, no year 0, and approximate for antiquity.
 */
@Getter
public abstract class Occupation {

    private final String name;
    private final OccupationGroup group;
    private final Set<Science> sciences;
    private final int sinceYear;
    /** Null while the occupation is still practised. */
    @Getter(AccessLevel.NONE)
    private final Integer untilYear;

    /** An occupation still practised today. */
    protected Occupation(String name, OccupationGroup group, Set<Science> sciences, int sinceYear) {
        this(name, group, sciences, sinceYear, (Integer) null);
    }

    /** An occupation that disappeared in the given year. */
    protected Occupation(String name, OccupationGroup group, Set<Science> sciences, int sinceYear, int untilYear) {
        this(name, group, sciences, sinceYear, Integer.valueOf(untilYear));
    }

    private Occupation(String name, OccupationGroup group, Set<Science> sciences, int sinceYear,
                       Integer untilYear) {
        if (sinceYear == 0 || untilYear != null && untilYear == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        if (untilYear != null && untilYear < sinceYear) {
            throw new IllegalArgumentException("an occupation cannot disappear before it appears");
        }
        this.name = name;
        this.group = group;
        this.sciences = sciences.isEmpty() ? Set.of() : Collections.unmodifiableSet(EnumSet.copyOf(sciences));
        this.sinceYear = sinceYear;
        this.untilYear = untilYear;
    }

    /** Whether the people doing this job carry weapons. */
    public abstract boolean isArmed();

    /** The year this occupation disappeared; empty if it is still practised. */
    public OptionalInt getUntilYear() {
        return untilYear == null ? OptionalInt.empty() : OptionalInt.of(untilYear);
    }

    public boolean isExtant() {
        return untilYear == null;
    }

    /** Whether this occupation was practised during the given year. */
    public boolean existedIn(int year) {
        return year >= sinceYear && (untilYear == null || year <= untilYear);
    }

    @Override
    public String toString() {
        return name;
    }
}
