package com.enlil.universe.geoscience;

/**
 * The layers of the Earth's standard atmosphere, by altitude.
 */
public enum AtmosphericLayer {
    /** Where the weather happens and the air cools with height. */
    TROPOSPHERE(0, 12_000),
    /** Home of the ozone layer; warms with height. */
    STRATOSPHERE(12_000, 50_000),
    /** Where meteors burn up. */
    MESOSPHERE(50_000, 80_000),
    /** Where the aurorae shine and the International Space Station orbits. */
    THERMOSPHERE(80_000, 700_000),
    /** Thinning out into space. */
    EXOSPHERE(700_000, 10_000_000);

    /** The Kármán line, the conventional edge of space, in meters. */
    public static final double KARMAN_LINE_METERS = 100_000;

    private final double bottomMeters;
    private final double topMeters;

    AtmosphericLayer(double bottomMeters, double topMeters) {
        this.bottomMeters = bottomMeters;
        this.topMeters = topMeters;
    }

    /**
     * The layer at a given altitude; a boundary belongs to the layer above it.
     *
     * @param altitudeMeters height above sea level, in meters, between 0 and 10 000 km
     */
    public static AtmosphericLayer of(double altitudeMeters) {
        if (altitudeMeters < 0 || altitudeMeters > EXOSPHERE.topMeters) {
            throw new IllegalArgumentException("altitude must be between 0 and 10 000 km");
        }
        for (AtmosphericLayer layer : values()) {
            if (altitudeMeters < layer.topMeters) {
                return layer;
            }
        }
        return EXOSPHERE;
    }

    /** Altitude of the bottom of this layer, in meters. */
    public double getBottomMeters() {
        return bottomMeters;
    }

    /** Altitude of the top of this layer, in meters. */
    public double getTopMeters() {
        return topMeters;
    }
}
