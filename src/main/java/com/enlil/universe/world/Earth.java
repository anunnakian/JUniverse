package com.enlil.universe.world;

import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.civilization.Civilization;
import com.enlil.universe.celestial.Planet;
import com.enlil.universe.life.Creature;

import java.util.List;

public class Earth extends Planet {

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
}
