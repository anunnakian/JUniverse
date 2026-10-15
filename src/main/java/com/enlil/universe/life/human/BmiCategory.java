package com.enlil.universe.life.human;

/**
 * WHO adult body mass index categories.
 */
public enum BmiCategory {
    UNDERWEIGHT,
    NORMAL,
    OVERWEIGHT,
    OBESE;

    /** Category of the given body mass index (in kg/m²). */
    public static BmiCategory of(double bmi) {
        if (bmi < 18.5) {
            return UNDERWEIGHT;
        }
        if (bmi < 25) {
            return NORMAL;
        }
        if (bmi < 30) {
            return OVERWEIGHT;
        }
        return OBESE;
    }
}
