package com.enlil.universe.geoscience;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;

/**
 * An earthquake, measured by its moment magnitude. Years are historical: negative for BCE, no year 0.
 */
@Getter
public class Earthquake {

    private final String name;
    private final int year;
    private final double momentMagnitude;
    /** Null when the earthquake isn't tied to a modeled plate boundary. */
    @Getter(AccessLevel.NONE)
    private final PlateBoundary boundary;

    public Earthquake(String name, int year, double momentMagnitude) {
        this(name, year, momentMagnitude, null);
    }

    public Earthquake(String name, int year, double momentMagnitude, PlateBoundary boundary) {
        if (year == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        if (momentMagnitude <= 0 || momentMagnitude > 10) {
            throw new IllegalArgumentException("moment magnitude must be in (0, 10]");
        }
        this.name = name;
        this.year = year;
        this.momentMagnitude = momentMagnitude;
        this.boundary = boundary;
    }

    /** The plate boundary that slipped; empty when not modeled. */
    public Optional<PlateBoundary> getBoundary() {
        return Optional.ofNullable(boundary);
    }

    /** Seismic moment M₀ = 10^(1.5·Mw + 9.1), in N·m (Hanks and Kanamori). */
    public double seismicMomentNewtonMeters() {
        return Math.pow(10, 1.5 * momentMagnitude + 9.1);
    }

    /** How many times more energy this earthquake released than another, 10^(1.5·ΔMw). */
    public double energyRatioTo(Earthquake other) {
        return Math.pow(10, 1.5 * (momentMagnitude - other.momentMagnitude));
    }

    @Override
    public String toString() {
        return name + " (Mw " + momentMagnitude + ")";
    }
}
