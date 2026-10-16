package com.enlil.universe.celestial;

/**
 * What is left of a dead star: a white dwarf, a neutron star or a black hole. Remnants have no planets in this
 * model.
 */
public abstract class StellarRemnant extends CelestialBody {

    protected StellarRemnant(String name, double massKg, double radiusMeters) {
        super(name, massKg, radiusMeters);
    }

    /** Mass in units of the Sun's mass. */
    public double massSolar() {
        return getMassKg() / PhysicalConstants.SOLAR_MASS;
    }

    /** Mean density, in kg/m³. */
    public double densityKgPerCubicMeter() {
        double radius = getRadiusMeters();
        return getMassKg() / (4.0 / 3 * Math.PI * radius * radius * radius);
    }
}
