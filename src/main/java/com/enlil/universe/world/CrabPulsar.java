package com.enlil.universe.world;

import com.enlil.universe.celestial.NeutronStar;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

/**
 * The pulsar left by the supernova Chinese astronomers saw in 1054. Its mass is the canonical 1.4 solar masses, not a
 * measurement.
 */
public class CrabPulsar extends NeutronStar {

    public CrabPulsar() {
        super("Crab Pulsar", 1.4 * SOLAR_MASS, 10e3, 0.033_5);
    }
}
