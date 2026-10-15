package com.enlil.universe.life.human.language;

import com.enlil.universe.world.language.Languages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LanguageSkillTest {

    @ParameterizedTest(name = "{0} guided hours should reach {1}")
    @CsvSource({"0, PRE_A1", "89, PRE_A1", "90, A1", "200, A2", "400, B1", "550, B2", "750, C1", "1200, C2"})
    @DisplayName("guided learning hours should reach the CEFR levels")
    void levels(double hours, Proficiency expected) {
        Assertions.assertEquals(expected, LanguageSkill.studied(Languages.SPANISH, hours).getProficiency());
    }

    @Test
    @DisplayName("from B1, 150 more hours should reach B2, and only A1 and above can speak")
    void progress() {
        LanguageSkill skill = LanguageSkill.studied(Languages.ENGLISH, 350);

        Assertions.assertEquals(150, skill.hoursTo(Proficiency.B2), 1e-9);
        Assertions.assertEquals(Proficiency.B2, skill.afterStudying(150).getProficiency());
        Assertions.assertEquals(0, skill.hoursTo(Proficiency.A2));
        Assertions.assertFalse(Proficiency.PRE_A1.canSpeak());
        Assertions.assertTrue(Proficiency.A1.canSpeak());
        Assertions.assertTrue(Proficiency.NATIVE.canSpeak());
    }

    @Test
    @DisplayName("a mother tongue should stay native whatever the study")
    void native_tongue() {
        LanguageSkill french = LanguageSkill.nativeTongue(Languages.FRENCH);

        Assertions.assertEquals(Proficiency.NATIVE, french.afterStudying(10).getProficiency());
        Assertions.assertEquals(0, french.hoursTo(Proficiency.C2));
    }

    @Test
    @DisplayName("Latin should be learned but no longer native to anyone")
    void latin() {
        Assertions.assertFalse(Languages.LATIN.isLiving());
        Assertions.assertTrue(Languages.FRENCH.isLiving());
        Assertions.assertEquals(LanguageFamily.INDO_EUROPEAN, Languages.LATIN.getFamily());
        Assertions.assertEquals(LanguageFamily.SINO_TIBETAN, Languages.MANDARIN.getFamily());
    }

    @Test
    @DisplayName("a speech of 150 words should last a minute")
    void speech() {
        Assertions.assertEquals(1, new Speech(Languages.ENGLISH, 150).durationMinutes(), 1e-9);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Speech(Languages.ENGLISH, 0));
    }

    @Test
    @DisplayName("a language should have a two-letter ISO code and no negative speakers")
    void rejects_invalid_languages() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Language("Klingon", "tlh", LanguageFamily.ISOLATE, 0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Language("Nothing", "nn", LanguageFamily.ISOLATE, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> LanguageSkill.studied(Languages.LATIN, -1));
    }
}
