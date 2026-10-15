package com.enlil.universe.life.human;

import lombok.Getter;

/**
 * A Big Five personality profile. Each trait is scored as a population percentile, from 0 to 100, so 50 is average.
 */
@Getter
public class Personality {

    private final double openness;
    private final double conscientiousness;
    private final double extraversion;
    private final double agreeableness;
    private final double neuroticism;

    public Personality(double openness, double conscientiousness, double extraversion,
                       double agreeableness, double neuroticism) {
        this.openness = percentile(openness);
        this.conscientiousness = percentile(conscientiousness);
        this.extraversion = percentile(extraversion);
        this.agreeableness = percentile(agreeableness);
        this.neuroticism = percentile(neuroticism);
    }

    private static double percentile(double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("trait scores are percentiles between 0 and 100");
        }
        return score;
    }

    /** Percentile score of the given trait, from 0 to 100. */
    public double score(PersonalityTrait trait) {
        return switch (trait) {
            case OPENNESS -> openness;
            case CONSCIENTIOUSNESS -> conscientiousness;
            case EXTRAVERSION -> extraversion;
            case AGREEABLENESS -> agreeableness;
            case NEUROTICISM -> neuroticism;
        };
    }

    /** Whether the given trait scores higher than the population median. */
    public boolean isAboveAverage(PersonalityTrait trait) {
        return score(trait) > 50;
    }
}
