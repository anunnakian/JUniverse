package com.enlil.universe.world.geoscience;

import com.enlil.universe.geoscience.Mineral;
import com.enlil.universe.matter.chemistry.Formula;

import java.util.List;

/**
 * Real minerals: the ten reference minerals of Friedrich Mohs's hardness scale (1812), and a few common others.
 */
public final class Minerals {

    public static final Mineral TALC = mineral("Talc", "Mg3Si4O10(OH)2", 1, 2_750);
    public static final Mineral GYPSUM = mineral("Gypsum", "CaSO4·2H2O", 2, 2_310);
    public static final Mineral CALCITE = mineral("Calcite", "CaCO3", 3, 2_710);
    public static final Mineral FLUORITE = mineral("Fluorite", "CaF2", 4, 3_180);
    public static final Mineral APATITE = mineral("Apatite", "Ca5(PO4)3F", 5, 3_190);
    public static final Mineral ORTHOCLASE = mineral("Orthoclase", "KAlSi3O8", 6, 2_560);
    public static final Mineral QUARTZ = mineral("Quartz", "SiO2", 7, 2_650);
    public static final Mineral TOPAZ = mineral("Topaz", "Al2SiO4F2", 8, 3_550);
    public static final Mineral CORUNDUM = mineral("Corundum", "Al2O3", 9, 4_020);
    public static final Mineral DIAMOND = mineral("Diamond", "C", 10, 3_510);
    /** The main iron ore. */
    public static final Mineral HEMATITE = mineral("Hematite", "Fe2O3", 6, 5_260);
    /** Forsterite, the magnesium olivine of the upper mantle. */
    public static final Mineral OLIVINE = mineral("Olivine", "Mg2SiO4", 6.5, 3_270);
    /** Rock salt. */
    public static final Mineral HALITE = mineral("Halite", "NaCl", 2.5, 2_170);

    /** The Mohs scale, from the softest reference mineral to the hardest. */
    public static final List<Mineral> MOHS_SCALE =
            List.of(TALC, GYPSUM, CALCITE, FLUORITE, APATITE, ORTHOCLASE, QUARTZ, TOPAZ, CORUNDUM, DIAMOND);

    private Minerals() {
    }

    private static Mineral mineral(String name, String formula, double mohsHardness, double densityKgPerCubicMeter) {
        return new Mineral(name, Formula.parse(formula), mohsHardness, densityKgPerCubicMeter);
    }
}
