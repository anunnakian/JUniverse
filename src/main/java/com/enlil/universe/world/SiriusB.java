package com.enlil.universe.world;

import com.enlil.universe.celestial.WhiteDwarf;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;
import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_RADIUS;

/** The white dwarf companion of Sirius, the nearest white dwarf but one. */
public class SiriusB extends WhiteDwarf {

    public SiriusB() {
        super("Sirius B", 1.018 * SOLAR_MASS, 0.008_4 * SOLAR_RADIUS, 25_000);
    }
}
