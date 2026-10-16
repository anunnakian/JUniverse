package com.enlil.universe.celestial;

import java.time.Instant;

public final class PhysicalConstants {

    /** Gravitational constant, in m³·kg⁻¹·s⁻². */
    public static final double G = 6.674_30e-11;
    /** Speed of light in vacuum, in m/s (exact). */
    public static final double SPEED_OF_LIGHT = 299_792_458;
    /** Stefan–Boltzmann constant, in W·m⁻²·K⁻⁴. */
    public static final double STEFAN_BOLTZMANN = 5.670_374_419e-8;
    /** Reduced Planck constant ħ, in J·s. */
    public static final double REDUCED_PLANCK = 1.054_571_817e-34;
    /** Boltzmann constant, in J/K (exact). */
    public static final double BOLTZMANN = 1.380_649e-23;
    /** Astronomical unit, in meters. */
    public static final double AU = 1.495_978_707e11;
    /** Parsec, the distance at which 1 AU spans one arcsecond, in meters. */
    public static final double PARSEC = 3.085_677_581_49e16;
    /** Megaparsec, in meters. */
    public static final double MEGAPARSEC = 1e6 * PARSEC;
    /** Mass of the Sun, in kilograms. */
    public static final double SOLAR_MASS = 1.988_47e30;
    /** Nominal solar luminosity (IAU 2015 B3), in watts. */
    public static final double SOLAR_LUMINOSITY = 3.828e26;
    /** Nominal solar radius (IAU 2015 B3), in meters. */
    public static final double SOLAR_RADIUS = 6.957e8;
    /** Absolute bolometric magnitude of the Sun (IAU 2015 B2). */
    public static final double SOLAR_BOLOMETRIC_MAGNITUDE = 4.74;
    /** One day, in seconds. */
    public static final double DAY = 86_400;
    /** One Julian year of 365.25 days, in seconds. */
    public static final double JULIAN_YEAR = 365.25 * DAY;
    /** The J2000 epoch, 1 January 2000 at noon (Terrestrial Time, approximated as UTC). */
    public static final Instant J2000 = Instant.parse("2000-01-01T12:00:00Z");

    private PhysicalConstants() {
    }
}
