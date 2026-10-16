package com.enlil.universe.celestial;

import lombok.Getter;

/**
 * The collapsed core of a massive star, held up by neutron degeneracy: more than a Sun's mass in the size of a city.
 * Spinning ones sweep beams across the sky as pulsars.
 */
@Getter
public class NeutronStar extends StellarRemnant {

    /** Above this mass (Tolman–Oppenheimer–Volkoff limit, as currently estimated), it collapses into a black hole. */
    public static final double TOV_LIMIT_SOLAR_MASSES = 2.3;

    private final double rotationPeriodSeconds;

    public NeutronStar(String name, double massKg, double radiusMeters, double rotationPeriodSeconds) {
        super(name, massKg, radiusMeters);
        if (massSolar() > TOV_LIMIT_SOLAR_MASSES) {
            throw new IllegalArgumentException("a neutron star cannot exceed the TOV limit of "
                    + TOV_LIMIT_SOLAR_MASSES + " solar masses");
        }
        if (rotationPeriodSeconds <= 0) {
            throw new IllegalArgumentException("rotation period must be positive");
        }
        this.rotationPeriodSeconds = rotationPeriodSeconds;
    }

    /** Escape velocity at the surface, √(2GM/R), as a fraction of the speed of light. */
    public double surfaceEscapeVelocityFractionOfLight() {
        return Math.sqrt(2 * PhysicalConstants.G * getMassKg() / getRadiusMeters()) / PhysicalConstants.SPEED_OF_LIGHT;
    }

    /** Whether it spins faster than once every 10 ms. */
    public boolean isMillisecondPulsar() {
        return rotationPeriodSeconds < 0.010;
    }
}
