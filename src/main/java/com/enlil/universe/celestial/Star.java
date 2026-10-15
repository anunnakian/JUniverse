package com.enlil.universe.celestial;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Getter
public class Star extends CelestialBody {

    private final double surfaceTemperatureKelvin;
    private final List<Planet> planets;

    public Star(String name, double massKg, double radiusMeters, double surfaceTemperatureKelvin, List<Planet> planets) {
        super(name, massKg, radiusMeters);
        this.surfaceTemperatureKelvin = surfaceTemperatureKelvin;
        this.planets = List.copyOf(planets);
    }

    public SpectralClass getSpectralClass() {
        return SpectralClass.fromTemperature(surfaceTemperatureKelvin);
    }

    /** Orbital period of a planet around this star, in days, from Kepler's third law. */
    public double orbitalPeriodDays(Planet planet) {
        return planet.getOrbit().periodDays(getMassKg() + planet.getMassKg());
    }

    /** Position of a planet around this star at the given instant. */
    public OrbitalPosition positionOf(Planet planet, Instant instant) {
        return planet.getOrbit().positionAt(instant, getMassKg() + planet.getMassKg());
    }

    /**
     * Fraction of a moon's disk lit by this star, as seen from its planet at the given instant:
     * 0 at new moon, 1 at full moon.
     */
    public double illuminatedFraction(Planet planet, NaturalSatellite moon, Instant instant) {
        OrbitalPosition towardsStar = new OrbitalPosition(0, 0, 0).minus(positionOf(planet, instant));
        double elongation = planet.positionOf(moon, instant).angleDegrees(towardsStar);
        return (1 - Math.cos(Math.toRadians(elongation))) / 2;
    }

    /** Distance between two planets of this star at the given instant, in AU. */
    public double distanceAu(Planet a, Planet b, Instant instant) {
        return positionOf(a, instant).distanceAu(positionOf(b, instant));
    }

    /** The day, between {@code from} and {@code to}, when two planets of this star come closest to each other. */
    public Instant closestApproach(Planet a, Planet b, Instant from, Instant to) {
        Instant closest = from;
        for (Instant day = from; !day.isAfter(to); day = day.plus(Duration.ofDays(1))) {
            if (distanceAu(a, b, day) < distanceAu(a, b, closest)) {
                closest = day;
            }
        }
        return closest;
    }
}
