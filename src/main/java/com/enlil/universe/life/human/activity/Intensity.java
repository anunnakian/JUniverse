package com.enlil.universe.life.human.activity;

/**
 * How hard an activity is, by its metabolic equivalent, after the WHO guidelines on physical activity (2020), which
 * advise adults 150 to 300 minutes of moderate activity a week, or 75 to 150 of vigorous activity.
 */
public enum Intensity {
    /** Below 1.5 MET: sitting or lying. */
    SEDENTARY(1),
    /** 1.5 to 3 MET. */
    LIGHT(1.5),
    /** 3 to 6 MET: faster breathing, still able to talk. */
    MODERATE(3),
    /** 6 MET and more: out of breath. */
    VIGOROUS(6);

    private final double minimumMetabolicEquivalent;

    Intensity(double minimumMetabolicEquivalent) {
        this.minimumMetabolicEquivalent = minimumMetabolicEquivalent;
    }

    public static Intensity of(double metabolicEquivalent) {
        Intensity[] intensities = values();
        for (int i = intensities.length - 1; i > 0; i--) {
            if (metabolicEquivalent >= intensities[i].minimumMetabolicEquivalent) {
                return intensities[i];
            }
        }
        return SEDENTARY;
    }

    public double getMinimumMetabolicEquivalent() {
        return minimumMetabolicEquivalent;
    }
}
