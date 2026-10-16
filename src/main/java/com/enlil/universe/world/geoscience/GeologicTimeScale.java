package com.enlil.universe.world.geoscience;

import com.enlil.universe.geoscience.GeologicRank;
import com.enlil.universe.geoscience.GeologicTimeUnit;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.enlil.universe.geoscience.GeologicRank.*;

/**
 * The geological time scale of the International Commission on Stratigraphy (International Chronostratigraphic
 * Chart, v2023/09), in millions of years ago. Eras and periods are only modeled in the Phanerozoic.
 */
public final class GeologicTimeScale {

    public static final GeologicTimeUnit HADEAN = new GeologicTimeUnit("Hadean", EON, 4_567, 4_031);
    public static final GeologicTimeUnit ARCHEAN = new GeologicTimeUnit("Archean", EON, 4_031, 2_500);
    public static final GeologicTimeUnit PROTEROZOIC = new GeologicTimeUnit("Proterozoic", EON, 2_500, 538.8);
    /** The eon of visible life. */
    public static final GeologicTimeUnit PHANEROZOIC = new GeologicTimeUnit("Phanerozoic", EON, 538.8, 0);

    public static final GeologicTimeUnit PALEOZOIC =
            new GeologicTimeUnit("Paleozoic", ERA, 538.8, 251.902, PHANEROZOIC);
    /** The age of the dinosaurs. */
    public static final GeologicTimeUnit MESOZOIC = new GeologicTimeUnit("Mesozoic", ERA, 251.902, 66.0, PHANEROZOIC);
    /** The age of mammals. */
    public static final GeologicTimeUnit CENOZOIC = new GeologicTimeUnit("Cenozoic", ERA, 66.0, 0, PHANEROZOIC);

    public static final GeologicTimeUnit CAMBRIAN = new GeologicTimeUnit("Cambrian", PERIOD, 538.8, 486.85, PALEOZOIC);
    public static final GeologicTimeUnit ORDOVICIAN =
            new GeologicTimeUnit("Ordovician", PERIOD, 486.85, 443.1, PALEOZOIC);
    public static final GeologicTimeUnit SILURIAN = new GeologicTimeUnit("Silurian", PERIOD, 443.1, 419.62, PALEOZOIC);
    public static final GeologicTimeUnit DEVONIAN = new GeologicTimeUnit("Devonian", PERIOD, 419.62, 358.86, PALEOZOIC);
    public static final GeologicTimeUnit CARBONIFEROUS =
            new GeologicTimeUnit("Carboniferous", PERIOD, 358.86, 298.9, PALEOZOIC);
    public static final GeologicTimeUnit PERMIAN = new GeologicTimeUnit("Permian", PERIOD, 298.9, 251.902, PALEOZOIC);
    public static final GeologicTimeUnit TRIASSIC = new GeologicTimeUnit("Triassic", PERIOD, 251.902, 201.4, MESOZOIC);
    public static final GeologicTimeUnit JURASSIC = new GeologicTimeUnit("Jurassic", PERIOD, 201.4, 143.1, MESOZOIC);
    public static final GeologicTimeUnit CRETACEOUS = new GeologicTimeUnit("Cretaceous", PERIOD, 143.1, 66.0, MESOZOIC);
    public static final GeologicTimeUnit PALEOGENE = new GeologicTimeUnit("Paleogene", PERIOD, 66.0, 23.04, CENOZOIC);
    public static final GeologicTimeUnit NEOGENE = new GeologicTimeUnit("Neogene", PERIOD, 23.04, 2.58, CENOZOIC);
    /** The ice ages, and humans. */
    public static final GeologicTimeUnit QUATERNARY = new GeologicTimeUnit("Quaternary", PERIOD, 2.58, 0, CENOZOIC);

    private static final List<GeologicTimeUnit> ALL = List.of(
            HADEAN, ARCHEAN, PROTEROZOIC, PHANEROZOIC,
            PALEOZOIC, MESOZOIC, CENOZOIC,
            CAMBRIAN, ORDOVICIAN, SILURIAN, DEVONIAN, CARBONIFEROUS, PERMIAN,
            TRIASSIC, JURASSIC, CRETACEOUS,
            PALEOGENE, NEOGENE, QUATERNARY);

    private GeologicTimeScale() {
    }

    /** Every unit, in chronological order (oldest first, larger units before the smaller ones they contain). */
    public static List<GeologicTimeUnit> all() {
        return ALL.stream()
                .sorted(Comparator.comparingDouble(GeologicTimeUnit::getStartMillionYearsAgo).reversed()
                        .thenComparing(GeologicTimeUnit::getRank))
                .toList();
    }

    /** The units of one rank, in chronological order. */
    public static List<GeologicTimeUnit> ofRank(GeologicRank rank) {
        return all().stream().filter(unit -> unit.getRank() == rank).toList();
    }

    /**
     * The unit of a given rank at a moment of the Earth's history; empty when that rank isn't modeled then (no eras
     * or periods before the Phanerozoic) or before the Earth formed.
     *
     * @param millionYearsAgo the moment, in millions of years ago
     */
    public static Optional<GeologicTimeUnit> at(double millionYearsAgo, GeologicRank rank) {
        if (millionYearsAgo < 0) {
            throw new IllegalArgumentException("the time scale stops at the present");
        }
        return ofRank(rank).stream().filter(unit -> unit.contains(millionYearsAgo)).findFirst();
    }
}
