package com.enlil.universe.world.chemistry;

import com.enlil.universe.matter.StateOfMatter;
import com.enlil.universe.matter.chemistry.Compound;
import com.enlil.universe.matter.chemistry.Formula;
import com.enlil.universe.matter.chemistry.Molecule;

import static com.enlil.universe.matter.chemistry.BondOrder.*;

/**
 * Real compounds, with their standard enthalpies of formation (NIST Chemistry WebBook, kJ/mol at 298.15 K) and
 * acid–base constants (CRC Handbook). Molecules list their bonds; ionic solids, metals, and molecules whose real
 * bonding breaks the simple valence rule are given by their formula only.
 */
public final class Compounds {

    /** Liquid water, as used by every reaction of this universe. */
    public static final Molecule WATER = Molecule.builder()
            .name("Water").standardState(StateOfMatter.LIQUID).enthalpyOfFormationKjPerMole(-285.83)
            .atom("O").atom("H1").atom("H2")
            .bond("O", "H1", SINGLE).bond("O", "H2", SINGLE)
            .build();
    public static final Molecule CARBON_DIOXIDE = Molecule.builder()
            .name("Carbon dioxide").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(-393.51)
            .atom("C").atom("O1").atom("O2")
            .bond("C", "O1", DOUBLE).bond("C", "O2", DOUBLE)
            .build();
    public static final Molecule OXYGEN_GAS = Molecule.builder()
            .name("Oxygen").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(0.0)
            .atom("O1").atom("O2")
            .bond("O1", "O2", DOUBLE)
            .build();
    public static final Molecule NITROGEN_GAS = Molecule.builder()
            .name("Nitrogen").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(0.0)
            .atom("N1").atom("N2")
            .bond("N1", "N2", TRIPLE)
            .build();
    public static final Molecule HYDROGEN_GAS = Molecule.builder()
            .name("Hydrogen").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(0.0)
            .atom("H1").atom("H2")
            .bond("H1", "H2", SINGLE)
            .build();
    public static final Molecule CHLORINE_GAS = Molecule.builder()
            .name("Chlorine").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(0.0)
            .atom("Cl1").atom("Cl2")
            .bond("Cl1", "Cl2", SINGLE)
            .build();
    public static final Molecule METHANE = Molecule.builder()
            .name("Methane").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(-74.87)
            .atom("C").atom("H1").atom("H2").atom("H3").atom("H4")
            .bond("C", "H1", SINGLE).bond("C", "H2", SINGLE).bond("C", "H3", SINGLE).bond("C", "H4", SINGLE)
            .build();
    public static final Molecule ETHANE = Molecule.builder()
            .name("Ethane").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(-84.0)
            .atom("C1").atom("C2").atom("H1").atom("H2").atom("H3").atom("H4").atom("H5").atom("H6")
            .bond("C1", "C2", SINGLE)
            .bond("C1", "H1", SINGLE).bond("C1", "H2", SINGLE).bond("C1", "H3", SINGLE)
            .bond("C2", "H4", SINGLE).bond("C2", "H5", SINGLE).bond("C2", "H6", SINGLE)
            .build();
    public static final Molecule PROPANE = Molecule.builder()
            .name("Propane").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(-104.7)
            .atom("C1").atom("C2").atom("C3")
            .atom("H1").atom("H2").atom("H3").atom("H4").atom("H5").atom("H6").atom("H7").atom("H8")
            .bond("C1", "C2", SINGLE).bond("C2", "C3", SINGLE)
            .bond("C1", "H1", SINGLE).bond("C1", "H2", SINGLE).bond("C1", "H3", SINGLE)
            .bond("C2", "H4", SINGLE).bond("C2", "H5", SINGLE)
            .bond("C3", "H6", SINGLE).bond("C3", "H7", SINGLE).bond("C3", "H8", SINGLE)
            .build();
    public static final Molecule ETHYLENE = Molecule.builder()
            .name("Ethylene").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(52.47)
            .atom("C1").atom("C2").atom("H1").atom("H2").atom("H3").atom("H4")
            .bond("C1", "C2", DOUBLE)
            .bond("C1", "H1", SINGLE).bond("C1", "H2", SINGLE).bond("C2", "H3", SINGLE).bond("C2", "H4", SINGLE)
            .build();
    public static final Molecule ACETYLENE = Molecule.builder()
            .name("Acetylene").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(227.4)
            .atom("C1").atom("C2").atom("H1").atom("H2")
            .bond("C1", "C2", TRIPLE).bond("C1", "H1", SINGLE).bond("C2", "H2", SINGLE)
            .build();
    public static final Molecule ETHANOL = Molecule.builder()
            .name("Ethanol").standardState(StateOfMatter.LIQUID).enthalpyOfFormationKjPerMole(-277.0)
            .atom("C1").atom("C2").atom("O").atom("H1").atom("H2").atom("H3").atom("H4").atom("H5").atom("H6")
            .bond("C1", "C2", SINGLE).bond("C2", "O", SINGLE).bond("O", "H6", SINGLE)
            .bond("C1", "H1", SINGLE).bond("C1", "H2", SINGLE).bond("C1", "H3", SINGLE)
            .bond("C2", "H4", SINGLE).bond("C2", "H5", SINGLE)
            .build();
    /** A weak base: Kb = 1.8e-5. */
    public static final Molecule AMMONIA = Molecule.builder()
            .name("Ammonia").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(-45.90)
            .baseDissociationConstant(1.8e-5)
            .atom("N").atom("H1").atom("H2").atom("H3")
            .bond("N", "H1", SINGLE).bond("N", "H2", SINGLE).bond("N", "H3", SINGLE)
            .build();
    /** The gas; dissolved in water it becomes {@link #HYDROCHLORIC_ACID}. */
    public static final Molecule HYDROGEN_CHLORIDE = Molecule.builder()
            .name("Hydrogen chloride").standardState(StateOfMatter.GAS).enthalpyOfFormationKjPerMole(-92.31)
            .atom("H").atom("Cl")
            .bond("H", "Cl", SINGLE)
            .build();

    /** Its C≡O triple bond carries formal charges, beyond the simple valence rule. */
    public static final Compound CARBON_MONOXIDE = compound("Carbon monoxide", "CO", StateOfMatter.GAS, -110.53);
    /** Its O=O–O bonds carry formal charges, beyond the simple valence rule. */
    public static final Compound OZONE = compound("Ozone", "O3", StateOfMatter.GAS, 142.67);
    public static final Compound GLUCOSE = compound("Glucose", "C6H12O6", StateOfMatter.SOLID, -1_273.3);
    /** The acid of vinegar, a weak acid: Ka = 1.75e-5. */
    public static final Compound ACETIC_ACID = Compound.builder()
            .name("Acetic acid").formula(Formula.parse("CH3COOH")).standardState(StateOfMatter.LIQUID)
            .enthalpyOfFormationKjPerMole(-484.3).acidDissociationConstant(1.75e-5)
            .build();
    /** Hydrogen chloride dissolved in water, a strong acid; its enthalpy of formation is the aqueous one. */
    public static final Compound HYDROCHLORIC_ACID = Compound.builder()
            .name("Hydrochloric acid").formula(Formula.parse("HCl")).standardState(StateOfMatter.LIQUID)
            .enthalpyOfFormationKjPerMole(-167.16).strongAcid(true)
            .build();
    public static final Compound SULFURIC_ACID = Compound.builder()
            .name("Sulfuric acid").formula(Formula.parse("H2SO4")).standardState(StateOfMatter.LIQUID)
            .enthalpyOfFormationKjPerMole(-814.0).strongAcid(true)
            .build();
    public static final Compound NITRIC_ACID = Compound.builder()
            .name("Nitric acid").formula(Formula.parse("HNO3")).standardState(StateOfMatter.LIQUID)
            .enthalpyOfFormationKjPerMole(-174.1).strongAcid(true)
            .build();
    /** Caustic soda, a strong base. */
    public static final Compound SODIUM_HYDROXIDE = Compound.builder()
            .name("Sodium hydroxide").formula(Formula.parse("NaOH")).standardState(StateOfMatter.SOLID)
            .enthalpyOfFormationKjPerMole(-425.8).strongBase(true)
            .build();
    /** Table salt. */
    public static final Compound SODIUM_CHLORIDE = compound("Sodium chloride", "NaCl", StateOfMatter.SOLID, -411.12);
    /** Limestone, chalk and marble. */
    public static final Compound CALCIUM_CARBONATE =
            compound("Calcium carbonate", "CaCO3", StateOfMatter.SOLID, -1_207.6);
    /** Quartz and sand. */
    public static final Compound SILICON_DIOXIDE = compound("Silicon dioxide", "SiO2", StateOfMatter.SOLID, -910.7);
    /** Rust and hematite. */
    public static final Compound IRON_III_OXIDE = compound("Iron(III) oxide", "Fe2O3", StateOfMatter.SOLID, -824.2);
    public static final Compound IRON = compound("Iron", "Fe", StateOfMatter.SOLID, 0);
    /** A monatomic noble gas. */
    public static final Compound ARGON = compound("Argon", "Ar", StateOfMatter.GAS, 0);

    private Compounds() {
    }

    private static Compound compound(String name, String formula, StateOfMatter standardState,
                                     double enthalpyOfFormationKjPerMole) {
        return Compound.builder()
                .name(name)
                .formula(Formula.parse(formula))
                .standardState(standardState)
                .enthalpyOfFormationKjPerMole(enthalpyOfFormationKjPerMole)
                .build();
    }
}
