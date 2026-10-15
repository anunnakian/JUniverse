package com.enlil.universe.civilization;

/**
 * The Kardashev scale, measuring a civilization by the power it harnesses: that of its planet, its star, then its
 * galaxy. Thresholds follow Sagan's interpolation (10^16, 10^26 and 10^36 W) rather than Kardashev's original 1964
 * figures. Type 0 is everything below a type I, humanity included.
 */
public enum KardashevType {
    TYPE_0(0),
    TYPE_I(1e16),
    TYPE_II(1e26),
    TYPE_III(1e36);

    private final double minimumPowerWatts;

    KardashevType(double minimumPowerWatts) {
        this.minimumPowerWatts = minimumPowerWatts;
    }

    public static KardashevType of(double powerWatts) {
        if (powerWatts <= 0) {
            throw new IllegalArgumentException("power must be positive");
        }
        KardashevType[] types = values();
        for (int i = types.length - 1; i > 0; i--) {
            if (powerWatts >= types[i].minimumPowerWatts) {
                return types[i];
            }
        }
        return TYPE_0;
    }

    /** Power a civilization must harness to reach this type, in watts. */
    public double getMinimumPowerWatts() {
        return minimumPowerWatts;
    }
}
