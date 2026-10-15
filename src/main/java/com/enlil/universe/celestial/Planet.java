package com.enlil.universe.celestial;

import com.enlil.universe.civilization.Civilization;
import com.enlil.universe.life.Creature;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class Planet extends CelestialBody {

    private final Orbit orbit;
    private final List<NaturalSatellite> moons;
    private final List<Creature> creatures;
    private final List<Civilization> civilizations;

    public Planet(String name, double massKg, double radiusMeters, double semiMajorAxisAu) {
        this(name, massKg, radiusMeters, Orbit.circular(semiMajorAxisAu));
    }

    public Planet(String name, double massKg, double radiusMeters, Orbit orbit) {
        this(name, massKg, radiusMeters, orbit, List.of());
    }

    public Planet(String name, double massKg, double radiusMeters, Orbit orbit, List<NaturalSatellite> moons) {
        this(name, massKg, radiusMeters, orbit, moons, List.of());
    }

    public Planet(String name, double massKg, double radiusMeters, Orbit orbit,
                  List<NaturalSatellite> moons, List<Creature> creatures) {
        this(name, massKg, radiusMeters, orbit, moons, creatures, List.of());
    }

    public Planet(String name, double massKg, double radiusMeters, Orbit orbit,
                  List<NaturalSatellite> moons, List<Creature> creatures, List<Civilization> civilizations) {
        super(name, massKg, radiusMeters);
        this.orbit = orbit;
        this.moons = List.copyOf(moons);
        this.creatures = List.copyOf(creatures);
        this.civilizations = List.copyOf(civilizations);
    }

    public double getSemiMajorAxisAu() {
        return orbit.getSemiMajorAxisAu();
    }

    /** Orbital period of a moon around this planet, in days, from Kepler's third law. */
    public double orbitalPeriodDays(NaturalSatellite moon) {
        return moon.getOrbit().periodDays(getMassKg() + moon.getMassKg());
    }

    /** Position of a moon around this planet at the given instant. */
    public OrbitalPosition positionOf(NaturalSatellite moon, Instant instant) {
        return moon.getOrbit().positionAt(instant, getMassKg() + moon.getMassKg());
    }

    /** Weight of the given creature on this planet's surface, in newtons. */
    public double weightNewtons(Creature creature) {
        return creature.weightNewtons(surfaceGravity());
    }
}
