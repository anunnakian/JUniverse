package com.enlil.universe.world;

import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;

public class Mercury extends Planet {

    public Mercury() {
        super("Mercury", 3.301_1e23, 2.439_7e6,
                new Orbit(0.387_099_27, 0.205_635_93, 7.004_979_02, 48.330_765_93, 77.457_796_28, 252.250_323_50));
    }
}
