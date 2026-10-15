package com.enlil.universe.life.human;

import lombok.Getter;

/**
 * Major human organs, with their ICRP Publication 89 reference adult masses.
 */
@Getter
public enum Organ {
    BRAIN(1.450, 1.300),
    HEART(0.330, 0.250),
    LIVER(1.800, 1.400),
    LUNGS(1.200, 0.950),
    /** Both kidneys together. */
    KIDNEYS(0.310, 0.275),
    SPLEEN(0.150, 0.130),
    PANCREAS(0.140, 0.120),
    STOMACH(0.150, 0.140),
    THYROID(0.020, 0.017),
    SKIN(3.300, 2.300);

    private final double referenceMaleMassKg;
    private final double referenceFemaleMassKg;

    Organ(double referenceMaleMassKg, double referenceFemaleMassKg) {
        this.referenceMaleMassKg = referenceMaleMassKg;
        this.referenceFemaleMassKg = referenceFemaleMassKg;
    }

    /** ICRP 89 reference adult mass for the given sex, in kg. */
    public double referenceMassKg(Sex sex) {
        return sex == Sex.MALE ? referenceMaleMassKg : referenceFemaleMassKg;
    }
}
