package com.enlil.universe.life.human.identity;

/**
 * Whom a person is sexually attracted to, as they name it themselves: labels are self-identified, so they are not
 * derived from, nor checked against, anyone's gender.
 */
public enum SexualOrientation {
    /** Attracted to another gender than one's own. */
    HETEROSEXUAL,
    /** Attracted to the same gender; used by men especially, and by people of any gender. */
    GAY,
    /** A woman, or a non-binary person, attracted to women. */
    LESBIAN,
    /** Attracted to more than one gender; the most common identity among LGBTQ+ adults. */
    BISEXUAL,
    /** Attracted to people regardless of their gender. */
    PANSEXUAL,
    /** Experiencing little or no sexual attraction. */
    ASEXUAL,
    /** An umbrella term for orientations outside heterosexuality, reclaimed from an insult. */
    QUEER;

    /** Whether this orientation belongs to the LGBTQ+ community: every one but heterosexual. */
    public boolean isLgbtq() {
        return this != HETEROSEXUAL;
    }
}
