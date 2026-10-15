package com.enlil.universe.life.human.identity;

/**
 * A person's inner sense of their own gender, which may or may not match the sex they were assigned at birth.
 * Non-binary is an umbrella for genders outside the woman–man binary, genderfluid and agender among them.
 */
public enum GenderIdentity {
    WOMAN,
    MAN,
    NON_BINARY,
    /** A gender that changes over time. */
    GENDERFLUID,
    /** No gender at all. */
    AGENDER;

    /** Whether this identity lies outside the woman–man binary. */
    public boolean isNonBinary() {
        return this != WOMAN && this != MAN;
    }
}
