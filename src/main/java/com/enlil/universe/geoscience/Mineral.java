package com.enlil.universe.geoscience;

import com.enlil.universe.matter.chemistry.Formula;
import lombok.Getter;

import java.util.Objects;

/**
 * A naturally occurring crystalline solid with a definite chemical formula.
 */
@Getter
public class Mineral {

    private final String name;
    private final Formula formula;
    /** Scratch hardness on the Mohs scale, from 1 (talc) to 10 (diamond). */
    private final double mohsHardness;
    private final double densityKgPerCubicMeter;

    public Mineral(String name, Formula formula, double mohsHardness, double densityKgPerCubicMeter) {
        if (mohsHardness < 1 || mohsHardness > 10) {
            throw new IllegalArgumentException("Mohs hardness must be between 1 and 10");
        }
        if (densityKgPerCubicMeter <= 0) {
            throw new IllegalArgumentException("density must be positive");
        }
        this.name = name;
        this.formula = Objects.requireNonNull(formula, "formula");
        this.mohsHardness = mohsHardness;
        this.densityKgPerCubicMeter = densityKgPerCubicMeter;
    }

    /** Whether this mineral scratches another, being strictly harder. */
    public boolean scratches(Mineral other) {
        return mohsHardness > other.mohsHardness;
    }

    /** Molar mass of the formula, in g/mol. */
    public double molarMassGramsPerMole() {
        return formula.molarMassGramsPerMole();
    }

    @Override
    public String toString() {
        return name;
    }
}
