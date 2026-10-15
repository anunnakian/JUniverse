package com.enlil.universe.life.human.language;

import lombok.Getter;

/** A human language, with its ISO 639-1 code, its family and how many people speak it as their mother tongue. */
@Getter
public class Language {

    private final String name;
    private final String isoCode;
    private final LanguageFamily family;
    private final long nativeSpeakers;

    public Language(String name, String isoCode, LanguageFamily family, long nativeSpeakers) {
        if (isoCode == null || !isoCode.matches("[a-z]{2}")) {
            throw new IllegalArgumentException("an ISO 639-1 code has two lowercase letters: " + isoCode);
        }
        if (nativeSpeakers < 0) {
            throw new IllegalArgumentException("native speakers must not be negative");
        }
        this.name = name;
        this.isoCode = isoCode;
        this.family = family;
        this.nativeSpeakers = nativeSpeakers;
    }

    /** Whether someone still learns this language from birth; Latin, for one, is only learned. */
    public boolean isLiving() {
        return nativeSpeakers > 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
