package com.enlil.universe.celestial;

import com.enlil.universe.civilization.Civilization;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public class Galaxy implements CelestialObject {

    private final String name;
    private final GalaxyType type;
    private final List<Star> stars;
    /** Dead stars: white dwarfs, neutron stars and stellar black holes. */
    private final List<StellarRemnant> remnants;
    /** Null when the galaxy's central black hole isn't modeled. */
    @Getter(AccessLevel.NONE)
    private final BlackHole centralBlackHole;

    public Galaxy(String name, GalaxyType type, List<Star> stars) {
        this(name, type, stars, List.of(), null);
    }

    /**
     * @param centralBlackHole the supermassive black hole at its heart; null when not modeled
     */
    public Galaxy(String name, GalaxyType type, List<Star> stars, List<StellarRemnant> remnants,
                  BlackHole centralBlackHole) {
        this.name = name;
        this.type = type;
        this.stars = List.copyOf(stars);
        this.remnants = List.copyOf(remnants);
        this.centralBlackHole = centralBlackHole;
    }

    /** The black hole at the center of this galaxy; empty when not modeled. */
    public Optional<BlackHole> getCentralBlackHole() {
        return Optional.ofNullable(centralBlackHole);
    }

    /** The central black hole first, if any, then the stellar black holes among the remnants. */
    public List<BlackHole> getBlackHoles() {
        return Stream.concat(getCentralBlackHole().stream(),
                        remnants.stream().filter(BlackHole.class::isInstance).map(BlackHole.class::cast))
                .toList();
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
