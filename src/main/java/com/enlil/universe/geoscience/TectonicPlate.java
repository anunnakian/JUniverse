package com.enlil.universe.geoscience;

import lombok.Getter;

/**
 * A slab of lithosphere drifting over the mantle.
 */
@Getter
public class TectonicPlate {

    private final String name;
    private final double areaSquareKm;
    private final PlateType type;
    /** Typical speed over the mantle, in mm/year. */
    private final double speedMillimetersPerYear;

    public TectonicPlate(String name, double areaSquareKm, PlateType type, double speedMillimetersPerYear) {
        if (areaSquareKm <= 0) {
            throw new IllegalArgumentException("area must be positive");
        }
        if (speedMillimetersPerYear < 0) {
            throw new IllegalArgumentException("speed must not be negative");
        }
        this.name = name;
        this.areaSquareKm = areaSquareKm;
        this.type = type;
        this.speedMillimetersPerYear = speedMillimetersPerYear;
    }

    /**
     * Years this plate takes to drift a given distance.
     *
     * @param distanceKm distance, in km
     */
    public double yearsToMove(double distanceKm) {
        if (speedMillimetersPerYear == 0) {
            throw new IllegalStateException(name + " plate does not move");
        }
        return distanceKm * 1e6 / speedMillimetersPerYear;
    }

    @Override
    public String toString() {
        return name;
    }
}
