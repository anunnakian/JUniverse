package com.enlil.universe.world;

import com.enlil.universe.celestial.PhysicalConstants;
import com.enlil.universe.celestial.Planet;
import com.enlil.universe.celestial.Star;

import java.util.List;

public class Sun extends Star {

    public Sun(List<Planet> planets) {
        super("Sun", PhysicalConstants.SOLAR_MASS, 6.957e8, 5_772, planets);
    }
}
