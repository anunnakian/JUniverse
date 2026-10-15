package com.enlil.universe.life.human;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;

/**
 * An adult human body, described by its sex assigned at birth, age, height and mass, from which the anthropometric
 * quantities are derived with standard clinical formulas. A body may have an intersex variation, and may be on
 * gender-affirming hormone therapy: then the quantities driven by sex hormones (metabolism, blood) follow its
 * {@linkplain #getHormonalSex() hormonal sex}, while those set by the skeleton (lung volumes) stay with the sex
 * assigned at birth, as clinicians commonly do.
 */
@Getter
public class Body {

    /** The formulas used here (Mifflin-St Jeor, Nadler, ICRP organ masses) are adult references. */
    public static final int ADULT_AGE_YEARS = 18;

    private final Sex sex;
    private final int ageYears;
    private final double heightMeters;
    private final double massKg;
    /** Null for a body without intersex variation. */
    @Getter(AccessLevel.NONE)
    private final IntersexVariation intersexVariation;
    /** Null for a body on no gender-affirming hormone therapy. */
    @Getter(AccessLevel.NONE)
    private final HormoneTherapy hormoneTherapy;

    public Body(Sex sex, int ageYears, double heightMeters, double massKg) {
        this(sex, ageYears, heightMeters, massKg, null, null);
    }

    /**
     * @param intersexVariation the body's intersex variation, or {@code null} for none
     * @param hormoneTherapy its gender-affirming hormone therapy, or {@code null} for none
     */
    public Body(Sex sex, int ageYears, double heightMeters, double massKg, IntersexVariation intersexVariation,
                HormoneTherapy hormoneTherapy) {
        if (ageYears < ADULT_AGE_YEARS) {
            throw new IllegalArgumentException("only adult bodies are modeled");
        }
        if (heightMeters <= 0) {
            throw new IllegalArgumentException("height must be positive");
        }
        if (massKg <= 0) {
            throw new IllegalArgumentException("mass must be positive");
        }
        this.sex = sex;
        this.ageYears = ageYears;
        this.heightMeters = heightMeters;
        this.massKg = massKg;
        this.intersexVariation = intersexVariation;
        this.hormoneTherapy = hormoneTherapy;
    }

    public Optional<IntersexVariation> getIntersexVariation() {
        return Optional.ofNullable(intersexVariation);
    }

    public boolean isIntersex() {
        return intersexVariation != null;
    }

    public Optional<HormoneTherapy> getHormoneTherapy() {
        return Optional.ofNullable(hormoneTherapy);
    }

    /** The sex chromosomes: those of the intersex variation if any, else the usual ones of the sex assigned. */
    public Karyotype getKaryotype() {
        if (intersexVariation != null) {
            return intersexVariation.getKaryotype();
        }
        return sex == Sex.FEMALE ? Karyotype.XX : Karyotype.XY;
    }

    /** The sex whose hormones drive this body: that of an established hormone therapy, else the sex assigned. */
    public Sex getHormonalSex() {
        return hormoneTherapy != null && hormoneTherapy.isEstablished() ? hormoneTherapy.getHormonalSex() : sex;
    }

    /** Body mass index, in kg/m². */
    public double bmi() {
        return massKg / (heightMeters * heightMeters);
    }

    public BmiCategory bmiCategory() {
        return BmiCategory.of(bmi());
    }

    /** Resting energy expenditure, in kcal per day (Mifflin-St Jeor, 1990). */
    public double basalMetabolicRateKcalPerDay() {
        double base = 10 * massKg + 6.25 * heightMeters * 100 - 5 * ageYears;
        return getHormonalSex() == Sex.MALE ? base + 5 : base - 161;
    }

    /** Body surface area, in m² (Mosteller, 1987). */
    public double bodySurfaceAreaSquareMeters() {
        return Math.sqrt(heightMeters * 100 * massKg / 3_600);
    }

    /** Maximum heart rate, in beats per minute (Tanaka, Monahan &amp; Seals, 2001). */
    public double maxHeartRateBpm() {
        return 208 - 0.7 * ageYears;
    }

    /**
     * Predicted forced vital capacity, in liters (ERS/ECSC reference equations, Quanjer 1993). The equations were
     * fitted on adults aged 18 to 70: beyond 70 they are extrapolated.
     */
    public double vitalCapacityLiters() {
        return sex == Sex.MALE
                ? 5.76 * heightMeters - 0.026 * ageYears - 4.34
                : 4.43 * heightMeters - 0.026 * ageYears - 2.89;
    }
}
