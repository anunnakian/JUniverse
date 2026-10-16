package com.enlil.universe.cosmology;

import java.util.OptionalDouble;

import static com.enlil.universe.celestial.PhysicalConstants.JULIAN_YEAR;

/**
 * The epochs of the universe's history, in chronological order and contiguous, timed in seconds after the Big Bang
 * (start included, end excluded). Boundaries are textbook values, only orders of magnitude for the earliest epochs.
 */
public enum CosmicEpoch {
    PLANCK(0, 1e-43, "Physics as we know it breaks down; gravity is as strong as the other forces"),
    GRAND_UNIFICATION(1e-43, 1e-36, "Gravity has split off; the strong, weak and electromagnetic forces are one"),
    INFLATION(1e-36, 1e-32, "Space grows by a factor of at least 10^26, smoothing and flattening the universe"),
    ELECTROWEAK(1e-32, 1e-12, "The strong force has split off; W and Z bosons and the Higgs roam freely"),
    QUARK(1e-12, 1e-6, "All four forces are apart; a soup of quarks, gluons and leptons"),
    HADRON(1e-6, 1, "Quarks are bound into protons and neutrons"),
    LEPTON(1, 10, "Electrons and positrons dominate, then annihilate"),
    NUCLEOSYNTHESIS(10, 20 * 60, "Protons and neutrons fuse into helium, deuterium and a trace of lithium"),
    PHOTON(20 * 60, 377_000 * JULIAN_YEAR, "A hot plasma of nuclei, electrons and light"),
    DARK_AGES(377_000 * JULIAN_YEAR, 150e6 * JULIAN_YEAR,
            "Atoms have formed and the light of recombination has cooled; no star shines yet"),
    REIONIZATION(150e6 * JULIAN_YEAR, 1e9 * JULIAN_YEAR, "The first stars and quasars ionize the gas again"),
    GALAXY_FORMATION(1e9 * JULIAN_YEAR, 10.3e9 * JULIAN_YEAR, "Galaxies grow, merge and gather into clusters"),
    DARK_ENERGY_DOMINATED(10.3e9 * JULIAN_YEAR, Double.POSITIVE_INFINITY,
            "Dark energy has overtaken matter, and the expansion accelerates");

    private final double startSeconds;
    private final double endSeconds;
    private final String description;

    CosmicEpoch(double startSeconds, double endSeconds, String description) {
        this.startSeconds = startSeconds;
        this.endSeconds = endSeconds;
        this.description = description;
    }

    /** The epoch at a given time after the Big Bang, in seconds. */
    public static CosmicEpoch at(double secondsAfterBigBang) {
        if (!(secondsAfterBigBang >= 0)) {
            throw new IllegalArgumentException("nothing came before the Big Bang");
        }
        for (CosmicEpoch epoch : values()) {
            if (secondsAfterBigBang < epoch.endSeconds) {
                return epoch;
            }
        }
        return DARK_ENERGY_DOMINATED;
    }

    /** The epoch at a given time after the Big Bang, in years. */
    public static CosmicEpoch atYears(double yearsAfterBigBang) {
        return at(yearsAfterBigBang * JULIAN_YEAR);
    }

    /** When this epoch began, in seconds after the Big Bang. */
    public double getStartSeconds() {
        return startSeconds;
    }

    /** When this epoch ended, in seconds after the Big Bang; empty for the epoch we live in. */
    public OptionalDouble getEndSeconds() {
        return Double.isInfinite(endSeconds) ? OptionalDouble.empty() : OptionalDouble.of(endSeconds);
    }

    public String getDescription() {
        return description;
    }
}
