package com.enlil.universe.world.geoscience;

import com.enlil.universe.geoscience.Earthquake;

/**
 * Real earthquakes, among the strongest and most famous ever recorded (USGS moment magnitudes).
 */
public final class Earthquakes {

    /** The strongest earthquake ever recorded. */
    public static final Earthquake VALDIVIA_1960 = new Earthquake("Valdivia", 1960, 9.5);
    public static final Earthquake ALASKA_1964 = new Earthquake("Great Alaska", 1964, 9.2);
    public static final Earthquake SUMATRA_2004 = new Earthquake("Sumatra–Andaman", 2004, 9.1);
    public static final Earthquake TOHOKU_2011 = new Earthquake("Tōhoku", 2011, 9.1);
    public static final Earthquake SAN_FRANCISCO_1906 =
            new Earthquake("San Francisco", 1906, 7.9, TectonicPlates.SAN_ANDREAS_FAULT);
    /** Estimated from historical accounts; it inspired Voltaire's Candide. */
    public static final Earthquake LISBON_1755 = new Earthquake("Lisbon", 1755, 8.5);

    private Earthquakes() {
    }
}
