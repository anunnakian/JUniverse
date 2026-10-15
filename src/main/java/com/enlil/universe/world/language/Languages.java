package com.enlil.universe.world.language;

import com.enlil.universe.life.human.language.Language;
import com.enlil.universe.life.human.language.LanguageFamily;

/** Real languages, with their native speakers from Ethnologue (2023), rounded. */
public final class Languages {

    public static final Language MANDARIN = new Language("Mandarin", "zh", LanguageFamily.SINO_TIBETAN, 940_000_000L);
    public static final Language SPANISH = new Language("Spanish", "es", LanguageFamily.INDO_EUROPEAN, 485_000_000L);
    public static final Language ENGLISH = new Language("English", "en", LanguageFamily.INDO_EUROPEAN, 380_000_000L);
    public static final Language HINDI = new Language("Hindi", "hi", LanguageFamily.INDO_EUROPEAN, 345_000_000L);
    public static final Language ARABIC = new Language("Arabic", "ar", LanguageFamily.AFRO_ASIATIC, 360_000_000L);
    public static final Language JAPANESE = new Language("Japanese", "ja", LanguageFamily.JAPONIC, 123_000_000L);
    public static final Language FRENCH = new Language("French", "fr", LanguageFamily.INDO_EUROPEAN, 74_000_000L);
    /** The language of Rome, with no native speaker left, still learned, and the official language of Vatican City. */
    public static final Language LATIN = new Language("Latin", "la", LanguageFamily.INDO_EUROPEAN, 0);

    private Languages() {
    }
}
