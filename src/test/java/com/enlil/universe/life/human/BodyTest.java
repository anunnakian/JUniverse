package com.enlil.universe.life.human;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BodyTest {

    @Test
    @DisplayName("a 1.75 m, 70 kg adult should have a normal BMI of about 22.9")
    void bmi() {
        Body body = new Body(Sex.MALE, 30, 1.75, 70);

        Assertions.assertEquals(22.86, body.bmi(), 0.01);
        Assertions.assertEquals(BmiCategory.NORMAL, body.bmiCategory());
    }

    @ParameterizedTest(name = "a BMI of {0} should be {1}")
    @CsvSource({"18.4, UNDERWEIGHT", "18.5, NORMAL", "24.9, NORMAL", "25.0, OVERWEIGHT", "29.9, OVERWEIGHT", "30.0, OBESE"})
    @DisplayName("BMI categories should follow the WHO adult cut-offs")
    void bmi_categories(double bmi, BmiCategory category) {
        Assertions.assertEquals(category, BmiCategory.of(bmi));
    }

    @Test
    @DisplayName("a 25-year-old, 1.80 m, 80 kg man should burn 1805 kcal/day at rest (Mifflin-St Jeor)")
    void basal_metabolic_rate_male() {
        Body body = new Body(Sex.MALE, 25, 1.80, 80);

        Assertions.assertEquals(1805, body.basalMetabolicRateKcalPerDay(), 0.01);
    }

    @Test
    @DisplayName("a 30-year-old, 1.65 m, 60 kg woman should burn about 1320 kcal/day at rest (Mifflin-St Jeor)")
    void basal_metabolic_rate_female() {
        Body body = new Body(Sex.FEMALE, 30, 1.65, 60);

        Assertions.assertEquals(1320.25, body.basalMetabolicRateKcalPerDay(), 0.01);
    }

    @Test
    @DisplayName("a 1.80 m, 80 kg adult should have a body surface area of 2 m² (Mosteller)")
    void body_surface_area() {
        Body body = new Body(Sex.MALE, 40, 1.80, 80);

        Assertions.assertEquals(2.0, body.bodySurfaceAreaSquareMeters(), 1e-9);
    }

    @Test
    @DisplayName("a 30-year-old's heart should beat at most 187 times a minute (Tanaka)")
    void max_heart_rate() {
        Body body = new Body(Sex.FEMALE, 30, 1.65, 60);

        Assertions.assertEquals(187, body.maxHeartRateBpm(), 1e-9);
    }

    @Test
    @DisplayName("the 30-year-old ICRP reference man (1.76 m) should have a vital capacity of about 5.02 L (ERS 1993)")
    void vital_capacity_male() {
        Body body = new Body(Sex.MALE, 30, 1.76, 73);

        Assertions.assertEquals(5.02, body.vitalCapacityLiters(), 0.01);
    }

    @Test
    @DisplayName("a 30-year-old, 1.65 m woman should have a vital capacity of about 3.64 L (ERS 1993)")
    void vital_capacity_female() {
        Body body = new Body(Sex.FEMALE, 30, 1.65, 60);

        Assertions.assertEquals(3.64, body.vitalCapacityLiters(), 0.01);
    }

    @Test
    @DisplayName("vital capacity should decline with age")
    void vital_capacity_declines_with_age() {
        Assertions.assertTrue(new Body(Sex.MALE, 60, 1.76, 73).vitalCapacityLiters()
                < new Body(Sex.MALE, 30, 1.76, 73).vitalCapacityLiters());
    }

    @Test
    @DisplayName("a body should not have a non-positive height or mass")
    void rejects_non_positive_dimensions() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Body(Sex.MALE, 30, 0, 70));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Body(Sex.MALE, 30, 1.75, -1));
    }

    @Test
    @DisplayName("a body should be an adult's, since the formulas are adult references")
    void rejects_minors() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Body(Sex.FEMALE, 17, 1.60, 50));
    }

    @Test
    @DisplayName("a body should carry the karyotype of its intersex variation, or the usual one of its sex")
    void karyotypes() {
        Body klinefelter = new Body(Sex.MALE, 30, 1.85, 80, IntersexVariation.KLINEFELTER_SYNDROME, null);
        Body androgenInsensitive = new Body(Sex.FEMALE, 30, 1.70, 60,
                IntersexVariation.COMPLETE_ANDROGEN_INSENSITIVITY, null);

        Assertions.assertEquals("47,XXY", klinefelter.getKaryotype().toString());
        Assertions.assertEquals(Karyotype.XY, androgenInsensitive.getKaryotype());
        Assertions.assertTrue(klinefelter.isIntersex());
        Assertions.assertEquals("46,XX", new Body(Sex.FEMALE, 30, 1.65, 60).getKaryotype().toString());
        Assertions.assertEquals("45,X", IntersexVariation.TURNER_SYNDROME.getKaryotype().toString());
        Assertions.assertFalse(new Body(Sex.MALE, 30, 1.80, 75).isIntersex());
    }

    @Test
    @DisplayName("after a year of feminizing hormones, metabolism should follow female references, lungs should not")
    void hormone_therapy() {
        Body before = new Body(Sex.MALE, 30, 1.75, 65);
        Body starting = new Body(Sex.MALE, 30, 1.75, 65, null,
                new HormoneTherapy(HormoneTherapy.Kind.FEMINIZING, 0.5));
        Body established = new Body(Sex.MALE, 30, 1.75, 65, null,
                new HormoneTherapy(HormoneTherapy.Kind.FEMINIZING, 2));

        Assertions.assertEquals(Sex.MALE, starting.getHormonalSex());
        Assertions.assertEquals(Sex.FEMALE, established.getHormonalSex());
        Assertions.assertEquals(Sex.MALE, established.getSex());
        Assertions.assertEquals(166, before.basalMetabolicRateKcalPerDay() - established.basalMetabolicRateKcalPerDay(),
                1e-9);
        Assertions.assertEquals(before.vitalCapacityLiters(), established.vitalCapacityLiters());
    }

    @Test
    @DisplayName("hormone therapy should not last a negative time")
    void rejects_negative_therapy() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new HormoneTherapy(HormoneTherapy.Kind.MASCULINIZING, -1));
    }
}
