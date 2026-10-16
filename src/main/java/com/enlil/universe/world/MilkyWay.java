package com.enlil.universe.world;

import com.enlil.universe.celestial.Galaxy;
import com.enlil.universe.celestial.GalaxyType;
import com.enlil.universe.celestial.Star;
import com.enlil.universe.celestial.StellarRemnant;

import java.util.List;

/** Our galaxy, with Sagittarius A* at its center. */
public class MilkyWay extends Galaxy {

    public MilkyWay(List<Star> stars) {
        this(stars, List.of());
    }

    public MilkyWay(List<Star> stars, List<StellarRemnant> remnants) {
        super("Milky Way", GalaxyType.BARRED_SPIRAL, stars, remnants, new SagittariusAStar());
    }
}
