package com.enlil.universe.matter;

/**
 * Chemical elements, identified by their atomic number.
 */
public enum ChemicalElement {
    HYDROGEN("H", 1, 1.008, 1, 13.99, 20.271),
    /** Never freezes at atmospheric pressure, even at absolute zero. */
    HELIUM("He", 2, 4.002_6, 4, Double.NaN, 4.222),
    LITHIUM("Li", 3, 6.94, 7, 453.65, 1_603),
    BERYLLIUM("Be", 4, 9.012_2, 9, 1_560, 2_742),
    BORON("B", 5, 10.81, 11, 2_349, 4_200),
    /** Graphite sublimates at atmospheric pressure: it goes straight from solid to gas. */
    CARBON("C", 6, 12.011, 12, 3_915, 3_915),
    NITROGEN("N", 7, 14.007, 14, 63.23, 77.355),
    OXYGEN("O", 8, 15.999, 16, 54.36, 90.188),
    FLUORINE("F", 9, 18.998, 19, 53.48, 85.03),
    NEON("Ne", 10, 20.180, 20, 24.56, 27.104),
    SODIUM("Na", 11, 22.990, 23, 370.944, 1_156.090),
    MAGNESIUM("Mg", 12, 24.305, 24, 923, 1_363),
    ALUMINIUM("Al", 13, 26.982, 27, 933.47, 2_743),
    SILICON("Si", 14, 28.085, 28, 1_687, 3_538),
    /** White phosphorus. */
    PHOSPHORUS("P", 15, 30.974, 31, 317.3, 553.7),
    SULFUR("S", 16, 32.06, 32, 388.36, 717.8),
    CHLORINE("Cl", 17, 35.45, 35, 171.6, 239.11),
    ARGON("Ar", 18, 39.95, 40, 83.81, 87.302),
    POTASSIUM("K", 19, 39.098, 39, 336.7, 1_032),
    CALCIUM("Ca", 20, 40.078, 40, 1_115, 1_757),
    IRON("Fe", 26, 55.845, 56, 1_811, 3_134);

    private final String symbol;
    private final int atomicNumber;
    private final double standardAtomicWeight;
    private final int mostAbundantMassNumber;
    private final double meltingPointKelvin;
    private final double boilingPointKelvin;

    ChemicalElement(String symbol, int atomicNumber, double standardAtomicWeight, int mostAbundantMassNumber,
                    double meltingPointKelvin, double boilingPointKelvin) {
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
        this.standardAtomicWeight = standardAtomicWeight;
        this.mostAbundantMassNumber = mostAbundantMassNumber;
        this.meltingPointKelvin = meltingPointKelvin;
        this.boilingPointKelvin = boilingPointKelvin;
    }

    public static ChemicalElement fromAtomicNumber(int atomicNumber) {
        for (ChemicalElement element : values()) {
            if (element.atomicNumber == atomicNumber) {
                return element;
            }
        }
        throw new IllegalArgumentException("unknown element with atomic number " + atomicNumber);
    }

    public static ChemicalElement fromSymbol(String symbol) {
        for (ChemicalElement element : values()) {
            if (element.symbol.equals(symbol)) {
                return element;
            }
        }
        throw new IllegalArgumentException("unknown element symbol " + symbol);
    }

    public String getSymbol() {
        return symbol;
    }

    /** Number of protons in the nucleus. */
    public int getAtomicNumber() {
        return atomicNumber;
    }

    /** Average atomic mass over the natural isotopes, in daltons. */
    public double getStandardAtomicWeight() {
        return standardAtomicWeight;
    }

    /** Mass number of the isotope found most often in nature. */
    public int getMostAbundantMassNumber() {
        return mostAbundantMassNumber;
    }

    /**
     * Temperature at which the solid melts at atmospheric pressure, in kelvins; {@code NaN} when it never
     * solidifies (helium). Equals the boiling point when the solid sublimates (carbon).
     */
    public double getMeltingPointKelvin() {
        return meltingPointKelvin;
    }

    /** Temperature at which the element boils (or sublimates) at atmospheric pressure, in kelvins. */
    public double getBoilingPointKelvin() {
        return boilingPointKelvin;
    }

    /** English name, e.g. "Carbon". */
    public String getDisplayName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
