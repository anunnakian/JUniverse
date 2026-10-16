package com.enlil.universe.world.geoscience;

import com.enlil.universe.geoscience.BoundaryType;
import com.enlil.universe.geoscience.PlateBoundary;
import com.enlil.universe.geoscience.PlateType;
import com.enlil.universe.geoscience.TectonicPlate;

import java.util.List;

/**
 * The Earth's tectonic plates (areas from Bird's 2003 model; typical speeds over the mantle, in mm/year) and their
 * best-known boundaries.
 */
public final class TectonicPlates {

    public static final TectonicPlate PACIFIC = new TectonicPlate("Pacific", 103_300_000, PlateType.OCEANIC, 70);
    public static final TectonicPlate NORTH_AMERICAN =
            new TectonicPlate("North American", 75_900_000, PlateType.MIXED, 15);
    public static final TectonicPlate EURASIAN = new TectonicPlate("Eurasian", 67_800_000, PlateType.MIXED, 10);
    public static final TectonicPlate AFRICAN = new TectonicPlate("African", 61_300_000, PlateType.MIXED, 21);
    public static final TectonicPlate ANTARCTIC = new TectonicPlate("Antarctic", 60_900_000, PlateType.MIXED, 12);
    public static final TectonicPlate INDO_AUSTRALIAN =
            new TectonicPlate("Indo-Australian", 58_900_000, PlateType.MIXED, 67);
    public static final TectonicPlate SOUTH_AMERICAN =
            new TectonicPlate("South American", 43_600_000, PlateType.MIXED, 27);
    /** The largest of the minor plates, sinking under South America. */
    public static final TectonicPlate NAZCA = new TectonicPlate("Nazca", 15_600_000, PlateType.OCEANIC, 76);

    /** The seven major plates. */
    public static final List<TectonicPlate> MAJOR = List.of(
            PACIFIC, NORTH_AMERICAN, EURASIAN, AFRICAN, ANTARCTIC, INDO_AUSTRALIAN, SOUTH_AMERICAN);
    /** Every modeled plate. */
    public static final List<TectonicPlate> ALL = List.of(
            PACIFIC, NORTH_AMERICAN, EURASIAN, AFRICAN, ANTARCTIC, INDO_AUSTRALIAN, SOUTH_AMERICAN, NAZCA);

    /** The Atlantic widening between the Americas and Europe. */
    public static final PlateBoundary MID_ATLANTIC_RIDGE =
            new PlateBoundary("Mid-Atlantic Ridge", NORTH_AMERICAN, EURASIAN, BoundaryType.DIVERGENT, 25);
    public static final PlateBoundary SAN_ANDREAS_FAULT =
            new PlateBoundary("San Andreas Fault", PACIFIC, NORTH_AMERICAN, BoundaryType.TRANSFORM, 46);
    /** India still pushing into Asia, raising the highest mountains. */
    public static final PlateBoundary HIMALAYAS =
            new PlateBoundary("Himalayas", INDO_AUSTRALIAN, EURASIAN, BoundaryType.CONVERGENT, 40);

    private TectonicPlates() {
    }
}
