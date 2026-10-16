package com.enlil.universe.world.cosmology;

import com.enlil.universe.cosmology.CosmicEvent;

import java.util.Comparator;
import java.util.List;

/**
 * Milestones of cosmic history, with consensus dates. The geological ones repeat the Earth sciences values (start of
 * the Cambrian, the end-Cretaceous extinction) as literals.
 */
public final class CosmicHistory {

    private static final double AGE_YEARS = new Planck2018().ageYears();

    /** The Big Bang, dated from the age of the universe in the Planck 2018 cosmology. */
    public static final CosmicEvent BIG_BANG = new CosmicEvent("Big Bang", AGE_YEARS);
    /** Atoms form and light breaks free: the cosmic microwave background. */
    public static final CosmicEvent RECOMBINATION = new CosmicEvent("Recombination", AGE_YEARS - 377_000);
    public static final CosmicEvent FIRST_STARS = new CosmicEvent("First stars", 13.6e9);
    public static final CosmicEvent MILKY_WAY_DISK = new CosmicEvent("Milky Way's disk", 8.8e9);
    public static final CosmicEvent SOLAR_SYSTEM = new CosmicEvent("Solar System", 4.567e9);
    public static final CosmicEvent EARTH = new CosmicEvent("Earth", 4.543e9);
    /** The giant impact that made the Moon. */
    public static final CosmicEvent MOON = new CosmicEvent("Moon", 4.51e9);
    public static final CosmicEvent FIRST_LIFE = new CosmicEvent("First life", 3.7e9);
    /** The Great Oxidation Event. */
    public static final CosmicEvent OXYGEN_ATMOSPHERE = new CosmicEvent("Oxygen atmosphere", 2.4e9);
    public static final CosmicEvent CAMBRIAN_EXPLOSION = new CosmicEvent("Cambrian explosion", 538.8e6);
    public static final CosmicEvent DINOSAURS_EXTINCTION = new CosmicEvent("Extinction of the dinosaurs", 66e6);
    public static final CosmicEvent HOMO_SAPIENS = new CosmicEvent("Homo sapiens", 300_000);
    /** Cuneiform, in Sumer. */
    public static final CosmicEvent WRITING = new CosmicEvent("Writing", 5_200);

    private CosmicHistory() {
    }

    /** Every event, the oldest first. */
    public static List<CosmicEvent> all() {
        return List.of(BIG_BANG, RECOMBINATION, FIRST_STARS, MILKY_WAY_DISK, SOLAR_SYSTEM, EARTH, MOON, FIRST_LIFE,
                        OXYGEN_ATMOSPHERE, CAMBRIAN_EXPLOSION, DINOSAURS_EXTINCTION, HOMO_SAPIENS, WRITING).stream()
                .sorted(Comparator.comparingDouble(CosmicEvent::getYearsAgo).reversed())
                .toList();
    }
}
