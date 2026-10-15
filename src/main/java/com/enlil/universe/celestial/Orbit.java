package com.enlil.universe.celestial;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

/**
 * A Keplerian orbit around a central body, described by its orbital elements at the J2000 epoch,
 * relative to a reference plane (the ecliptic for planets). The elements are fixed: neither
 * precession nor perturbations by other bodies are modeled.
 */
@Getter
public class Orbit {

    private final double semiMajorAxisAu;
    private final double eccentricity;
    private final double inclinationDegrees;
    private final double longitudeOfAscendingNodeDegrees;
    private final double longitudeOfPerihelionDegrees;
    private final double meanLongitudeAtJ2000Degrees;

    /** An orbit lying in the reference plane. */
    public Orbit(double semiMajorAxisAu, double eccentricity,
                 double longitudeOfPerihelionDegrees, double meanLongitudeAtJ2000Degrees) {
        this(semiMajorAxisAu, eccentricity, 0, 0, longitudeOfPerihelionDegrees, meanLongitudeAtJ2000Degrees);
    }

    public Orbit(double semiMajorAxisAu, double eccentricity, double inclinationDegrees, double longitudeOfAscendingNodeDegrees,
                 double longitudeOfPerihelionDegrees, double meanLongitudeAtJ2000Degrees) {
        if (semiMajorAxisAu <= 0) {
            throw new IllegalArgumentException("semi-major axis must be positive");
        }
        if (eccentricity < 0 || eccentricity >= 1) {
            throw new IllegalArgumentException("eccentricity of a closed orbit must be in [0, 1)");
        }
        this.semiMajorAxisAu = semiMajorAxisAu;
        this.eccentricity = eccentricity;
        this.inclinationDegrees = inclinationDegrees;
        this.longitudeOfAscendingNodeDegrees = longitudeOfAscendingNodeDegrees;
        this.longitudeOfPerihelionDegrees = longitudeOfPerihelionDegrees;
        this.meanLongitudeAtJ2000Degrees = meanLongitudeAtJ2000Degrees;
    }

    /** A circular orbit in the reference plane, starting at its reference direction at J2000. */
    public static Orbit circular(double semiMajorAxisAu) {
        return new Orbit(semiMajorAxisAu, 0, 0, 0);
    }

    /** Distance to the central body at perihelion, in AU. */
    public double perihelionAu() {
        return semiMajorAxisAu * (1 - eccentricity);
    }

    /** Distance to the central body at aphelion, in AU. */
    public double aphelionAu() {
        return semiMajorAxisAu * (1 + eccentricity);
    }

    /** Orbital period, in days, from Kepler's third law for two bodies of the given total mass. */
    public double periodDays(double totalMassKg) {
        double semiMajorAxisMeters = semiMajorAxisAu * PhysicalConstants.AU;
        double seconds = 2 * Math.PI * Math.sqrt(Math.pow(semiMajorAxisMeters, 3) / (PhysicalConstants.G * totalMassKg));
        return seconds / PhysicalConstants.DAY;
    }

    /** Position relative to the central body at the given instant, for two bodies of the given total mass. */
    public OrbitalPosition positionAt(Instant instant, double totalMassKg) {
        double daysSinceJ2000 = Duration.between(PhysicalConstants.J2000, instant).toSeconds() / PhysicalConstants.DAY;
        return positionAt(daysSinceJ2000, periodDays(totalMassKg));
    }

    /**
     * Position relative to the central body, a given number of days after J2000,
     * for an orbit completed in {@code periodDays}; solves Kepler's equation M = E − e·sin E.
     */
    public OrbitalPosition positionAt(double daysSinceJ2000, double periodDays) {
        double meanAnomaly = Math.IEEEremainder(Math.toRadians(
                meanLongitudeAtJ2000Degrees - longitudeOfPerihelionDegrees + 360 * daysSinceJ2000 / periodDays), 2 * Math.PI);
        double eccentricAnomaly = meanAnomaly;
        for (int i = 0; i < 50; i++) {
            double delta = (eccentricAnomaly - eccentricity * Math.sin(eccentricAnomaly) - meanAnomaly)
                    / (1 - eccentricity * Math.cos(eccentricAnomaly));
            eccentricAnomaly -= delta;
            if (Math.abs(delta) < 1e-12) {
                break;
            }
        }
        double xInOrbit = semiMajorAxisAu * (Math.cos(eccentricAnomaly) - eccentricity);
        double yInOrbit = semiMajorAxisAu * Math.sqrt(1 - eccentricity * eccentricity) * Math.sin(eccentricAnomaly);

        // Rotate from the orbital plane to the reference plane: argument of perihelion, inclination, then node
        double node = Math.toRadians(longitudeOfAscendingNodeDegrees);
        double argumentOfPerihelion = Math.toRadians(longitudeOfPerihelionDegrees) - node;
        double inclination = Math.toRadians(inclinationDegrees);
        double cosW = Math.cos(argumentOfPerihelion), sinW = Math.sin(argumentOfPerihelion);
        double cosN = Math.cos(node), sinN = Math.sin(node);
        double cosI = Math.cos(inclination), sinI = Math.sin(inclination);
        return new OrbitalPosition(
                (cosW * cosN - sinW * sinN * cosI) * xInOrbit + (-sinW * cosN - cosW * sinN * cosI) * yInOrbit,
                (cosW * sinN + sinW * cosN * cosI) * xInOrbit + (-sinW * sinN + cosW * cosN * cosI) * yInOrbit,
                sinW * sinI * xInOrbit + cosW * sinI * yInOrbit);
    }
}
