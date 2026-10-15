package com.enlil.universe.world;

import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;

import java.util.List;

import static com.enlil.universe.celestial.PhysicalConstants.AU;

/** Its moons have their real orbit size and shape; their orientation and phase at J2000 are not modeled. */
public class Jupiter extends Planet {

    public Jupiter() {
        super("Jupiter", 1.898_19e27, 6.991_1e7,
                new Orbit(5.202_887_00, 0.048_386_24, 1.304_396_95, 100.473_909_09, 14.728_479_83, 34.396_440_51), List.of(
                new NaturalSatellite("Io", 8.931_9e22, 1.821_6e6, new Orbit(421_700e3 / AU, 0.004_1, 0, 0)),
                new NaturalSatellite("Europa", 4.799_8e22, 1.560_8e6, new Orbit(671_034e3 / AU, 0.009, 0, 0)),
                new NaturalSatellite("Ganymede", 1.481_9e23, 2.634_1e6, new Orbit(1_070_412e3 / AU, 0.001_3, 0, 0)),
                new NaturalSatellite("Callisto", 1.075_9e23, 2.410_3e6, new Orbit(1_882_709e3 / AU, 0.007_4, 0, 0))));
    }
}
