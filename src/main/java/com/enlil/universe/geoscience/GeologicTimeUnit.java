package com.enlil.universe.geoscience;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;

/**
 * A division of the geological time scale (an eon, an era or a period), dated in millions of years ago.
 */
@Getter
public class GeologicTimeUnit {

    private final String name;
    private final GeologicRank rank;
    private final double startMillionYearsAgo;
    private final double endMillionYearsAgo;
    /** Null for a unit of the highest rank. */
    @Getter(AccessLevel.NONE)
    private final GeologicTimeUnit parent;

    public GeologicTimeUnit(String name, GeologicRank rank, double startMillionYearsAgo, double endMillionYearsAgo) {
        this(name, rank, startMillionYearsAgo, endMillionYearsAgo, null);
    }

    /**
     * @param parent the unit of the next rank up whose span contains this one
     */
    public GeologicTimeUnit(String name, GeologicRank rank, double startMillionYearsAgo, double endMillionYearsAgo,
                            GeologicTimeUnit parent) {
        if (endMillionYearsAgo < 0 || startMillionYearsAgo <= endMillionYearsAgo) {
            throw new IllegalArgumentException("a unit starts before it ends, and ends no later than today");
        }
        if (parent != null) {
            if (parent.rank.ordinal() != rank.ordinal() - 1) {
                throw new IllegalArgumentException("a " + rank + " belongs to a unit of the next rank up");
            }
            if (startMillionYearsAgo > parent.startMillionYearsAgo || endMillionYearsAgo < parent.endMillionYearsAgo) {
                throw new IllegalArgumentException(name + " must fall within " + parent.name);
            }
        }
        this.name = name;
        this.rank = rank;
        this.startMillionYearsAgo = startMillionYearsAgo;
        this.endMillionYearsAgo = endMillionYearsAgo;
        this.parent = parent;
    }

    /** The unit of the next rank up that contains this one; empty for an eon. */
    public Optional<GeologicTimeUnit> getParent() {
        return Optional.ofNullable(parent);
    }

    public double durationMillionYears() {
        return startMillionYearsAgo - endMillionYearsAgo;
    }

    /**
     * Whether a moment falls within this unit: its start is included, its end excluded, except that the present
     * belongs to the last unit.
     */
    public boolean contains(double millionYearsAgo) {
        return millionYearsAgo <= startMillionYearsAgo
                && (millionYearsAgo > endMillionYearsAgo || millionYearsAgo == 0 && endMillionYearsAgo == 0);
    }

    @Override
    public String toString() {
        return name;
    }
}
