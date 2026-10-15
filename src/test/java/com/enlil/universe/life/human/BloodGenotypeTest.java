package com.enlil.universe.life.human;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static com.enlil.universe.life.human.AboAllele.A;
import static com.enlil.universe.life.human.AboAllele.B;
import static com.enlil.universe.life.human.AboAllele.O;
import static com.enlil.universe.life.human.RhAllele.D;
import static com.enlil.universe.life.human.RhAllele.d;

public class BloodGenotypeTest {

    @ParameterizedTest(name = "{0}{1} {2}{3} should be group {4} {5}")
    @CsvSource({
            "A, A, D, D, A, POSITIVE",
            "A, O, D, d, A, POSITIVE",
            "O, B, d, D, B, POSITIVE",
            "A, B, d, d, AB, NEGATIVE",
            "O, O, d, d, O, NEGATIVE"})
    @DisplayName("A and B should be codominant over O, and D dominant over d")
    void phenotype(AboAllele abo1, AboAllele abo2, RhAllele rh1, RhAllele rh2, AboGroup group, RhFactor rhFactor) {
        BloodGenotype genotype = new BloodGenotype(abo1, abo2, rh1, rh2);

        Assertions.assertEquals(group, genotype.aboGroup());
        Assertions.assertEquals(rhFactor, genotype.rhFactor());
    }

    @Test
    @DisplayName("the order of the alleles should not matter")
    void unordered_alleles() {
        Assertions.assertEquals(new BloodGenotype(A, O, D, d), new BloodGenotype(O, A, d, D));
    }

    @Test
    @DisplayName("two AO Dd parents should have a 1 in 4 chance of an O child and 1 in 4 of an Rh- one")
    void punnett_square() {
        BloodGenotype parent = new BloodGenotype(A, O, D, d);

        Map<BloodGenotype, Double> offspring = parent.offspring(parent);

        Assertions.assertEquals(1, offspring.values().stream().mapToDouble(Double::doubleValue).sum(), 1e-12);
        Assertions.assertEquals(0.25, probability(offspring, AboGroup.O), 1e-12);
        Assertions.assertEquals(0.25, offspring.entrySet().stream()
                .filter(entry -> entry.getKey().rhFactor() == RhFactor.NEGATIVE)
                .mapToDouble(Map.Entry::getValue).sum(), 1e-12);
        Assertions.assertEquals(1.0 / 16, offspring.get(new BloodGenotype(O, O, d, d)), 1e-12);
    }

    @Test
    @DisplayName("AB and O parents should only have A or B children")
    void ab_and_o_parents() {
        Map<BloodGenotype, Double> offspring = new BloodGenotype(A, B, D, D).offspring(new BloodGenotype(O, O, d, d));

        Assertions.assertEquals(0.5, probability(offspring, AboGroup.A), 1e-12);
        Assertions.assertEquals(0.5, probability(offspring, AboGroup.B), 1e-12);
        Assertions.assertTrue(offspring.keySet().stream().allMatch(child -> child.rhFactor() == RhFactor.POSITIVE));
    }

    @Test
    @DisplayName("a child should carry one of a parent's alleles for each gene")
    void can_inherit_from() {
        BloodGenotype parent = new BloodGenotype(A, B, d, d);

        Assertions.assertTrue(new BloodGenotype(B, O, D, d).canInheritFrom(parent));
        Assertions.assertFalse(new BloodGenotype(O, O, d, d).canInheritFrom(parent));
        Assertions.assertFalse(new BloodGenotype(A, O, D, D).canInheritFrom(parent));
    }

    private static double probability(Map<BloodGenotype, Double> offspring, AboGroup group) {
        return offspring.entrySet().stream()
                .filter(entry -> entry.getKey().aboGroup() == group)
                .mapToDouble(Map.Entry::getValue).sum();
    }
}
