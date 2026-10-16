package com.enlil.universe.world;

import com.enlil.universe.celestial.BlackHole;

import static com.enlil.universe.celestial.PhysicalConstants.SOLAR_MASS;

/** The supermassive black hole at the center of Andromeda (M31). */
public class M31Star extends BlackHole {

    public M31Star() {
        super("M31*", 1.4e8 * SOLAR_MASS);
    }
}
