package com.enlil.universe.matter;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * An atom: a nucleus surrounded by electrons. When the electrons don't balance the protons, it is an ion.
 */
@Getter
@EqualsAndHashCode
public class Atom {

    private final Nucleus nucleus;
    private final int electrons;

    public Atom(Nucleus nucleus, int electrons) {
        if (electrons < 0) {
            throw new IllegalArgumentException("electron count must not be negative");
        }
        ChemicalElement.fromAtomicNumber(nucleus.getAtomicNumber());
        this.nucleus = nucleus;
        this.electrons = electrons;
    }

    /** A neutral atom of the most abundant isotope of an element. */
    public static Atom of(ChemicalElement element) {
        return isotope(element, element.getMostAbundantMassNumber());
    }

    /** A neutral atom of a given isotope, e.g. carbon-14. */
    public static Atom isotope(ChemicalElement element, int massNumber) {
        int protons = element.getAtomicNumber();
        return new Atom(new Nucleus(protons, massNumber - protons), protons);
    }

    public ChemicalElement getElement() {
        return ChemicalElement.fromAtomicNumber(nucleus.getAtomicNumber());
    }

    public int getProtons() {
        return nucleus.getProtons();
    }

    public int getNeutrons() {
        return nucleus.getNeutrons();
    }

    public int getMassNumber() {
        return nucleus.getMassNumber();
    }

    /** Net electric charge, in elementary charges. */
    public int getCharge() {
        return nucleus.getCharge() + electrons * SubatomicParticle.ELECTRON.getCharge();
    }

    public boolean isIon() {
        return getCharge() != 0;
    }

    /** A positive ion, which lost electrons. */
    public boolean isCation() {
        return getCharge() > 0;
    }

    /** A negative ion, which gained electrons. */
    public boolean isAnion() {
        return getCharge() < 0;
    }

    /** Whether this atom has the same number of protons as another one, but not the same number of neutrons. */
    public boolean isIsotopeOf(Atom other) {
        return getProtons() == other.getProtons() && getNeutrons() != other.getNeutrons();
    }

    /** This atom with one electron less. */
    public Atom ionize() {
        if (electrons == 0) {
            throw new IllegalStateException("no electron left to remove");
        }
        return new Atom(nucleus, electrons - 1);
    }

    /** This atom with one electron more. */
    public Atom gainElectron() {
        return new Atom(nucleus, electrons + 1);
    }

    /** Mass of the nucleons and electrons, in kilograms (the binding energies are ignored). */
    public double getMassKg() {
        return nucleus.getMassKg() + electrons * SubatomicParticle.ELECTRON.getMassKg();
    }

    /** Mass, in daltons. */
    public double getMassDaltons() {
        return getMassKg() / AtomicConstants.DALTON;
    }

    public ElectronConfiguration getElectronConfiguration() {
        return new ElectronConfiguration(electrons);
    }

    public int getValenceElectrons() {
        return getElectronConfiguration().getValenceElectrons();
    }

    /** Chemical symbol with its charge, e.g. "Na", "Na+", "Mg2+" or "Cl-". */
    public String getIonSymbol() {
        int charge = getCharge();
        if (charge == 0) {
            return getElement().getSymbol();
        }
        String magnitude = Math.abs(charge) == 1 ? "" : String.valueOf(Math.abs(charge));
        return getElement().getSymbol() + magnitude + (charge > 0 ? "+" : "-");
    }

    /** Isotope name, e.g. "Carbon-14". */
    public String getIsotopeName() {
        return getElement().getDisplayName() + "-" + getMassNumber();
    }

    @Override
    public String toString() {
        return getIsotopeName() + " (" + getIonSymbol() + ")";
    }
}
