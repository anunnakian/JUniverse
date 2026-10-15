package com.enlil.universe.life.human.language;

import lombok.Getter;

import java.util.Locale;

/** Words spoken aloud in a language, taking time at a conversational pace. */
@Getter
public class Speech {

    /** Average conversational speaking rate, in words per minute (about 150 in English). */
    public static final double WORDS_PER_MINUTE = 150;

    private final Language language;
    private final int words;

    public Speech(Language language, int words) {
        if (words <= 0) {
            throw new IllegalArgumentException("a speech has at least one word");
        }
        this.language = language;
        this.words = words;
    }

    /** How long saying these words takes, in minutes. */
    public double durationMinutes() {
        return words / WORDS_PER_MINUTE;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%d words of %s, %.1f min", words, language, durationMinutes());
    }
}
