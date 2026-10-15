package com.enlil.universe.celestial;

import lombok.Getter;

/**
 * A celestial object with a physical body: a star, a planet or a moon.
 */
@Getter
public abstract class CelestialBody implements CelestialObject {

    private final String name;
    private final double massKg;
    private final double radiusMeters;

    protected CelestialBody(String name, double massKg, double radiusMeters) {
        if (massKg <= 0 || radiusMeters <= 0) {
            throw new IllegalArgumentException("mass and radius must be positive");
        }
        this.name = name;
        this.massKg = massKg;
        this.radiusMeters = radiusMeters;
    }

    /** Surface gravity, in m/s². */
    public double surfaceGravity() {
        return PhysicalConstants.G * massKg / (radiusMeters * radiusMeters);
    }
}
