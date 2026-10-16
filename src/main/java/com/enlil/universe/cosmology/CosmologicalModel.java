package com.enlil.universe.cosmology;

import com.enlil.universe.celestial.PhysicalConstants;
import lombok.Getter;

import java.util.function.DoubleUnaryOperator;

/**
 * A flat ΛCDM model of the expanding universe: radiation, matter (dark and ordinary) and dark energy, with today's
 * expansion rate H₀. Dark matter and dark energy are parameters here, not physics. Integrals use composite Simpson's
 * rule over the scale factor a = 1/(1+z), where every integrand stays finite down to a = 0.
 */
@Getter
public class CosmologicalModel {

    /** One Julian year, in seconds. */
    public static final double SECONDS_PER_YEAR = PhysicalConstants.JULIAN_YEAR;

    private static final int INTERVALS = 10_000;
    private static final double FLATNESS_TOLERANCE = 0.01;
    private static final double SPEED_OF_LIGHT_KM_PER_SECOND = PhysicalConstants.SPEED_OF_LIGHT / 1_000;

    private final double hubbleConstantKmPerSecondPerMegaparsec;
    /** Ω_m, today's density of matter as a share of the critical density. */
    private final double matterDensity;
    /** Ω_Λ, today's density of dark energy as a share of the critical density. */
    private final double darkEnergyDensity;
    /** Ω_r, today's density of radiation (photons and neutrinos) as a share of the critical density. */
    private final double radiationDensity;
    private final double cmbTemperatureKelvin;

    /**
     * @throws IllegalArgumentException if a parameter isn't positive, or the densities don't sum to 1 within 0.01
     */
    public CosmologicalModel(double hubbleConstantKmPerSecondPerMegaparsec, double matterDensity,
                             double darkEnergyDensity, double radiationDensity, double cmbTemperatureKelvin) {
        if (hubbleConstantKmPerSecondPerMegaparsec <= 0 || matterDensity <= 0 || darkEnergyDensity <= 0
                || radiationDensity <= 0 || cmbTemperatureKelvin <= 0) {
            throw new IllegalArgumentException("cosmological parameters must be positive");
        }
        if (Math.abs(matterDensity + darkEnergyDensity + radiationDensity - 1) > FLATNESS_TOLERANCE) {
            throw new IllegalArgumentException("the densities of a flat universe must sum to 1");
        }
        this.hubbleConstantKmPerSecondPerMegaparsec = hubbleConstantKmPerSecondPerMegaparsec;
        this.matterDensity = matterDensity;
        this.darkEnergyDensity = darkEnergyDensity;
        this.radiationDensity = radiationDensity;
        this.cmbTemperatureKelvin = cmbTemperatureKelvin;
    }

    /** E(z) = H(z)/H₀ = √(Ω_r(1+z)⁴ + Ω_m(1+z)³ + Ω_Λ). */
    public double expansionRateRatio(double z) {
        checkRedshift(z);
        double stretch = 1 + z;
        return Math.sqrt(radiationDensity * Math.pow(stretch, 4) + matterDensity * Math.pow(stretch, 3)
                + darkEnergyDensity);
    }

    /** The expansion rate H(z) at redshift z, in km/s/Mpc. */
    public double hubbleParameterAt(double z) {
        return hubbleConstantKmPerSecondPerMegaparsec * expansionRateRatio(z);
    }

    /** 1/H₀, the age the universe would have if it had always expanded at today's rate, in years. */
    public double hubbleTimeYears() {
        return 1 / hubbleConstantPerSecond() / SECONDS_PER_YEAR;
    }

    /** Age of the universe today, in years. */
    public double ageYears() {
        return ageAtYears(0);
    }

    /** Age of the universe when light of redshift z was emitted, ∫₀^a da / (a·H(a)), in years. */
    public double ageAtYears(double z) {
        double a = scaleFactorAt(z);
        // 1/(a·H) = a / (H₀·√(Ω_r + Ω_m·a + Ω_Λ·a⁴)), finite at a = 0
        return simpson(x -> x / reducedExpansion(x), 0, a) / hubbleConstantPerSecond() / SECONDS_PER_YEAR;
    }

    /** How long ago light of redshift z was emitted, in years. */
    public double lookbackTimeYears(double z) {
        return ageYears() - ageAtYears(z);
    }

    /** Size of the universe at redshift z relative to today, 1/(1+z). */
    public double scaleFactorAt(double z) {
        checkRedshift(z);
        return 1 / (1 + z);
    }

    /** Today's distance to a source of redshift z, c ∫₀^z dz / H(z), in megaparsecs. */
    public double comovingDistanceMegaparsecs(double z) {
        double a = scaleFactorAt(z);
        // dz / E(z) = da / (a²·E(a)) = da / √(Ω_r + Ω_m·a + Ω_Λ·a⁴)
        return SPEED_OF_LIGHT_KM_PER_SECOND / hubbleConstantKmPerSecondPerMegaparsec
                * simpson(x -> 1 / reducedExpansion(x), a, 1);
    }

    /** Distance that makes a source of redshift z as faint as it looks, (1+z)·D_C, in megaparsecs. */
    public double luminosityDistanceMegaparsecs(double z) {
        return (1 + z) * comovingDistanceMegaparsecs(z);
    }

    /** Distance that makes a source of redshift z as small as it looks, D_C/(1+z), in megaparsecs. */
    public double angularDiameterDistanceMegaparsecs(double z) {
        return comovingDistanceMegaparsecs(z) / (1 + z);
    }

    /** Speed at which the expansion carries away a galaxy at a given distance, H₀·d (Hubble–Lemaître law), in km/s. */
    public double recessionVelocityKmPerSecond(double distanceMegaparsecs) {
        return hubbleConstantKmPerSecondPerMegaparsec * distanceMegaparsecs;
    }

    /** Temperature of the cosmic background radiation at redshift z, T₀(1+z), in kelvins. */
    public double cmbTemperatureAtKelvin(double z) {
        checkRedshift(z);
        return cmbTemperatureKelvin * (1 + z);
    }

    /**
     * The redshift of light emitted when the universe had a given age, the inverse of {@link #ageAtYears}, found by
     * bisection on the scale factor.
     *
     * @param ageYears in (0, age today]
     */
    public double redshiftAtAge(double ageYears) {
        double today = ageYears();
        if (ageYears <= 0 || ageYears > today) {
            throw new IllegalArgumentException("age must be between the Big Bang and today");
        }
        double low = 0;
        double high = 1;
        for (int i = 0; i < 100 && high - low > 1e-15; i++) {
            double middle = (low + high) / 2;
            if (ageAtYears(1 / middle - 1) < ageYears) {
                low = middle;
            } else {
                high = middle;
            }
        }
        return Math.max(0, 1 / ((low + high) / 2) - 1);
    }

    /** Redshift when dark energy overtook matter, (Ω_Λ/Ω_m)^⅓ − 1. */
    public double matterDarkEnergyEqualityRedshift() {
        return Math.cbrt(darkEnergyDensity / matterDensity) - 1;
    }

    /** H₀ in s⁻¹. */
    private double hubbleConstantPerSecond() {
        return hubbleConstantKmPerSecondPerMegaparsec * 1_000 / PhysicalConstants.MEGAPARSEC;
    }

    /** a²·E(a) = √(Ω_r + Ω_m·a + Ω_Λ·a⁴). */
    private double reducedExpansion(double a) {
        return Math.sqrt(radiationDensity + matterDensity * a + darkEnergyDensity * a * a * a * a);
    }

    private static void checkRedshift(double z) {
        if (!(z >= 0)) {
            throw new IllegalArgumentException("the model takes redshifts of 0 or more, not " + z);
        }
    }

    private static double simpson(DoubleUnaryOperator f, double from, double to) {
        double step = (to - from) / INTERVALS;
        double sum = f.applyAsDouble(from) + f.applyAsDouble(to);
        for (int i = 1; i < INTERVALS; i++) {
            sum += (i % 2 == 0 ? 2 : 4) * f.applyAsDouble(from + i * step);
        }
        return sum * step / 3;
    }
}
