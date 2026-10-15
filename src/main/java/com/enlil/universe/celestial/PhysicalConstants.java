package com.enlil.universe.celestial;

import java.time.Instant;

public final class PhysicalConstants {

    /** Gravitational constant, in m³·kg⁻¹·s⁻². */
    public static final double G = 6.674_30e-11;
    /** Astronomical unit, in meters. */
    public static final double AU = 1.495_978_707e11;
    /** Mass of the Sun, in kilograms. */
    public static final double SOLAR_MASS = 1.988_47e30;
    /** One day, in seconds. */
    public static final double DAY = 86_400;
    /** The J2000 epoch, 1 January 2000 at noon (Terrestrial Time, approximated as UTC). */
    public static final Instant J2000 = Instant.parse("2000-01-01T12:00:00Z");

    private PhysicalConstants() {
    }
}
