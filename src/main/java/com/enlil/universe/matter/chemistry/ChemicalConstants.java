package com.enlil.universe.matter.chemistry;

/**
 * Constants of chemistry, in SI units unless stated otherwise.
 */
public final class ChemicalConstants {

    /** Avogadro constant, in mol⁻¹ (exact since 2019). */
    public static final double AVOGADRO = 6.022_140_76e23;
    /** Molar gas constant R, in J·mol⁻¹·K⁻¹. */
    public static final double MOLAR_GAS_CONSTANT = 8.314_462_618;
    /** Ion product of water Kw = [H⁺][OH⁻] at 25 °C, in (mol/L)². */
    public static final double WATER_ION_PRODUCT = 1.0e-14;
    /** IUPAC standard temperature for thermochemistry, 25 °C, in kelvins. */
    public static final double STANDARD_TEMPERATURE_KELVIN = 298.15;
    /** IUPAC standard pressure, 1 bar, in pascals. */
    public static final double STANDARD_PRESSURE_PASCALS = 100_000;

    private ChemicalConstants() {
    }
}
