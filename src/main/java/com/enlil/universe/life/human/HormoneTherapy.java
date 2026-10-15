package com.enlil.universe.life.human;

import lombok.Getter;

import java.util.Objects;

/**
 * Gender-affirming hormone therapy: feminizing (estradiol, usually with an anti-androgen) or masculinizing
 * (testosterone). Hemoglobin shifts within months, body fat and muscle over one to two years; this model holds the
 * therapy as established, and its hormones as those that drive the body, after {@value #ESTABLISHED_AFTER_YEARS}
 * year.
 */
@Getter
public class HormoneTherapy {

    public static final double ESTABLISHED_AFTER_YEARS = 1;

    public enum Kind {
        FEMINIZING,
        MASCULINIZING
    }

    private final Kind kind;
    private final double years;

    public HormoneTherapy(Kind kind, double years) {
        if (years < 0) {
            throw new IllegalArgumentException("years on therapy must not be negative");
        }
        this.kind = Objects.requireNonNull(kind, "kind");
        this.years = years;
    }

    public boolean isEstablished() {
        return years >= ESTABLISHED_AFTER_YEARS;
    }

    /** The sex whose hormones this therapy gives the body. */
    public Sex getHormonalSex() {
        return kind == Kind.FEMINIZING ? Sex.FEMALE : Sex.MALE;
    }
}
