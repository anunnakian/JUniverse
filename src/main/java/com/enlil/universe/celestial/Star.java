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

    /** Power radiated by the whole star, 4πR²σT⁴ (Stefan–Boltzmann law), in watts. */
    public double luminosityWatts() {
        double radius = getRadiusMeters();
        return 4 * Math.PI * radius * radius * PhysicalConstants.STEFAN_BOLTZMANN
                * Math.pow(surfaceTemperatureKelvin, 4);
    }

    /** Luminosity in units of the Sun's nominal luminosity. */
    public double luminositySolar() {
        return luminosityWatts() / PhysicalConstants.SOLAR_LUMINOSITY;
    }

    /** Absolute bolometric magnitude, 4.74 − 2.5·log₁₀(L/L☉): lower is brighter. */
    public double absoluteBolometricMagnitude() {
        return PhysicalConstants.SOLAR_BOLOMETRIC_MAGNITUDE - 2.5 * Math.log10(luminositySolar());
    }

    /** Mass in units of the Sun's mass. */
    public double massSolar() {
        return getMassKg() / PhysicalConstants.SOLAR_MASS;
    }

    /**
     * Rough time spent fusing hydrogen, 10¹⁰ · (M/M☉)^−2.5 years, from the textbook scaling L ∝ M^3.5. Poor below
     * 0.4 solar masses, where real lifetimes are even longer.
     */
    public double mainSequenceLifetimeYears() {
        return 1e10 * Math.pow(massSolar(), -2.5);
    }

    /**
     * Where liquid water could exist around this star, from the conservative stellar fluxes of Kasting and Kopparapu:
     * inner edge √(L/1.1) AU, outer edge √(L/0.53) AU, with L in solar units.
     */
    public HabitableZone getHabitableZone() {
        double luminosity = luminositySolar();
        return new HabitableZone(Math.sqrt(luminosity / 1.1), Math.sqrt(luminosity / 0.53));
    }

    /** What this star becomes when it dies, taking its mass as its initial mass (mass loss isn't modeled). */
    public StellarFate getFate() {
        return StellarFate.fromInitialMass(massSolar());
    }

    /**
     * The stage of this star at a given age, from a three-phase textbook model: the main sequence for its lifetime,
     * then a giant phase lasting a tenth of it (red giant below 8 solar masses, red supergiant above), then its
     * remnant. Never a protostar, as the pre-main sequence isn't modeled.
     *
     * @param ageYears time since the star started fusing hydrogen, in years
     */
    public StellarStage stageAt(double ageYears) {
        if (ageYears < 0) {
            throw new IllegalArgumentException("age must not be negative");
        }
        double lifetime = mainSequenceLifetimeYears();
        if (ageYears < lifetime) {
            return StellarStage.MAIN_SEQUENCE;
        }
        if (ageYears < 1.1 * lifetime) {
            return massSolar() < 8 ? StellarStage.RED_GIANT : StellarStage.RED_SUPERGIANT;
        }
        return getFate().getRemnant();
    }

    /**
     * Temperature a planet of this star would have as a black body in radiative equilibrium, without any greenhouse
     * effect, in kelvins: T★ · √(R★ / 2a) · (1 − A)^¼, with a the planet's semi-major axis.
     *
     * @param bondAlbedo share of the starlight the planet reflects, in [0, 1)
     */
    public double equilibriumTemperatureKelvin(Planet planet, double bondAlbedo) {
        if (bondAlbedo < 0 || bondAlbedo >= 1) {
            throw new IllegalArgumentException("albedo must be in [0, 1)");
        }
        double distanceMeters = planet.getSemiMajorAxisAu() * PhysicalConstants.AU;
        return surfaceTemperatureKelvin * Math.sqrt(getRadiusMeters() / (2 * distanceMeters))
                * Math.pow(1 - bondAlbedo, 0.25);
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
