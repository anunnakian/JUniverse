package com.enlil.universe.life.human;

/**
 * The sex assigned at birth, which selects the sex-specific physiological formulas and reference values. It says
 * nothing of gender identity, held apart in an {@link com.enlil.universe.life.human.identity.Identity}. A
 * {@link Body} may also have an {@link IntersexVariation}, and a {@link HormoneTherapy} that changes its hormonal sex.
 */
public enum Sex {
    MALE,
    FEMALE
}
