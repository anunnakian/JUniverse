package com.enlil.universe.world;

import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;

public class Venus extends Planet {

    public Venus() {
        super("Venus", 4.867_5e24, 6.051_8e6,
                new Orbit(0.723_335_66, 0.006_776_72, 3.394_676_05, 76.679_842_55, 131.602_467_18, 181.979_099_50));
    }
}
