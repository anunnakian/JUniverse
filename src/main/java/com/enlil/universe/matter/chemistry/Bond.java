package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.ChemicalElement;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;

import static com.enlil.universe.matter.ChemicalElement.*;

/**
 * A covalent bond between two labeled atoms of a molecule. A label starts with its element's symbol, then anything
 * that tells it apart: {@code "C"}, {@code "O1"}, {@code "Cl2"}.
 */
@Getter
public class Bond {

    /** Textbook average bond enthalpies, in kJ/mol, by element pair and order. */
    private static final Map<Key, Double> AVERAGE_ENERGIES_KJ_PER_MOLE = Map.ofEntries(
            energy(HYDROGEN, HYDROGEN, BondOrder.SINGLE, 436),
            energy(CARBON, HYDROGEN, BondOrder.SINGLE, 413),
            energy(CARBON, CARBON, BondOrder.SINGLE, 348),
            energy(CARBON, CARBON, BondOrder.DOUBLE, 614),
            energy(CARBON, CARBON, BondOrder.TRIPLE, 839),
            energy(CARBON, OXYGEN, BondOrder.SINGLE, 358),
            energy(CARBON, OXYGEN, BondOrder.DOUBLE, 745),
            energy(OXYGEN, HYDROGEN, BondOrder.SINGLE, 463),
            energy(OXYGEN, OXYGEN, BondOrder.DOUBLE, 498),
            energy(NITROGEN, HYDROGEN, BondOrder.SINGLE, 391),
            energy(NITROGEN, NITROGEN, BondOrder.TRIPLE, 945),
            energy(HYDROGEN, CHLORINE, BondOrder.SINGLE, 431),
            energy(CARBON, CHLORINE, BondOrder.SINGLE, 328),
            energy(CHLORINE, CHLORINE, BondOrder.SINGLE, 242));

    private final String firstLabel;
    private final String secondLabel;
    private final ChemicalElement firstElement;
    private final ChemicalElement secondElement;
    private final BondOrder order;

    public Bond(String firstLabel, String secondLabel, BondOrder order) {
        if (firstLabel.equals(secondLabel)) {
            throw new IllegalArgumentException("an atom cannot bond to itself: " + firstLabel);
        }
        this.firstLabel = firstLabel;
        this.secondLabel = secondLabel;
        this.firstElement = elementOf(firstLabel);
        this.secondElement = elementOf(secondLabel);
        this.order = order;
    }

    /** The element named by an atom label: its leading capital letter and the lowercase letters after it. */
    public static ChemicalElement elementOf(String label) {
        if (label == null || label.isEmpty() || !Character.isUpperCase(label.charAt(0))) {
            throw new IllegalArgumentException("an atom label starts with an element symbol: " + label);
        }
        int end = 1;
        while (end < label.length() && Character.isLowerCase(label.charAt(end))) {
            end++;
        }
        return ChemicalElement.fromSymbol(label.substring(0, end));
    }

    /** Whether this bond links the given atom. */
    public boolean involves(String label) {
        return firstLabel.equals(label) || secondLabel.equals(label);
    }

    /** The atom at the other end of the bond from the given one. */
    public String otherEnd(String label) {
        return firstLabel.equals(label) ? secondLabel : firstLabel;
    }

    /**
     * Absolute difference between the Pauling electronegativities of the two atoms.
     *
     * @throws IllegalStateException if either element has no measured electronegativity
     */
    public double electronegativityDifference() {
        return Math.abs(electronegativity(firstElement) - electronegativity(secondElement));
    }

    public BondType getType() {
        return BondType.fromElectronegativityDifference(electronegativityDifference());
    }

    /** Average bond enthalpy, in kJ/mol, from a textbook table; empty for the pairs it doesn't list. */
    public OptionalDouble averageEnergyKjPerMole() {
        Double energy = AVERAGE_ENERGIES_KJ_PER_MOLE.get(new Key(EnumSet.of(firstElement, secondElement), order));
        return energy == null ? OptionalDouble.empty() : OptionalDouble.of(energy);
    }

    @Override
    public String toString() {
        String symbol = switch (order) {
            case SINGLE -> "-";
            case DOUBLE -> "=";
            case TRIPLE -> "≡";
        };
        return firstLabel + symbol + secondLabel;
    }

    private static double electronegativity(ChemicalElement element) {
        return element.getElectronegativity().orElseThrow(() ->
                new IllegalStateException(element.getDisplayName() + " has no measured electronegativity"));
    }

    private static Map.Entry<Key, Double> energy(ChemicalElement a, ChemicalElement b, BondOrder order,
                                                 double kjPerMole) {
        return Map.entry(new Key(EnumSet.of(a, b), order), kjPerMole);
    }

    /** An unordered pair of elements (a singleton for a homonuclear bond) and a bond order. */
    private record Key(Set<ChemicalElement> elements, BondOrder order) {
    }
}
