package com.enlil.universe.cosmology;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * How much the light of a source is stretched on its way to us, z = λ_observed/λ_emitted − 1. Negative values are
 * blueshifts, from sources coming closer (Andromeda); the {@link CosmologicalModel} only takes z ≥ 0.
 */
@Getter
@EqualsAndHashCode
public final class Redshift {

    private final double z;

    private Redshift(double z) {
        if (!(z > -1)) {
            throw new IllegalArgumentException("a redshift must be greater than −1");
        }
        this.z = z;
    }

    public static Redshift of(double z) {
        return new Redshift(z);
    }

    /** The redshift of a spectral line observed at one wavelength and emitted at another. */
    public static Redshift fromWavelengths(double observedNanometers, double emittedNanometers) {
        if (observedNanometers <= 0 || emittedNanometers <= 0) {
            throw new IllegalArgumentException("wavelengths must be positive");
        }
        return new Redshift(observedNanometers / emittedNanometers - 1);
    }

    /** Whether the source is coming closer. */
    public boolean isBlueshift() {
        return z < 0;
    }

    /** Factor 1+z by which wavelengths are stretched. */
    public double stretchFactor() {
        return 1 + z;
    }

    /** The wavelength at which a line emitted at a given wavelength is observed, in nanometers. */
    public double observedWavelengthNanometers(double emittedNanometers) {
        return emittedNanometers * stretchFactor();
    }

    @Override
    public String toString() {
        return "z = " + z;
    }
}
