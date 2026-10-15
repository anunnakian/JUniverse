package com.enlil.universe.celestial;

import com.enlil.universe.civilization.Civilization;
import lombok.Getter;

import java.util.List;

@Getter
public class Galaxy implements CelestialObject {

    private final String name;
    private final GalaxyType type;
    private final List<Star> stars;

    public Galaxy(String name, GalaxyType type, List<Star> stars) {
        this.name = name;
        this.type = type;
        this.stars = List.copyOf(stars);
    }

    /** All the planets orbiting the stars of this galaxy. */
    public List<Planet> getPlanets() {
        return stars.stream()
                .flatMap(star -> star.getPlanets().stream())
                .toList();
    }

    /** All the civilizations living on the planets of this galaxy. */
    public List<Civilization> getCivilizations() {
        return getPlanets().stream()
                .flatMap(planet -> planet.getCivilizations().stream())
                .toList();
    }
}
