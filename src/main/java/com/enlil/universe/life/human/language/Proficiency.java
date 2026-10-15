package com.enlil.universe.life.human.language;

/**
 * How well a language is mastered, on the Common European Framework of Reference (CEFR) scale, reached after the
 * guided learning hours estimated by Cambridge English for each level, plus the mother tongue above them all.
 */
public enum Proficiency {
    /** A few words, not yet able to hold the simplest exchange. */
    PRE_A1(0),
    /** Familiar everyday expressions and very basic phrases. */
    A1(90),
    /** Simple exchanges about familiar, routine matters. */
    A2(180),
    /** The main points of clear speech on familiar matters; travel. */
    B1(350),
    /** Fluent, spontaneous exchanges with native speakers, without strain. */
    B2(500),
    /** Flexible, effective use for social, academic and professional purposes. */
    C1(700),
    /** Understanding virtually everything heard or read, effortlessly. */
    C2(1_000),
    /** The mother tongue, acquired in childhood rather than studied. */
    NATIVE(Double.POSITIVE_INFINITY);

    private final double guidedLearningHours;

    Proficiency(double guidedLearningHours) {
        this.guidedLearningHours = guidedLearningHours;
    }

    /** The level reached after the given hours of guided learning; never {@link #NATIVE}. */
    public static Proficiency ofHours(double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("hours must not be negative");
        }
        Proficiency level = PRE_A1;
        for (Proficiency proficiency : values()) {
            if (proficiency != NATIVE && hours >= proficiency.guidedLearningHours) {
                level = proficiency;
            }
        }
        return level;
    }

    /** Guided learning hours needed to reach this level from scratch; infinite for a mother tongue. */
    public double getGuidedLearningHours() {
        return guidedLearningHours;
    }

    /** Whether this level is enough to hold a conversation, however simple: A1 and above. */
    public boolean canSpeak() {
        return compareTo(A1) >= 0;
    }
}
