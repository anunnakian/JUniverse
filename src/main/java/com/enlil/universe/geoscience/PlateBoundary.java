package com.enlil.universe.geoscience;

import lombok.Getter;

/**
 * Where two tectonic plates meet: a ridge, a trench, a mountain range or a fault.
 */
@Getter
public class PlateBoundary {

    private final String name;
    private final TectonicPlate firstPlate;
    private final TectonicPlate secondPlate;
    private final BoundaryType type;
    /** Relative speed of the two plates across the boundary, in mm/year. */
    private final double spreadingRateMillimetersPerYear;

    public PlateBoundary(String name, TectonicPlate firstPlate, TectonicPlate secondPlate, BoundaryType type,
                         double spreadingRateMillimetersPerYear) {
        if (firstPlate == secondPlate) {
            throw new IllegalArgumentException("a boundary separates two different plates");
        }
        if (spreadingRateMillimetersPerYear < 0) {
            throw new IllegalArgumentException("rate must not be negative");
        }
        this.name = name;
        this.firstPlate = firstPlate;
        this.secondPlate = secondPlate;
        this.type = type;
        this.spreadingRateMillimetersPerYear = spreadingRateMillimetersPerYear;
    }

    /**
     * Years a divergent boundary takes to open by a given width.
     *
     * @param distanceKm width, in km
     * @throws IllegalStateException if the boundary is not divergent, or doesn't move
     */
    public double yearsToWiden(double distanceKm) {
        if (type != BoundaryType.DIVERGENT) {
            throw new IllegalStateException("only a divergent boundary widens, not a " + type + " one");
        }
        if (spreadingRateMillimetersPerYear == 0) {
            throw new IllegalStateException(name + " does not move");
        }
        return distanceKm * 1e6 / spreadingRateMillimetersPerYear;
    }

    @Override
    public String toString() {
        return name;
    }
}
