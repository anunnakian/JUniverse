package com.enlil.universe.world;

import com.enlil.universe.celestial.PhysicalConstants;
import com.enlil.universe.celestial.Star;

import java.util.List;

public class Sirius extends Star {

    public Sirius() {
        super("Sirius", 2.063 * PhysicalConstants.SOLAR_MASS, 1.190_3e9, 9_940, List.of());
    }
}
