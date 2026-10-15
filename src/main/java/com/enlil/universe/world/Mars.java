package com.enlil.universe.world;

import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;

import java.util.List;

import static com.enlil.universe.celestial.PhysicalConstants.AU;

/** Its moons have their real orbit size and shape; their orientation and phase at J2000 are not modeled. */
public class Mars extends Planet {

    public Mars() {
        super("Mars", 6.417_1e23, 3.389_5e6,
                new Orbit(1.523_710_34, 0.093_394_10, 1.849_691_42, 49.559_538_91, -23.943_629_59, -4.553_432_05), List.of(
                new NaturalSatellite("Phobos", 1.065_9e16, 1.126_7e4, new Orbit(9_376e3 / AU, 0.015_1, 0, 0)),
                new NaturalSatellite("Deimos", 1.476_2e15, 6.2e3, new Orbit(23_463e3 / AU, 0.000_33, 0, 0))));
    }
}
