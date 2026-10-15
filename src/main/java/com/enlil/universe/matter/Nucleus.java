package com.enlil.universe.matter;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The core of an atom, made of protons and neutrons.
 */
@Getter
@EqualsAndHashCode
public class Nucleus {

    private final int protons;
    private final int neutrons;

    public Nucleus(int protons, int neutrons) {
        if (protons < 1 || neutrons < 0) {
            throw new IllegalArgumentException("a nucleus needs at least one proton and no negative neutrons");
        }
        this.protons = protons;
        this.neutrons = neutrons;
    }

    /** Number of protons, which identifies the element. */
    public int getAtomicNumber() {
        return protons;
    }

    /** Number of nucleons (protons and neutrons). */
    public int getMassNumber() {
        return protons + neutrons;
    }

    /** Electric charge, in elementary charges. */
    public int getCharge() {
        return protons * SubatomicParticle.PROTON.getCharge();
    }

    /** Mass of the free nucleons, in kilograms (the nuclear binding energy is ignored). */
    public double getMassKg() {
        return protons * SubatomicParticle.PROTON.getMassKg() + neutrons * SubatomicParticle.NEUTRON.getMassKg();
    }
}
