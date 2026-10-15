package com.enlil.universe.world;

import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;

import java.util.List;

import static com.enlil.universe.celestial.PhysicalConstants.AU;

/** Its moons have their real orbit size and shape; their orientation and phase at J2000 are not modeled. */
public class Neptune extends Planet {

    public Neptune() {
        super("Neptune", 1.024_13e26, 2.462_2e7,
                new Orbit(30.069_922_76, 0.008_590_48, 1.770_043_47, 131.784_225_74, 44.964_762_27, -55.120_029_69), List.of(
                new NaturalSatellite("Triton", 2.139e22, 1.353_4e6, new Orbit(354_759e3 / AU, 0.000_016, 0, 0))));
    }
}
