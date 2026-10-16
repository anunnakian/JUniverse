package com.enlil.universe.celestial;

import lombok.Getter;

/**
 * The bare core of a Sun-like star, held up by electron degeneracy pressure: a Sun's mass in an Earth's size.
 */
@Getter
public class WhiteDwarf extends StellarRemnant {

    /** Above this mass, electron degeneracy can no longer hold a white dwarf up, in solar masses. */
    public static final double CHANDRASEKHAR_LIMIT_SOLAR_MASSES = 1.44;

    private final double surfaceTemperatureKelvin;

    public WhiteDwarf(String name, double massKg, double radiusMeters, double surfaceTemperatureKelvin) {
        super(name, massKg, radiusMeters);
        if (massSolar() > CHANDRASEKHAR_LIMIT_SOLAR_MASSES) {
            throw new IllegalArgumentException("a white dwarf cannot exceed the Chandrasekhar limit of "
                    + CHANDRASEKHAR_LIMIT_SOLAR_MASSES + " solar masses");
        }
        if (surfaceTemperatureKelvin <= 0) {
            throw new IllegalArgumentException("temperature must be positive");
        }
        this.surfaceTemperatureKelvin = surfaceTemperatureKelvin;
    }

    /** Power radiated, 4πR²σT⁴, in watts. */
    public double luminosityWatts() {
        double radius = getRadiusMeters();
        return 4 * Math.PI * radius * radius * PhysicalConstants.STEFAN_BOLTZMANN
                * Math.pow(surfaceTemperatureKelvin, 4);
    }
}
