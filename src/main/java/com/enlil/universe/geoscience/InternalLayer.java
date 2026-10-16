package com.enlil.universe.geoscience;

import com.enlil.universe.matter.StateOfMatter;
import lombok.Getter;

/**
 * A spherical shell of a planet's interior: the inner core, the mantle, the crust...
 */
@Getter
public class InternalLayer {

    private final String name;
    /** Distance from the planet's center to the top of this layer, in meters. */
    private final double outerRadiusMeters;
    /** Mean density, in kg/m³. */
    private final double densityKgPerCubicMeter;
    private final StateOfMatter state;

    public InternalLayer(String name, double outerRadiusMeters, double densityKgPerCubicMeter, StateOfMatter state) {
        if (outerRadiusMeters <= 0 || densityKgPerCubicMeter <= 0) {
            throw new IllegalArgumentException("radius and density must be positive");
        }
        if (state != StateOfMatter.SOLID && state != StateOfMatter.LIQUID) {
            throw new IllegalArgumentException("a planet's layer is solid or liquid, not " + state);
        }
        this.name = name;
        this.outerRadiusMeters = outerRadiusMeters;
        this.densityKgPerCubicMeter = densityKgPerCubicMeter;
        this.state = state;
    }

    @Override
    public String toString() {
        return name;
    }
}
