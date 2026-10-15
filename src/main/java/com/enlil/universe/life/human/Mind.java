package com.enlil.universe.life.human;

import com.enlil.universe.life.human.activity.Activity;
import lombok.Getter;

import java.util.Set;

/**
 * A human's mind: their Big Five personality, their intelligence quotient and their hobbies.
 */
@Getter
public class Mind {

    /** IQ scales are normed to a population mean of 100 and a standard deviation of 15. */
    public static final double IQ_MEAN = 100;
    public static final double IQ_STANDARD_DEVIATION = 15;

    private final Personality personality;
    private final int iq;
    /** The activities this human enjoys in their free time. */
    private final Set<Activity> hobbies;

    public Mind(Personality personality, int iq, Set<Activity> hobbies) {
        if (iq <= 0) {
            throw new IllegalArgumentException("IQ must be positive");
        }
        this.personality = personality;
        this.iq = iq;
        this.hobbies = Set.copyOf(hobbies);
    }

    /** Share of the population scoring below this IQ, in %. */
    public double iqPercentile() {
        return 100 * standardNormalCdf((iq - IQ_MEAN) / IQ_STANDARD_DEVIATION);
    }

    public IqClassification iqClassification() {
        return IqClassification.of(iq);
    }

    /** Standard normal cumulative distribution, through the error function. */
    static double standardNormalCdf(double z) {
        return 0.5 * (1 + erf(z / Math.sqrt(2)));
    }

    /** Error function (Abramowitz &amp; Stegun 7.1.26, absolute error below 1.5e-7). */
    private static double erf(double x) {
        double t = 1 / (1 + 0.327_591_1 * Math.abs(x));
        double polynomial = t * (0.254_829_592 + t * (-0.284_496_736 + t * (1.421_413_741
                + t * (-1.453_152_027 + t * 1.061_405_429))));
        double y = 1 - polynomial * Math.exp(-x * x);
        return Math.signum(x) * y;
    }
}
