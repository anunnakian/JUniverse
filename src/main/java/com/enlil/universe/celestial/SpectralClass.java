package com.enlil.universe.celestial;

/**
 * Morgan–Keenan spectral classes, from the hottest to the coolest star.
 */
public enum SpectralClass {
    O(30_000),
    B(10_000),
    A(7_500),
    F(6_000),
    G(5_200),
    K(3_700),
    M(0);

    private final double minTemperatureKelvin;

    SpectralClass(double minTemperatureKelvin) {
        this.minTemperatureKelvin = minTemperatureKelvin;
    }

    public static SpectralClass fromTemperature(double temperatureKelvin) {
        for (SpectralClass spectralClass : values()) {
            if (temperatureKelvin >= spectralClass.minTemperatureKelvin) {
                return spectralClass;
            }
        }
        throw new IllegalArgumentException("temperature must be positive");
    }
}
