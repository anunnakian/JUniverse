package com.enlil.universe.world;

import com.enlil.universe.celestial.PhysicalConstants;
import com.enlil.universe.celestial.Star;

import java.util.List;

public class ProximaCentauri extends Star {

    public ProximaCentauri() {
        super("Proxima Centauri", 0.122_1 * PhysicalConstants.SOLAR_MASS, 1.072_8e8, 3_042, List.of());
    }
}
