package com.enlil.universe.matter.chemistry;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A homogeneous mixture of compounds, described by their mole fractions.
 */
public final class Mixture {

    private static final double TOLERANCE = 1e-3;

    private final Map<Compound, Double> moleFractions;

    /**
     * @param moleFractions the share of the molecules of each compound, each in (0, 1], summing to 1 within 0.001
     */
    public Mixture(Map<Compound, Double> moleFractions) {
        if (moleFractions.isEmpty()) {
            throw new IllegalArgumentException("a mixture needs at least one compound");
        }
        if (moleFractions.values().stream().anyMatch(fraction -> fraction <= 0 || fraction > 1)) {
            throw new IllegalArgumentException("mole fractions must be in (0, 1]");
        }
        double sum = moleFractions.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(sum - 1) > TOLERANCE) {
            throw new IllegalArgumentException("mole fractions must sum to 1, not " + sum);
        }
        this.moleFractions = Collections.unmodifiableMap(new LinkedHashMap<>(moleFractions));
    }

    /** A mixture made of given masses of each compound, in grams, converted to mole fractions. */
    public static Mixture ofMasses(Map<Compound, Double> massesGrams) {
        Map<Compound, Double> moles = new LinkedHashMap<>();
        massesGrams.forEach((compound, grams) -> moles.put(compound, grams / compound.molarMassGramsPerMole()));
        double total = moles.values().stream().mapToDouble(Double::doubleValue).sum();
        Map<Compound, Double> fractions = new LinkedHashMap<>();
        moles.forEach((compound, amount) -> fractions.put(compound, amount / total));
        return new Mixture(fractions);
    }

    public Map<Compound, Double> getMoleFractions() {
        return moleFractions;
    }

    /** Share of the molecules that are the given compound; 0 if absent. */
    public double moleFraction(Compound compound) {
        return moleFractions.getOrDefault(compound, 0.0);
    }

    /** Share of the mass carried by the given compound; 0 if absent. */
    public double massFraction(Compound compound) {
        return moleFraction(compound) * compound.molarMassGramsPerMole() / molarMassGramsPerMole();
    }

    /** Mean molar mass Σ xᵢ·Mᵢ, in g/mol. */
    public double molarMassGramsPerMole() {
        return moleFractions.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * entry.getKey().molarMassGramsPerMole())
                .sum();
    }
}
