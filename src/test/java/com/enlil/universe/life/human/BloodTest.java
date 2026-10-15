package com.enlil.universe.life.human;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BloodTest {

    private static final Body REFERENCE_MAN = new Body(Sex.MALE, 30, 1.76, 73);
    private static final Body REFERENCE_WOMAN = new Body(Sex.FEMALE, 30, 1.63, 60);

    @Test
    @DisplayName("the ICRP reference man (1.76 m, 73 kg) should have about 4.95 L of blood (Nadler)")
    void blood_volume_male() {
        Blood blood = new Blood(REFERENCE_MAN, genotype(AboGroup.O, RhFactor.POSITIVE));

        Assertions.assertEquals(4.95, blood.getVolumeLiters(), 0.01);
    }

    @Test
    @DisplayName("the ICRP reference woman (1.63 m, 60 kg) should have about 3.71 L of blood (Nadler)")
    void blood_volume_female() {
        Blood blood = new Blood(REFERENCE_WOMAN, genotype(AboGroup.A, RhFactor.NEGATIVE));

        Assertions.assertEquals(3.71, blood.getVolumeLiters(), 0.01);
    }

    @ParameterizedTest(name = "{0} {1} giving to {2} {3}: {4}")
    @CsvSource({
            "O, NEGATIVE, AB, POSITIVE, true",
            "O, NEGATIVE, A, NEGATIVE, true",
            "O, POSITIVE, O, NEGATIVE, false",
            "O, POSITIVE, B, POSITIVE, true",
            "A, NEGATIVE, A, POSITIVE, true",
            "A, NEGATIVE, AB, NEGATIVE, true",
            "A, POSITIVE, B, POSITIVE, false",
            "A, POSITIVE, O, POSITIVE, false",
            "B, NEGATIVE, AB, POSITIVE, true",
            "B, POSITIVE, A, POSITIVE, false",
            "AB, NEGATIVE, AB, POSITIVE, true",
            "AB, POSITIVE, O, POSITIVE, false",
            "AB, POSITIVE, AB, NEGATIVE, false"})
    @DisplayName("red cells should only go to recipients carrying all of the donor's antigens")
    void transfusion_compatibility(AboGroup donorGroup, RhFactor donorRh,
                                   AboGroup recipientGroup, RhFactor recipientRh, boolean compatible) {
        Blood donor = new Blood(REFERENCE_MAN, genotype(donorGroup, donorRh));
        Blood recipient = new Blood(REFERENCE_WOMAN, genotype(recipientGroup, recipientRh));

        Assertions.assertEquals(compatible, donor.canDonateTo(recipient));
        Assertions.assertEquals(compatible, recipient.canReceiveFrom(donor));
    }

    @Test
    @DisplayName("O- should be the universal donor and AB+ the universal recipient")
    void universal_donor_and_recipient() {
        Blood universalDonor = new Blood(REFERENCE_MAN, genotype(AboGroup.O, RhFactor.NEGATIVE));
        Blood universalRecipient = new Blood(REFERENCE_MAN, genotype(AboGroup.AB, RhFactor.POSITIVE));

        for (AboGroup group : AboGroup.values()) {
            for (RhFactor rh : RhFactor.values()) {
                Blood other = new Blood(REFERENCE_WOMAN, genotype(group, rh));
                Assertions.assertTrue(universalDonor.canDonateTo(other));
                Assertions.assertTrue(universalRecipient.canReceiveFrom(other));
            }
        }
    }

    /** A genotype expressing the given blood type; homozygous where there is a choice. */
    private static BloodGenotype genotype(AboGroup group, RhFactor rhFactor) {
        AboAllele first = group == AboGroup.B ? AboAllele.B : group == AboGroup.O ? AboAllele.O : AboAllele.A;
        AboAllele second = group == AboGroup.A ? AboAllele.A : group == AboGroup.O ? AboAllele.O : AboAllele.B;
        RhAllele rh = rhFactor == RhFactor.POSITIVE ? RhAllele.D : RhAllele.d;
        return new BloodGenotype(first, second, rh, rh);
    }

    @Test
    @DisplayName("a trans man on testosterone should be held to the male anemia threshold, 130 g/L")
    void anemia_follows_hormones() {
        BloodGenotype genotype = new BloodGenotype(AboAllele.O, AboAllele.O, RhAllele.D, RhAllele.D);
        Body transMan = new Body(Sex.FEMALE, 30, 1.68, 62, null,
                new HormoneTherapy(HormoneTherapy.Kind.MASCULINIZING, 3));
        Blood blood = new Blood(transMan, genotype);

        Assertions.assertEquals(130, blood.getAnemiaThresholdGramsPerLiter());
        Assertions.assertTrue(blood.isAnemic(125));
        Assertions.assertFalse(new Blood(new Body(Sex.FEMALE, 30, 1.68, 62), genotype).isAnemic(125));
        Assertions.assertTrue(blood.getVolumeLiters()
                > new Blood(new Body(Sex.FEMALE, 30, 1.68, 62), genotype).getVolumeLiters());
    }
}
