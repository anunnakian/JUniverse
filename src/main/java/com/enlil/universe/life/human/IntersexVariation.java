package com.enlil.universe.life.human;

/**
 * Variations of sex characteristics (chromosomes, gonads, hormones or anatomy) that do not fit the typical female or
 * male body. Whether the chromosomal ones, Klinefelter's and Turner's, count as intersex is debated; intersex
 * organizations usually include them. The sex assigned at birth varies with each person, so it is not checked
 * against the variation.
 */
public enum IntersexVariation {
    /** An extra X chromosome, often with low testosterone; about 1 in 600 male births. */
    KLINEFELTER_SYNDROME(Karyotype.XXY, Sex.MALE, 600),
    /** A single X chromosome, with ovaries that stop working early; about 1 in 2,500 female births. */
    TURNER_SYNDROME(Karyotype.X, Sex.FEMALE, 2_500),
    /** XY chromosomes and testes, but a body that does not respond to androgens; about 1 in 20,000 XY births. */
    COMPLETE_ANDROGEN_INSENSITIVITY(Karyotype.XY, Sex.FEMALE, 20_000),
    /** XX chromosomes with androgens in excess from the adrenal glands; classic forms in about 1 in 15,000 births. */
    CONGENITAL_ADRENAL_HYPERPLASIA(Karyotype.XX, Sex.FEMALE, 15_000);

    private final Karyotype karyotype;
    private final Sex usualSexAssignedAtBirth;
    private final int birthsPerCase;

    IntersexVariation(Karyotype karyotype, Sex usualSexAssignedAtBirth, int birthsPerCase) {
        this.karyotype = karyotype;
        this.usualSexAssignedAtBirth = usualSexAssignedAtBirth;
        this.birthsPerCase = birthsPerCase;
    }

    public Karyotype getKaryotype() {
        return karyotype;
    }

    public Sex getUsualSexAssignedAtBirth() {
        return usualSexAssignedAtBirth;
    }

    /** About one case in this many births of the usual sex assigned (all births for adrenal hyperplasia). */
    public int getBirthsPerCase() {
        return birthsPerCase;
    }
}
