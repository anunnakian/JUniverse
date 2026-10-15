package com.enlil.universe.life.human;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The pair of ABO alleles and the pair of RhD alleles a human inherited, one of each pair from each parent.
 * The order of the alleles within a pair carries no meaning, so they are kept sorted.
 */
@Getter
@EqualsAndHashCode
public class BloodGenotype {

    private final AboAllele firstAbo;
    private final AboAllele secondAbo;
    private final RhAllele firstRh;
    private final RhAllele secondRh;

    public BloodGenotype(AboAllele abo1, AboAllele abo2, RhAllele rh1, RhAllele rh2) {
        boolean aboSorted = abo1.compareTo(abo2) <= 0;
        this.firstAbo = aboSorted ? abo1 : abo2;
        this.secondAbo = aboSorted ? abo2 : abo1;
        boolean rhSorted = rh1.compareTo(rh2) <= 0;
        this.firstRh = rhSorted ? rh1 : rh2;
        this.secondRh = rhSorted ? rh2 : rh1;
    }

    /** The blood group this genotype expresses. */
    public AboGroup aboGroup() {
        boolean a = hasAbo(AboAllele.A);
        boolean b = hasAbo(AboAllele.B);
        if (a && b) {
            return AboGroup.AB;
        }
        if (a) {
            return AboGroup.A;
        }
        return b ? AboGroup.B : AboGroup.O;
    }

    /** The Rh factor this genotype expresses. */
    public RhFactor rhFactor() {
        return hasRh(RhAllele.D) ? RhFactor.POSITIVE : RhFactor.NEGATIVE;
    }

    /**
     * Probability of each genotype in a child of this and the other parent (Punnett square). ABO and RhD lie on
     * different chromosomes, so they are inherited independently.
     */
    public Map<BloodGenotype, Double> offspring(BloodGenotype other) {
        Map<BloodGenotype, Double> probabilities = new HashMap<>();
        for (AboAllele abo1 : aboAlleles()) {
            for (AboAllele abo2 : other.aboAlleles()) {
                for (RhAllele rh1 : rhAlleles()) {
                    for (RhAllele rh2 : other.rhAlleles()) {
                        probabilities.merge(new BloodGenotype(abo1, abo2, rh1, rh2), 1.0 / 16, Double::sum);
                    }
                }
            }
        }
        return Map.copyOf(probabilities);
    }

    /** Whether this genotype could have received one allele of each gene from the given parent. */
    public boolean canInheritFrom(BloodGenotype parent) {
        return aboAlleles().stream().anyMatch(parent::hasAbo) && rhAlleles().stream().anyMatch(parent::hasRh);
    }

    private List<AboAllele> aboAlleles() {
        return List.of(firstAbo, secondAbo);
    }

    private List<RhAllele> rhAlleles() {
        return List.of(firstRh, secondRh);
    }

    private boolean hasAbo(AboAllele allele) {
        return firstAbo == allele || secondAbo == allele;
    }

    private boolean hasRh(RhAllele allele) {
        return firstRh == allele || secondRh == allele;
    }

    @Override
    public String toString() {
        return "" + firstAbo + secondAbo + " " + firstRh + secondRh;
    }
}
