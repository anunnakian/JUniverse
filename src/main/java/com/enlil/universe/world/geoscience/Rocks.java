package com.enlil.universe.world.geoscience;

import com.enlil.universe.geoscience.Rock;
import com.enlil.universe.geoscience.RockType;

import java.util.List;

import static com.enlil.universe.world.geoscience.Minerals.*;

/**
 * Real rocks, with their main minerals.
 */
public final class Rocks {

    /** The continents' crust, cooled slowly deep underground. */
    public static final Rock GRANITE = new Rock("Granite", RockType.IGNEOUS, List.of(QUARTZ, ORTHOCLASE));
    /** The ocean floor, cooled fast from lava. */
    public static final Rock BASALT = new Rock("Basalt", RockType.IGNEOUS, List.of(OLIVINE));
    public static final Rock SANDSTONE = new Rock("Sandstone", RockType.SEDIMENTARY, List.of(QUARTZ));
    /** Mostly the shells of sea creatures. */
    public static final Rock LIMESTONE = new Rock("Limestone", RockType.SEDIMENTARY, List.of(CALCITE));
    /** Limestone transformed by heat and pressure. */
    public static final Rock MARBLE = new Rock("Marble", RockType.METAMORPHIC, List.of(CALCITE));
    /** Left behind by evaporated seas. */
    public static final Rock ROCK_SALT = new Rock("Rock salt", RockType.SEDIMENTARY, List.of(HALITE));

    private Rocks() {
    }
}
