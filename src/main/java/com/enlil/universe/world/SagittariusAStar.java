package com.enlil.universe.world;

import com.enlil.universe.celestial.BlackHole;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

/** The supermassive black hole at the center of the Milky Way; mass from the GRAVITY collaboration (2022). */
public class SagittariusAStar extends BlackHole {

    public SagittariusAStar() {
        super("Sagittarius A*", 4.297e6 * SOLAR_MASS);
    }
}
