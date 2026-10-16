package com.enlil.universe.matter.chemistry;

import lombok.Getter;

/**
 * An aqueous solution of a single solute, at 25 °C. Strong acids and bases are counted as giving one H⁺ or OH⁻ per
 * formula unit: the second proton of sulfuric acid is not modeled.
 */
@Getter
public class Solution {

    private static final Formula WATER = Formula.parse("H2O");

    private final Compound solute;
    private final Compound solvent;
    private final double molarityMolesPerLiter;

    /**
     * @param solvent               must be water
     * @param molarityMolesPerLiter concentration of the solute, in mol/L
     */
    public Solution(Compound solute, Compound solvent, double molarityMolesPerLiter) {
        if (!WATER.equals(solvent.getFormula())) {
            throw new IllegalArgumentException("only aqueous solutions are modeled, not " + solvent.getName());
        }
        if (molarityMolesPerLiter <= 0) {
            throw new IllegalArgumentException("molarity must be positive");
        }
        this.solute = solute;
        this.solvent = solvent;
        this.molarityMolesPerLiter = molarityMolesPerLiter;
    }

    /**
     * pH at 25 °C. A strong acid of concentration C gives [H⁺] = C/2 + √(C²/4 + Kw), counting the water's own ions,
     * so a very dilute acid stays near 7; a weak acid gives the positive root of x² + Ka·x − Ka·C = 0. Bases do the
     * same for [OH⁻], then pH = 14 − pOH. Anything else is neutral.
     */
    public double ph() {
        double concentration = molarityMolesPerLiter;
        if (solute.isStrongAcid()) {
            return -Math.log10(strongIonConcentration(concentration));
        }
        if (solute.isStrongBase()) {
            return 14 + Math.log10(strongIonConcentration(concentration));
        }
        if (solute.getAcidDissociationConstant().isPresent()) {
            return -Math.log10(weakIonConcentration(solute.getAcidDissociationConstant().getAsDouble(), concentration));
        }
        if (solute.getBaseDissociationConstant().isPresent()) {
            return 14 + Math.log10(weakIonConcentration(solute.getBaseDissociationConstant().getAsDouble(),
                    concentration));
        }
        return 7;
    }

    public Acidity acidity() {
        return Acidity.fromPh(ph());
    }

    private static double strongIonConcentration(double concentration) {
        return concentration / 2
                + Math.sqrt(concentration * concentration / 4 + ChemicalConstants.WATER_ION_PRODUCT);
    }

    private static double weakIonConcentration(double dissociationConstant, double concentration) {
        return (-dissociationConstant + Math.sqrt(dissociationConstant * dissociationConstant
                + 4 * dissociationConstant * concentration)) / 2;
    }
}
