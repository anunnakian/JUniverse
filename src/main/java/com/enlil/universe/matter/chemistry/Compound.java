package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.StateOfMatter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.OptionalDouble;

/**
 * A chemical substance: a name, a formula, its state at the standard state (298.15 K and 1 bar) and the
 * thermochemical and acid–base data known for it. Two compounds are equal when they share their name and formula.
 */
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Compound {

    @EqualsAndHashCode.Include
    private final String name;
    @EqualsAndHashCode.Include
    private final Formula formula;
    private final StateOfMatter standardState;
    /** Standard enthalpy of formation, in kJ/mol; null when unknown. */
    @Getter(AccessLevel.NONE)
    private final Double enthalpyOfFormationKjPerMole;
    /** Acid dissociation constant Ka of a weak acid; null otherwise. */
    @Getter(AccessLevel.NONE)
    private final Double acidDissociationConstant;
    /** Base dissociation constant Kb of a weak base; null otherwise. */
    @Getter(AccessLevel.NONE)
    private final Double baseDissociationConstant;
    /** Whether the compound gives up all its H⁺ in water. */
    private final boolean strongAcid;
    /** Whether the compound releases all its OH⁻ in water. */
    private final boolean strongBase;

    @Builder
    protected Compound(String name, Formula formula, StateOfMatter standardState,
                       Double enthalpyOfFormationKjPerMole, Double acidDissociationConstant,
                       Double baseDissociationConstant, boolean strongAcid, boolean strongBase) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("a compound must have a name");
        }
        Objects.requireNonNull(formula, "formula");
        Objects.requireNonNull(standardState, "standardState");
        if (acidDissociationConstant != null && acidDissociationConstant <= 0
                || baseDissociationConstant != null && baseDissociationConstant <= 0) {
            throw new IllegalArgumentException("dissociation constants must be positive");
        }
        int behaviours = (acidDissociationConstant != null ? 1 : 0) + (baseDissociationConstant != null ? 1 : 0)
                + (strongAcid ? 1 : 0) + (strongBase ? 1 : 0);
        if (behaviours > 1) {
            throw new IllegalArgumentException(
                    "a compound is at most one of weak acid, weak base, strong acid or strong base");
        }
        this.name = name;
        this.formula = formula;
        this.standardState = standardState;
        this.enthalpyOfFormationKjPerMole = enthalpyOfFormationKjPerMole;
        this.acidDissociationConstant = acidDissociationConstant;
        this.baseDissociationConstant = baseDissociationConstant;
        this.strongAcid = strongAcid;
        this.strongBase = strongBase;
    }

    /** A copy of another compound, for subclasses that add structure to it. */
    protected Compound(Compound other) {
        this.name = other.name;
        this.formula = other.formula;
        this.standardState = other.standardState;
        this.enthalpyOfFormationKjPerMole = other.enthalpyOfFormationKjPerMole;
        this.acidDissociationConstant = other.acidDissociationConstant;
        this.baseDissociationConstant = other.baseDissociationConstant;
        this.strongAcid = other.strongAcid;
        this.strongBase = other.strongBase;
    }

    /** Standard enthalpy of formation ΔHf°, in kJ/mol; 0 for an element in its standard state. */
    public OptionalDouble getEnthalpyOfFormationKjPerMole() {
        return optional(enthalpyOfFormationKjPerMole);
    }

    /** Acid dissociation constant Ka, for a weak acid. */
    public OptionalDouble getAcidDissociationConstant() {
        return optional(acidDissociationConstant);
    }

    /** Base dissociation constant Kb, for a weak base. */
    public OptionalDouble getBaseDissociationConstant() {
        return optional(baseDissociationConstant);
    }

    /** Molar mass, in g/mol. */
    public double molarMassGramsPerMole() {
        return formula.molarMassGramsPerMole();
    }

    @Override
    public String toString() {
        return name + " (" + formula + ")";
    }

    private static OptionalDouble optional(Double value) {
        return value == null ? OptionalDouble.empty() : OptionalDouble.of(value);
    }
}
