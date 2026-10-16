package com.enlil.universe.world;

import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.civilization.Civilization;
import com.enlil.universe.celestial.Planet;
import com.enlil.universe.geoscience.Atmosphere;
import com.enlil.universe.geoscience.Interior;
import com.enlil.universe.geoscience.InternalLayer;
import com.enlil.universe.geoscience.TectonicPlate;
import com.enlil.universe.life.Creature;
import com.enlil.universe.matter.StateOfMatter;
import com.enlil.universe.world.chemistry.Mixtures;
import com.enlil.universe.world.geoscience.TectonicPlates;

import java.util.List;
import java.util.Optional;

public class Earth extends Planet {

    /** Share of the sunlight the Earth reflects back to space (CERES). */
    public static final double BOND_ALBEDO = 0.306;

    /** Dry air at sea level, 101 325 Pa, and a mean surface temperature of 15 °C. */
    private static final Atmosphere ATMOSPHERE = new Atmosphere(Mixtures.DRY_AIR, 101_325, 288);
    /** PREM layer boundaries (Dziewonski and Anderson, 1981), with mean densities. */
    private static final Interior INTERIOR = new Interior(List.of(
            new InternalLayer("Inner core", 1_221e3, 12_900, StateOfMatter.SOLID),
            new InternalLayer("Outer core", 3_480e3, 10_900, StateOfMatter.LIQUID),
            new InternalLayer("Lower mantle", 5_701e3, 4_900, StateOfMatter.SOLID),
            new InternalLayer("Upper mantle", 6_346.6e3, 3_700, StateOfMatter.SOLID),
            new InternalLayer("Crust", 6_371e3, 2_800, StateOfMatter.SOLID)));

    public Earth() {
        this(List.of());
    }

    public Earth(List<Creature> creatures) {
        this(creatures, List.of());
    }

    public Earth(List<Creature> creatures, List<Civilization> civilizations) {
        super("Earth", 5.972_2e24, 6.371e6,
                new Orbit(1.000_002_61, 0.016_711_23, -0.000_015_31, 0.0, 102.937_681_93, 100.464_571_66),
                List.of(new Moon()), creatures, civilizations);
    }

    @Override
    public Optional<Atmosphere> getAtmosphere() {
        return Optional.of(ATMOSPHERE);
    }

    @Override
    public Optional<Interior> getInterior() {
        return Optional.of(INTERIOR);
    }

    /** The plates the Earth's surface is broken into. */
    public List<TectonicPlate> getTectonicPlates() {
        return TectonicPlates.ALL;
    }
}
