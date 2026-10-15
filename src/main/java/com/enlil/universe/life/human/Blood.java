package com.enlil.universe.life.human;

import lombok.Getter;

/**
 * A human's blood: its genotype, the type it expresses (ABO group and Rh factor) and its total volume, estimated from
 * the body. Volume and hemoglobin follow the body's hormonal sex, so they shift with gender-affirming hormones.
 */
@Getter
public class Blood {

    private final BloodGenotype genotype;
    private final AboGroup aboGroup;
    private final RhFactor rhFactor;
    /** Total blood volume, in liters (Nadler, 1962). */
    private final double volumeLiters;
    /** Hemoglobin concentration below which an adult is anemic, in g/L (WHO, 2011): 130 for men, 120 for women. */
    private final double anemiaThresholdGramsPerLiter;

    public Blood(Body body, BloodGenotype genotype) {
        this.genotype = genotype;
        this.aboGroup = genotype.aboGroup();
        this.rhFactor = genotype.rhFactor();
        this.volumeLiters = nadlerVolumeLiters(body);
        this.anemiaThresholdGramsPerLiter = body.getHormonalSex() == Sex.MALE ? 130 : 120;
    }

    private static double nadlerVolumeLiters(Body body) {
        double heightCubed = Math.pow(body.getHeightMeters(), 3);
        return body.getHormonalSex() == Sex.MALE
                ? 0.366_9 * heightCubed + 0.032_19 * body.getMassKg() + 0.604_1
                : 0.356_1 * heightCubed + 0.033_08 * body.getMassKg() + 0.183_3;
    }

    /** Whether the given hemoglobin concentration, in g/L, means anemia for this blood. */
    public boolean isAnemic(double hemoglobinGramsPerLiter) {
        return hemoglobinGramsPerLiter < anemiaThresholdGramsPerLiter;
    }

    /** Whether this blood's red cells can be transfused to the recipient without an ABO or RhD reaction. */
    public boolean canDonateTo(Blood recipient) {
        return aboGroup.canDonateTo(recipient.aboGroup) && rhFactor.canDonateTo(recipient.rhFactor);
    }

    public boolean canReceiveFrom(Blood donor) {
        return donor.canDonateTo(this);
    }
}
