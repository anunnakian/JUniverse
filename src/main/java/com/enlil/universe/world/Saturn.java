package com.enlil.universe.world;

import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;

import java.util.List;

import static com.enlil.universe.celestial.PhysicalConstants.AU;

/** Its moons have their real orbit size and shape; their orientation and phase at J2000 are not modeled. */
public class Saturn extends Planet {

    public Saturn() {
        super("Saturn", 5.683_4e26, 5.823_2e7,
                new Orbit(9.536_675_94, 0.053_861_79, 2.485_991_87, 113.662_424_48, 92.598_878_31, 49.954_244_23), List.of(
                new NaturalSatellite("Enceladus", 1.080_2e20, 2.521e5, new Orbit(237_948e3 / AU, 0.004_7, 0, 0)),
                new NaturalSatellite("Rhea", 2.306_5e21, 7.638e5, new Orbit(527_108e3 / AU, 0.001, 0, 0)),
                new NaturalSatellite("Titan", 1.345_2e23, 2.574_7e6, new Orbit(1_221_870e3 / AU, 0.028_8, 0, 0))));
    }
}
