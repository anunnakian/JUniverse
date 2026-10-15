package com.enlil.universe.life.human.identity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The pronouns a person goes by, in their subject, object and possessive forms: she/her/her, he/him/his,
 * they/them/their, or neopronouns like xe/xem/xyr. Singular "they" has been used in English since the 14th century.
 */
@Getter
@EqualsAndHashCode
public class Pronouns {

    public static final Pronouns SHE_HER = new Pronouns("she", "her", "her");
    public static final Pronouns HE_HIM = new Pronouns("he", "him", "his");
    public static final Pronouns THEY_THEM = new Pronouns("they", "them", "their");

    private final String subject;
    private final String object;
    private final String possessive;

    public Pronouns(String subject, String object, String possessive) {
        if (isBlank(subject) || isBlank(object) || isBlank(possessive)) {
            throw new IllegalArgumentException("pronouns have a subject, an object and a possessive form");
        }
        this.subject = subject;
        this.object = object;
        this.possessive = possessive;
    }

    private static boolean isBlank(String form) {
        return form == null || form.isBlank();
    }

    @Override
    public String toString() {
        return subject + "/" + object;
    }
}
