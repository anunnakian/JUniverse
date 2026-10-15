package com.enlil.universe.world;

import com.enlil.universe.celestial.Galaxy;
import com.enlil.universe.celestial.GalaxyType;

import java.util.List;

/** A galaxy of the Local Group; its stars are not modeled individually. */
public class LargeMagellanicCloud extends Galaxy {

    public LargeMagellanicCloud() {
        super("Large Magellanic Cloud", GalaxyType.IRREGULAR, List.of());
    }
}
