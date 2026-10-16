package com.enlil.universe.world.chemistry;

import com.enlil.universe.matter.chemistry.Mixture;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.enlil.universe.world.chemistry.Compounds.*;

/**
 * Real mixtures.
 */
public final class Mixtures {

    /** Dry air at sea level (NOAA mole fractions; carbon dioxide at 420 ppm). */
    public static final Mixture DRY_AIR = new Mixture(ordered(
            NITROGEN_GAS, 0.780_8, OXYGEN_GAS, 0.209_5, ARGON, 0.009_34, CARBON_DIOXIDE, 0.000_42));
    /** Seawater at a salinity of 35 g of salt per kilogram, counted as sodium chloride. */
    public static final Mixture SEAWATER = Mixture.ofMasses(ordered(WATER, 965.0, SODIUM_CHLORIDE, 35.0));
    /** The air of Mars (Curiosity SAM, normalized to its three main gases). */
    public static final Mixture MARTIAN_AIR = new Mixture(ordered(
            CARBON_DIOXIDE, 0.951, NITROGEN_GAS, 0.028, ARGON, 0.021));
    /** The air of Venus. */
    public static final Mixture VENUSIAN_AIR = new Mixture(ordered(CARBON_DIOXIDE, 0.965, NITROGEN_GAS, 0.035));

    private Mixtures() {
    }

    /** A map keeping the order of its alternating keys and values. */
    @SuppressWarnings("unchecked")
    private static <K> Map<K, Double> ordered(Object... keysAndValues) {
        Map<K, Double> map = new LinkedHashMap<>();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put((K) keysAndValues[i], (Double) keysAndValues[i + 1]);
        }
        return map;
    }
}
