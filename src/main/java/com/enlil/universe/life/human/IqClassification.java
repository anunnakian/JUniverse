package com.enlil.universe.life.human;

/**
 * Descriptive IQ bands of the Wechsler Adult Intelligence Scale (WAIS-IV).
 */
public enum IqClassification {
    EXTREMELY_LOW,
    BORDERLINE,
    LOW_AVERAGE,
    AVERAGE,
    HIGH_AVERAGE,
    SUPERIOR,
    VERY_SUPERIOR;

    /** Band of the given full-scale IQ. */
    public static IqClassification of(int iq) {
        if (iq < 70) {
            return EXTREMELY_LOW;
        }
        if (iq < 80) {
            return BORDERLINE;
        }
        if (iq < 90) {
            return LOW_AVERAGE;
        }
        if (iq < 110) {
            return AVERAGE;
        }
        if (iq < 120) {
            return HIGH_AVERAGE;
        }
        if (iq < 130) {
            return SUPERIOR;
        }
        return VERY_SUPERIOR;
    }
}
