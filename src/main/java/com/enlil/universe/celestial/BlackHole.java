package com.enlil.universe.celestial;

import static com.enlil.universe.celestial.PhysicalConstants.*;

/**
 * A non-rotating, uncharged (Schwarzschild) black hole. Its radius is its event horizon, the Schwarzschild radius
 * 2GM/c².
 */
public class BlackHole extends StellarRemnant {

    /** From this mass, in solar masses, a black hole is supermassive. */
    public static final double SUPERMASSIVE_THRESHOLD_SOLAR_MASSES = 1e5;

    public BlackHole(String name, double massKg) {
        super(name, massKg, schwarzschildRadius(massKg));
    }

    /** Radius of the event horizon, 2GM/c², in meters. */
    public double schwarzschildRadiusMeters() {
        return getRadiusMeters();
    }

    /** Radius where light itself can orbit, 1.5 r_s, in meters. */
    public double photonSphereRadiusMeters() {
        return 1.5 * schwarzschildRadiusMeters();
    }

    /** Innermost stable circular orbit, 3 r_s, in meters: the inner edge of accretion disks. */
    public double innermostStableOrbitRadiusMeters() {
        return 3 * schwarzschildRadiusMeters();
    }

    /**
     * How fast a clock held still at a given distance from the center ticks compared with a distant one,
     * √(1 − r_s/r).
     *
     * @param distanceMeters distance from the center, in meters, outside the horizon
     */
    public double timeDilationFactor(double distanceMeters) {
        if (distanceMeters <= schwarzschildRadiusMeters()) {
            throw new IllegalArgumentException("no static clock exists at or inside the event horizon");
        }
        return Math.sqrt(1 - schwarzschildRadiusMeters() / distanceMeters);
    }

    /**
     * Difference in gravitational acceleration between the two ends of a body of a given length pointing at the
     * hole, 2GM·L/r³, in m/s²: what stretches an infalling astronaut head to toe.
     */
    public double tidalAccelerationDifference(double distanceMeters, double lengthMeters) {
        return 2 * G * getMassKg() * lengthMeters / (distanceMeters * distanceMeters * distanceMeters);
    }

    /** Temperature of its Hawking radiation, ħc³/(8πGMk), in kelvins. */
    public double hawkingTemperatureKelvin() {
        return REDUCED_PLANCK * Math.pow(SPEED_OF_LIGHT, 3) / (8 * Math.PI * G * getMassKg() * BOLTZMANN);
    }

    /** Time to evaporate entirely by Hawking radiation, 5120πG²M³/(ħc⁴), in years. */
    public double evaporationTimeYears() {
        double mass = getMassKg();
        double seconds = 5120 * Math.PI * G * G * mass * mass * mass / (REDUCED_PLANCK * Math.pow(SPEED_OF_LIGHT, 4));
        return seconds / JULIAN_YEAR;
    }

    /** Whether it weighs at least 10⁵ solar masses, like those at the hearts of galaxies. */
    public boolean isSupermassive() {
        return massSolar() >= SUPERMASSIVE_THRESHOLD_SOLAR_MASSES;
    }

    /** A black hole has no surface to stand on, so it has no surface gravity. */
    @Override
    public double surfaceGravity() {
        throw new UnsupportedOperationException("a black hole has no surface");
    }

    private static double schwarzschildRadius(double massKg) {
        return 2 * G * massKg / (SPEED_OF_LIGHT * SPEED_OF_LIGHT);
    }
}
