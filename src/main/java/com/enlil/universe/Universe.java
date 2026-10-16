package com.enlil.universe;

import com.enlil.universe.celestial.*;
import com.enlil.universe.civilization.Civilization;
import com.enlil.universe.cosmology.CosmologicalModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class Universe implements BigBangFlow<List<CelestialObject>> {

    @Singular
    private List<Galaxy> galaxies;
    /** The model its expansion follows; null when none is given. */
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    private CosmologicalModel cosmology;

    /** The cosmological model this universe expands by, which gives its age; empty when none is given. */
    public Optional<CosmologicalModel> getCosmology() {
        return Optional.ofNullable(cosmology);
    }

    /** All the stars of the galaxies of this universe. */
    public List<Star> getStars() {
        return galaxies.stream()
                .flatMap(galaxy -> galaxy.getStars().stream())
                .toList();
    }

    /** All the black holes of the galaxies of this universe, central ones included. */
    public List<BlackHole> getBlackHoles() {
        return galaxies.stream()
                .flatMap(galaxy -> galaxy.getBlackHoles().stream())
                .toList();
    }

    /** All the planets orbiting the stars of this universe. */
    public List<Planet> getPlanets() {
        return galaxies.stream()
                .flatMap(galaxy -> galaxy.getPlanets().stream())
                .toList();
    }

    /** All the civilizations living on the planets of this universe. */
    public List<Civilization> getCivilizations() {
        return galaxies.stream()
                .flatMap(galaxy -> galaxy.getCivilizations().stream())
                .toList();
    }

    @Override
    public List<CelestialObject> createThing() {
        return List.copyOf(galaxies);
    }
}
