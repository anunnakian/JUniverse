package com.enlil.universe.celestial;

import lombok.Getter;

/**
 * A moon orbiting a planet.
 */
@Getter
public class NaturalSatellite extends CelestialBody {

    private final Orbit orbit;

    public NaturalSatellite(String name, double massKg, double radiusMeters, Orbit orbit) {
        super(name, massKg, radiusMeters);
        this.orbit = orbit;
    }
}
