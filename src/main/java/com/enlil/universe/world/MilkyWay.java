package com.enlil.universe.world;

import com.enlil.universe.celestial.Galaxy;
import com.enlil.universe.celestial.GalaxyType;
import com.enlil.universe.celestial.Star;

import java.util.List;

public class MilkyWay extends Galaxy {

    public MilkyWay(List<Star> stars) {
        super("Milky Way", GalaxyType.BARRED_SPIRAL, stars);
    }
}
