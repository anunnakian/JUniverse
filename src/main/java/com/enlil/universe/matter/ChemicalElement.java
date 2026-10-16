package com.enlil.universe.matter;

import java.util.Arrays;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static com.enlil.universe.matter.ElementCategory.*;

/**
 * The 118 chemical elements, identified by their atomic number, from hydrogen to oganesson.
 * <p>
 * Standard atomic weights are IUPAC's 2021 abridged values (the conventional value where IUPAC gives an interval);
 * for elements without a stable isotope, the weight and the most abundant mass number are the mass number of the
 * longest-lived isotope, as the IUPAC table gives it. Melting and boiling points at 1 atm and Pauling
 * electronegativities come from the CRC Handbook; {@code NaN} or empty where they were never measured.
 */
public enum ChemicalElement {
    HYDROGEN("H", 1, 1.008, 1, 13.99, 20.271, 1, 1, REACTIVE_NONMETAL, 2.20),
    /** Never freezes at atmospheric pressure, even at absolute zero. */
    HELIUM("He", 2, 4.002_6, 4, Double.NaN, 4.222, 1, 18, NOBLE_GAS, null),
    LITHIUM("Li", 3, 6.94, 7, 453.65, 1_603, 2, 1, ALKALI_METAL, 0.98),
    BERYLLIUM("Be", 4, 9.012_2, 9, 1_560, 2_742, 2, 2, ALKALINE_EARTH_METAL, 1.57),
    BORON("B", 5, 10.81, 11, 2_349, 4_200, 2, 13, METALLOID, 2.04),
    /** Graphite sublimates at atmospheric pressure: it goes straight from solid to gas. */
    CARBON("C", 6, 12.011, 12, 3_915, 3_915, 2, 14, REACTIVE_NONMETAL, 2.55),
    NITROGEN("N", 7, 14.007, 14, 63.23, 77.355, 2, 15, REACTIVE_NONMETAL, 3.04),
    OXYGEN("O", 8, 15.999, 16, 54.36, 90.188, 2, 16, REACTIVE_NONMETAL, 3.44),
    /** The most electronegative element. */
    FLUORINE("F", 9, 18.998, 19, 53.48, 85.03, 2, 17, HALOGEN, 3.98),
    NEON("Ne", 10, 20.180, 20, 24.56, 27.104, 2, 18, NOBLE_GAS, null),
    SODIUM("Na", 11, 22.990, 23, 370.944, 1_156.090, 3, 1, ALKALI_METAL, 0.93),
    MAGNESIUM("Mg", 12, 24.305, 24, 923, 1_363, 3, 2, ALKALINE_EARTH_METAL, 1.31),
    ALUMINIUM("Al", 13, 26.982, 27, 933.47, 2_743, 3, 13, POST_TRANSITION_METAL, 1.61),
    SILICON("Si", 14, 28.085, 28, 1_687, 3_538, 3, 14, METALLOID, 1.90),
    /** White phosphorus. */
    PHOSPHORUS("P", 15, 30.974, 31, 317.3, 553.7, 3, 15, REACTIVE_NONMETAL, 2.19),
    SULFUR("S", 16, 32.06, 32, 388.36, 717.8, 3, 16, REACTIVE_NONMETAL, 2.58),
    CHLORINE("Cl", 17, 35.45, 35, 171.6, 239.11, 3, 17, HALOGEN, 3.16),
    ARGON("Ar", 18, 39.95, 40, 83.81, 87.302, 3, 18, NOBLE_GAS, null),
    POTASSIUM("K", 19, 39.098, 39, 336.7, 1_032, 4, 1, ALKALI_METAL, 0.82),
    CALCIUM("Ca", 20, 40.078, 40, 1_115, 1_757, 4, 2, ALKALINE_EARTH_METAL, 1.00),
    SCANDIUM("Sc", 21, 44.956, 45, 1_814, 3_109, 4, 3, TRANSITION_METAL, 1.36),
    TITANIUM("Ti", 22, 47.867, 48, 1_941, 3_560, 4, 4, TRANSITION_METAL, 1.54),
    VANADIUM("V", 23, 50.942, 51, 2_183, 3_680, 4, 5, TRANSITION_METAL, 1.63),
    CHROMIUM("Cr", 24, 51.996, 52, 2_180, 2_944, 4, 6, TRANSITION_METAL, 1.66),
    MANGANESE("Mn", 25, 54.938, 55, 1_519, 2_334, 4, 7, TRANSITION_METAL, 1.55),
    IRON("Fe", 26, 55.845, 56, 1_811, 3_134, 4, 8, TRANSITION_METAL, 1.83),
    COBALT("Co", 27, 58.933, 59, 1_768, 3_200, 4, 9, TRANSITION_METAL, 1.88),
    NICKEL("Ni", 28, 58.693, 58, 1_728, 3_003, 4, 10, TRANSITION_METAL, 1.91),
    COPPER("Cu", 29, 63.546, 63, 1_357.77, 2_835, 4, 11, TRANSITION_METAL, 1.90),
    ZINC("Zn", 30, 65.38, 64, 692.68, 1_180, 4, 12, TRANSITION_METAL, 1.65),
    GALLIUM("Ga", 31, 69.723, 69, 302.914_6, 2_673, 4, 13, POST_TRANSITION_METAL, 1.81),
    GERMANIUM("Ge", 32, 72.630, 74, 1_211.40, 3_106, 4, 14, METALLOID, 2.01),
    /** Grey arsenic sublimates at atmospheric pressure; it only melts under pressure. */
    ARSENIC("As", 33, 74.922, 75, 887, 887, 4, 15, METALLOID, 2.18),
    SELENIUM("Se", 34, 78.971, 80, 494, 958, 4, 16, REACTIVE_NONMETAL, 2.55),
    BROMINE("Br", 35, 79.904, 79, 265.8, 332.0, 4, 17, HALOGEN, 2.96),
    KRYPTON("Kr", 36, 83.798, 84, 115.78, 119.93, 4, 18, NOBLE_GAS, 3.00),
    RUBIDIUM("Rb", 37, 85.468, 85, 312.45, 961, 5, 1, ALKALI_METAL, 0.82),
    STRONTIUM("Sr", 38, 87.62, 88, 1_050, 1_650, 5, 2, ALKALINE_EARTH_METAL, 0.95),
    YTTRIUM("Y", 39, 88.906, 89, 1_799, 3_203, 5, 3, TRANSITION_METAL, 1.22),
    ZIRCONIUM("Zr", 40, 91.224, 90, 2_128, 4_650, 5, 4, TRANSITION_METAL, 1.33),
    NIOBIUM("Nb", 41, 92.906, 93, 2_750, 5_017, 5, 5, TRANSITION_METAL, 1.6),
    MOLYBDENUM("Mo", 42, 95.95, 98, 2_896, 4_912, 5, 6, TRANSITION_METAL, 2.16),
    /** The lightest element without a stable isotope. */
    TECHNETIUM("Tc", 43, 98, 98, 2_430, 4_538, 5, 7, TRANSITION_METAL, 1.9),
    RUTHENIUM("Ru", 44, 101.07, 102, 2_607, 4_423, 5, 8, TRANSITION_METAL, 2.2),
    RHODIUM("Rh", 45, 102.91, 103, 2_237, 3_968, 5, 9, TRANSITION_METAL, 2.28),
    PALLADIUM("Pd", 46, 106.42, 106, 1_828.05, 3_236, 5, 10, TRANSITION_METAL, 2.20),
    SILVER("Ag", 47, 107.87, 107, 1_234.93, 2_435, 5, 11, TRANSITION_METAL, 1.93),
    CADMIUM("Cd", 48, 112.41, 114, 594.22, 1_040, 5, 12, TRANSITION_METAL, 1.69),
    INDIUM("In", 49, 114.82, 115, 429.75, 2_345, 5, 13, POST_TRANSITION_METAL, 1.78),
    TIN("Sn", 50, 118.71, 120, 505.08, 2_875, 5, 14, POST_TRANSITION_METAL, 1.96),
    ANTIMONY("Sb", 51, 121.76, 121, 903.78, 1_908, 5, 15, METALLOID, 2.05),
    TELLURIUM("Te", 52, 127.60, 130, 722.66, 1_261, 5, 16, METALLOID, 2.1),
    IODINE("I", 53, 126.90, 127, 386.85, 457.4, 5, 17, HALOGEN, 2.66),
    XENON("Xe", 54, 131.29, 132, 161.40, 165.051, 5, 18, NOBLE_GAS, 2.6),
    CAESIUM("Cs", 55, 132.91, 133, 301.7, 944, 6, 1, ALKALI_METAL, 0.79),
    BARIUM("Ba", 56, 137.33, 138, 1_000, 2_118, 6, 2, ALKALINE_EARTH_METAL, 0.89),
    LANTHANUM("La", 57, 138.91, 139, 1_193, 3_737, 6, null, LANTHANIDE, 1.10),
    CERIUM("Ce", 58, 140.12, 140, 1_068, 3_716, 6, null, LANTHANIDE, 1.12),
    PRASEODYMIUM("Pr", 59, 140.91, 141, 1_208, 3_793, 6, null, LANTHANIDE, 1.13),
    NEODYMIUM("Nd", 60, 144.24, 142, 1_297, 3_347, 6, null, LANTHANIDE, 1.14),
    PROMETHIUM("Pm", 61, 145, 145, 1_315, 3_273, 6, null, LANTHANIDE, null),
    SAMARIUM("Sm", 62, 150.36, 152, 1_345, 2_067, 6, null, LANTHANIDE, 1.17),
    EUROPIUM("Eu", 63, 151.96, 153, 1_099, 1_802, 6, null, LANTHANIDE, 1.2),
    GADOLINIUM("Gd", 64, 157.25, 158, 1_585, 3_546, 6, null, LANTHANIDE, 1.20),
    TERBIUM("Tb", 65, 158.93, 159, 1_629, 3_503, 6, null, LANTHANIDE, 1.1),
    DYSPROSIUM("Dy", 66, 162.50, 164, 1_680, 2_840, 6, null, LANTHANIDE, 1.22),
    HOLMIUM("Ho", 67, 164.93, 165, 1_734, 2_993, 6, null, LANTHANIDE, 1.23),
    ERBIUM("Er", 68, 167.26, 166, 1_802, 3_141, 6, null, LANTHANIDE, 1.24),
    THULIUM("Tm", 69, 168.93, 169, 1_818, 2_223, 6, null, LANTHANIDE, 1.25),
    YTTERBIUM("Yb", 70, 173.05, 174, 1_097, 1_469, 6, null, LANTHANIDE, 1.1),
    LUTETIUM("Lu", 71, 174.97, 175, 1_925, 3_675, 6, null, LANTHANIDE, 1.27),
    HAFNIUM("Hf", 72, 178.49, 180, 2_506, 4_876, 6, 4, TRANSITION_METAL, 1.3),
    TANTALUM("Ta", 73, 180.95, 181, 3_290, 5_731, 6, 5, TRANSITION_METAL, 1.5),
    /** The highest melting point of all metals. */
    TUNGSTEN("W", 74, 183.84, 184, 3_695, 6_203, 6, 6, TRANSITION_METAL, 2.36),
    RHENIUM("Re", 75, 186.21, 187, 3_459, 5_869, 6, 7, TRANSITION_METAL, 1.9),
    OSMIUM("Os", 76, 190.23, 192, 3_306, 5_285, 6, 8, TRANSITION_METAL, 2.2),
    IRIDIUM("Ir", 77, 192.22, 193, 2_719, 4_403, 6, 9, TRANSITION_METAL, 2.20),
    PLATINUM("Pt", 78, 195.08, 195, 2_041.4, 4_098, 6, 10, TRANSITION_METAL, 2.28),
    GOLD("Au", 79, 196.97, 197, 1_337.33, 3_243, 6, 11, TRANSITION_METAL, 2.54),
    /** The only metal liquid at room temperature. */
    MERCURY("Hg", 80, 200.59, 202, 234.321, 629.88, 6, 12, TRANSITION_METAL, 2.00),
    THALLIUM("Tl", 81, 204.38, 205, 577, 1_746, 6, 13, POST_TRANSITION_METAL, 1.62),
    LEAD("Pb", 82, 207.2, 208, 600.61, 2_022, 6, 14, POST_TRANSITION_METAL, 1.87),
    BISMUTH("Bi", 83, 208.98, 209, 544.7, 1_837, 6, 15, POST_TRANSITION_METAL, 2.02),
    POLONIUM("Po", 84, 209, 209, 527, 1_235, 6, 16, POST_TRANSITION_METAL, 2.0),
    /** Its melting and boiling points are estimates: no visible amount of it has ever been gathered. */
    ASTATINE("At", 85, 210, 210, 575, 610, 6, 17, HALOGEN, 2.2),
    RADON("Rn", 86, 222, 222, 202, 211.5, 6, 18, NOBLE_GAS, 2.2),
    FRANCIUM("Fr", 87, 223, 223, 300, 950, 7, 1, ALKALI_METAL, 0.7),
    RADIUM("Ra", 88, 226, 226, 973, 2_010, 7, 2, ALKALINE_EARTH_METAL, 0.9),
    ACTINIUM("Ac", 89, 227, 227, 1_500, 3_500, 7, null, ACTINIDE, 1.1),
    THORIUM("Th", 90, 232.04, 232, 2_023, 5_061, 7, null, ACTINIDE, 1.3),
    PROTACTINIUM("Pa", 91, 231.04, 231, 1_841, 4_300, 7, null, ACTINIDE, 1.5),
    URANIUM("U", 92, 238.03, 238, 1_405.3, 4_404, 7, null, ACTINIDE, 1.38),
    NEPTUNIUM("Np", 93, 237, 237, 912, 4_447, 7, null, ACTINIDE, 1.36),
    PLUTONIUM("Pu", 94, 244, 244, 912.5, 3_505, 7, null, ACTINIDE, 1.28),
    AMERICIUM("Am", 95, 243, 243, 1_449, 2_880, 7, null, ACTINIDE, 1.3),
    CURIUM("Cm", 96, 247, 247, 1_613, 3_383, 7, null, ACTINIDE, 1.28),
    BERKELIUM("Bk", 97, 247, 247, 1_259, 2_900, 7, null, ACTINIDE, 1.3),
    CALIFORNIUM("Cf", 98, 251, 251, 1_173, Double.NaN, 7, null, ACTINIDE, 1.3),
    EINSTEINIUM("Es", 99, 252, 252, 1_133, Double.NaN, 7, null, ACTINIDE, 1.3),
    FERMIUM("Fm", 100, 257, 257, 1_800, Double.NaN, 7, null, ACTINIDE, 1.3),
    MENDELEVIUM("Md", 101, 258, 258, 1_100, Double.NaN, 7, null, ACTINIDE, 1.3),
    NOBELIUM("No", 102, 259, 259, 1_100, Double.NaN, 7, null, ACTINIDE, 1.3),
    LAWRENCIUM("Lr", 103, 266, 266, 1_900, Double.NaN, 7, null, ACTINIDE, null),
    RUTHERFORDIUM("Rf", 104, 267, 267, Double.NaN, Double.NaN, 7, 4, TRANSITION_METAL, null),
    DUBNIUM("Db", 105, 268, 268, Double.NaN, Double.NaN, 7, 5, TRANSITION_METAL, null),
    SEABORGIUM("Sg", 106, 269, 269, Double.NaN, Double.NaN, 7, 6, TRANSITION_METAL, null),
    BOHRIUM("Bh", 107, 270, 270, Double.NaN, Double.NaN, 7, 7, TRANSITION_METAL, null),
    HASSIUM("Hs", 108, 269, 269, Double.NaN, Double.NaN, 7, 8, TRANSITION_METAL, null),
    MEITNERIUM("Mt", 109, 278, 278, Double.NaN, Double.NaN, 7, 9, UNKNOWN, null),
    DARMSTADTIUM("Ds", 110, 281, 281, Double.NaN, Double.NaN, 7, 10, UNKNOWN, null),
    ROENTGENIUM("Rg", 111, 282, 282, Double.NaN, Double.NaN, 7, 11, UNKNOWN, null),
    COPERNICIUM("Cn", 112, 285, 285, Double.NaN, Double.NaN, 7, 12, UNKNOWN, null),
    NIHONIUM("Nh", 113, 286, 286, Double.NaN, Double.NaN, 7, 13, UNKNOWN, null),
    FLEROVIUM("Fl", 114, 289, 289, Double.NaN, Double.NaN, 7, 14, UNKNOWN, null),
    MOSCOVIUM("Mc", 115, 290, 290, Double.NaN, Double.NaN, 7, 15, UNKNOWN, null),
    LIVERMORIUM("Lv", 116, 293, 293, Double.NaN, Double.NaN, 7, 16, UNKNOWN, null),
    TENNESSINE("Ts", 117, 294, 294, Double.NaN, Double.NaN, 7, 17, UNKNOWN, null),
    /** The heaviest element known, made a few atoms at a time. */
    OGANESSON("Og", 118, 294, 294, Double.NaN, Double.NaN, 7, 18, UNKNOWN, null);

    private static final Map<String, ChemicalElement> BY_SYMBOL = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(ChemicalElement::getSymbol, element -> element));

    private final String symbol;
    private final int atomicNumber;
    private final double standardAtomicWeight;
    private final int mostAbundantMassNumber;
    private final double meltingPointKelvin;
    private final double boilingPointKelvin;
    private final int period;
    /** Null for the lanthanides and actinides, which the 18-column table gives no group number. */
    private final Integer group;
    private final ElementCategory category;
    /** Null when no Pauling electronegativity was measured. */
    private final Double electronegativity;

    ChemicalElement(String symbol, int atomicNumber, double standardAtomicWeight, int mostAbundantMassNumber,
                    double meltingPointKelvin, double boilingPointKelvin, int period, Integer group,
                    ElementCategory category, Double electronegativity) {
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
        this.standardAtomicWeight = standardAtomicWeight;
        this.mostAbundantMassNumber = mostAbundantMassNumber;
        this.meltingPointKelvin = meltingPointKelvin;
        this.boilingPointKelvin = boilingPointKelvin;
        this.period = period;
        this.group = group;
        this.category = category;
        this.electronegativity = electronegativity;
    }

    public static ChemicalElement fromAtomicNumber(int atomicNumber) {
        if (atomicNumber < 1 || atomicNumber > values().length) {
            throw new IllegalArgumentException("unknown element with atomic number " + atomicNumber);
        }
        return values()[atomicNumber - 1];
    }

    public static ChemicalElement fromSymbol(String symbol) {
        ChemicalElement element = BY_SYMBOL.get(symbol);
        if (element == null) {
            throw new IllegalArgumentException("unknown element symbol " + symbol);
        }
        return element;
    }

    public String getSymbol() {
        return symbol;
    }

    /** Number of protons in the nucleus. */
    public int getAtomicNumber() {
        return atomicNumber;
    }

    /** Average atomic mass over the natural isotopes, in daltons (g/mol). */
    public double getStandardAtomicWeight() {
        return standardAtomicWeight;
    }

    /** Mass number of the isotope found most often in nature, or of the longest-lived one if none is stable. */
    public int getMostAbundantMassNumber() {
        return mostAbundantMassNumber;
    }

    /**
     * Temperature at which the solid melts at atmospheric pressure, in kelvins; {@code NaN} when it never
     * solidifies (helium) or was never measured. Equals the boiling point when the solid sublimates (carbon).
     */
    public double getMeltingPointKelvin() {
        return meltingPointKelvin;
    }

    /**
     * Temperature at which the element boils (or sublimates) at atmospheric pressure, in kelvins; {@code NaN} when
     * it was never measured.
     */
    public double getBoilingPointKelvin() {
        return boilingPointKelvin;
    }

    /** Row of the periodic table, 1 to 7. */
    public int getPeriod() {
        return period;
    }

    /** Column of the periodic table, 1 to 18; empty for the lanthanides and actinides. */
    public OptionalInt getGroup() {
        return group == null ? OptionalInt.empty() : OptionalInt.of(group);
    }

    public ElementCategory getCategory() {
        return category;
    }

    /**
     * Block of the periodic table, from the last subshell its electrons fill by the aufbau principle; helium is
     * therefore in the s-block, even though tables draw it above the noble gases.
     */
    public Block getBlock() {
        Subshell last = null;
        for (Subshell subshell : new ElectronConfiguration(atomicNumber).getOccupancy().keySet()) {
            last = subshell;
        }
        return last.getBlock();
    }

    /** Pauling electronegativity; empty for the light noble gases and elements without a measured value. */
    public OptionalDouble getElectronegativity() {
        return electronegativity == null ? OptionalDouble.empty() : OptionalDouble.of(electronegativity);
    }

    /** English name, e.g. "Carbon". */
    public String getDisplayName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
