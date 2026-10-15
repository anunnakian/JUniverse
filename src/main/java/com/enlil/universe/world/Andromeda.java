package com.enlil.universe.world;

import com.enlil.universe.celestial.Galaxy;
import com.enlil.universe.celestial.GalaxyType;

import java.util.List;

/** A galaxy of the Local Group; its stars are not modeled individually. */
public class Andromeda extends Galaxy {

    public Andromeda() {
        super("Andromeda", GalaxyType.SPIRAL, List.of());
    }
}
