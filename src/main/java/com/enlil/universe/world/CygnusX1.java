package com.enlil.universe.world;

import com.enlil.universe.celestial.BlackHole;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

/** The first black hole ever identified, in 1971; mass from Miller-Jones et al. (2021). */
public class CygnusX1 extends BlackHole {

    public CygnusX1() {
        super("Cygnus X-1", 21.2 * SOLAR_MASS);
    }
}
