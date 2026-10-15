package com.enlil.universe.matter;

/**
 * The particles an atom is made of.
 */
public enum SubatomicParticle {
    PROTON(AtomicConstants.PROTON_MASS, 1),
    NEUTRON(AtomicConstants.NEUTRON_MASS, 0),
    ELECTRON(AtomicConstants.ELECTRON_MASS, -1);

    private final double massKg;
    private final int charge;

    SubatomicParticle(double massKg, int charge) {
        this.massKg = massKg;
        this.charge = charge;
    }

    public double getMassKg() {
        return massKg;
    }

    /** Electric charge, in elementary charges. */
    public int getCharge() {
        return charge;
    }

    /** Electric charge, in coulombs. */
    public double getChargeCoulombs() {
        return charge * AtomicConstants.ELEMENTARY_CHARGE;
    }
}
