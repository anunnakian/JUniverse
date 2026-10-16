package com.enlil.universe.world;

import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;
import com.enlil.universe.geoscience.Atmosphere;
import com.enlil.universe.world.chemistry.Mixtures;

import java.util.Optional;

public class Venus extends Planet {

    /** Share of the sunlight Venus reflects back to space, off its sulfuric acid clouds. */
    public static final double BOND_ALBEDO = 0.76;

    /** A crushing carbon dioxide atmosphere: 9.2 MPa at the surface and a runaway greenhouse at 737 K. */
    private static final Atmosphere ATMOSPHERE = new Atmosphere(Mixtures.VENUSIAN_AIR, 9.2e6, 737);

    public Venus() {
        super("Venus", 4.867_5e24, 6.051_8e6,
                new Orbit(0.723_335_66, 0.006_776_72, 3.394_676_05, 76.679_842_55, 131.602_467_18, 181.979_099_50));
    }

    @Override
    public Optional<Atmosphere> getAtmosphere() {
        return Optional.of(ATMOSPHERE);
    }
}
