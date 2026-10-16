package com.enlil.universe.world.cosmology;

import com.enlil.universe.cosmology.CosmologicalModel;

/**
 * The cosmology measured by the Planck satellite (Planck 2018 results VI, TT,TE,EE+lowE+lensing): H₀ = 67.4 km/s/Mpc,
 * Ω_m = 0.315, radiation Ω_r = 9.0e-5 and dark energy making up the rest of a flat universe, with the FIRAS CMB
 * temperature. Planck gives an age of 13.787 ± 0.020 billion years.
 */
public class Planck2018 extends CosmologicalModel {

    private static final double MATTER_DENSITY = 0.315;
    private static final double RADIATION_DENSITY = 9.0e-5;

    public Planck2018() {
        super(67.4, MATTER_DENSITY, 1 - MATTER_DENSITY - RADIATION_DENSITY, RADIATION_DENSITY, 2.725_5);
    }
}
