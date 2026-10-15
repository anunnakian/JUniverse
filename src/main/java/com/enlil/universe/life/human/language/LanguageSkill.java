package com.enlil.universe.life.human.language;

import lombok.Getter;

import java.util.Objects;

/** A language someone knows: their mother tongue, or one they studied for a number of hours. */
@Getter
public class LanguageSkill {

    private final Language language;
    private final boolean nativeTongue;
    private final double hoursStudied;

    private LanguageSkill(Language language, boolean nativeTongue, double hoursStudied) {
        if (hoursStudied < 0) {
            throw new IllegalArgumentException("hours must not be negative");
        }
        this.language = Objects.requireNonNull(language, "language");
        this.nativeTongue = nativeTongue;
        this.hoursStudied = hoursStudied;
    }

    public static LanguageSkill nativeTongue(Language language) {
        return new LanguageSkill(language, true, 0);
    }

    public static LanguageSkill studied(Language language, double hours) {
        return new LanguageSkill(language, false, hours);
    }

    public Proficiency getProficiency() {
        return nativeTongue ? Proficiency.NATIVE : Proficiency.ofHours(hoursStudied);
    }

    /** This skill after more hours of study; a mother tongue stays a mother tongue. */
    public LanguageSkill afterStudying(double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("hours must not be negative");
        }
        return new LanguageSkill(language, nativeTongue, hoursStudied + hours);
    }

    /** Guided hours still needed to reach the given level; zero if already there. */
    public double hoursTo(Proficiency target) {
        if (getProficiency().compareTo(target) >= 0) {
            return 0;
        }
        return target.getGuidedLearningHours() - hoursStudied;
    }

    @Override
    public String toString() {
        return language + " (" + getProficiency() + ")";
    }
}
