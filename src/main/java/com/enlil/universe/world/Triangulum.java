package com.enlil.universe.world;

import com.enlil.universe.celestial.Galaxy;
import com.enlil.universe.celestial.GalaxyType;

import java.util.List;

/** A galaxy of the Local Group; its stars are not modeled individually. */
public class Triangulum extends Galaxy {

    public Triangulum() {
        super("Triangulum", GalaxyType.SPIRAL, List.of());
    }
}
