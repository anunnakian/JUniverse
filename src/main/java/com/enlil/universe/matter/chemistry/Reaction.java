package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.ChemicalElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A balanced chemical reaction: reactants turn into products, each with its stoichiometric coefficient, conserving
 * every element and the total charge.
 */
public final class Reaction {

    private final Map<Compound, Integer> reactants;
    private final Map<Compound, Integer> products;

    /**
     * A reaction with explicit coefficients.
     *
     * @throws IllegalArgumentException if a coefficient isn't positive, a compound appears on both sides, or an
     *                                  element or the charge isn't conserved
     */
    public Reaction(Map<Compound, Integer> reactants, Map<Compound, Integer> products) {
        if (reactants.isEmpty() || products.isEmpty()) {
            throw new IllegalArgumentException("a reaction needs reactants and products");
        }
        if (reactants.values().stream().anyMatch(coefficient -> coefficient <= 0)
                || products.values().stream().anyMatch(coefficient -> coefficient <= 0)) {
            throw new IllegalArgumentException("coefficients must be positive");
        }
        if (reactants.keySet().stream().anyMatch(products::containsKey)) {
            throw new IllegalArgumentException("a compound cannot be both a reactant and a product");
        }
        for (ChemicalElement element : elementsOf(reactants.keySet(), products.keySet())) {
            if (atoms(reactants, element) != atoms(products, element)) {
                throw new IllegalArgumentException(element.getDisplayName() + " is not conserved");
            }
        }
        if (charge(reactants) != charge(products)) {
            throw new IllegalArgumentException("charge is not conserved");
        }
        this.reactants = Collections.unmodifiableMap(new LinkedHashMap<>(reactants));
        this.products = Collections.unmodifiableMap(new LinkedHashMap<>(products));
    }

    /**
     * Finds the smallest whole coefficients that balance a reaction: the null space of the matrix with one row per
     * element plus one for the charge and one column per compound (products negated), in exact fractions.
     *
     * @throws IllegalArgumentException if no positive balancing exists, or several independent ones do
     */
    public static Reaction balance(List<Compound> reactants, List<Compound> products) {
        List<Compound> compounds = new ArrayList<>(reactants);
        compounds.addAll(products);
        List<ChemicalElement> elements = new ArrayList<>(elementsOf(reactants, products));
        long[][] matrix = new long[elements.size() + 1][compounds.size()];
        for (int column = 0; column < compounds.size(); column++) {
            Formula formula = compounds.get(column).getFormula();
            int sign = column < reactants.size() ? 1 : -1;
            for (int row = 0; row < elements.size(); row++) {
                matrix[row][column] = sign * formula.count(elements.get(row));
            }
            matrix[elements.size()][column] = sign * formula.getCharge();
        }
        List<long[]> nullSpace = new RationalMatrix(matrix).nullSpace();
        if (nullSpace.size() > 1) {
            throw new IllegalArgumentException("the reaction has several independent balancings");
        }
        long[] coefficients = nullSpace.isEmpty() ? new long[0] : nullSpace.get(0);
        if (coefficients.length > 0 && coefficients[0] < 0) {
            for (int i = 0; i < coefficients.length; i++) {
                coefficients[i] = -coefficients[i];
            }
        }
        if (coefficients.length == 0 || Arrays.stream(coefficients).anyMatch(c -> c <= 0)) {
            throw new IllegalArgumentException("the reaction cannot be balanced");
        }
        Map<Compound, Integer> reactantCoefficients = new LinkedHashMap<>();
        Map<Compound, Integer> productCoefficients = new LinkedHashMap<>();
        for (int i = 0; i < compounds.size(); i++) {
            (i < reactants.size() ? reactantCoefficients : productCoefficients)
                    .put(compounds.get(i), Math.toIntExact(coefficients[i]));
        }
        return new Reaction(reactantCoefficients, productCoefficients);
    }

    /** Reactants with their coefficients, in the order given. */
    public Map<Compound, Integer> getReactants() {
        return reactants;
    }

    /** Products with their coefficients, in the order given. */
    public Map<Compound, Integer> getProducts() {
        return products;
    }

    /** The coefficient of a compound, on whichever side it is; 0 if it takes no part. */
    public int coefficientOf(Compound compound) {
        return reactants.getOrDefault(compound, products.getOrDefault(compound, 0));
    }

    /**
     * Standard reaction enthalpy, in kJ per mole of reaction as written, by Hess's law:
     * Σ ν·ΔHf(products) − Σ ν·ΔHf(reactants).
     *
     * @throws IllegalStateException if a compound has no known enthalpy of formation
     */
    public double enthalpyKjPerMole() {
        return enthalpy(products) - enthalpy(reactants);
    }

    /** Whether the reaction releases heat. */
    public boolean isExothermic() {
        return enthalpyKjPerMole() < 0;
    }

    /**
     * Mass of a compound made or used when a given mass of another reacts, in grams, from the coefficients.
     *
     * @throws IllegalArgumentException if either compound takes no part in this reaction
     */
    public double massGrams(Compound given, double givenGrams, Compound wanted) {
        int givenCoefficient = coefficientOf(given);
        int wantedCoefficient = coefficientOf(wanted);
        if (givenCoefficient == 0 || wantedCoefficient == 0) {
            throw new IllegalArgumentException("both compounds must take part in the reaction");
        }
        double moles = givenGrams / given.molarMassGramsPerMole() * wantedCoefficient / givenCoefficient;
        return moles * wanted.molarMassGramsPerMole();
    }

    /** The equation, e.g. {@code CH4 + 2 O2 -> CO2 + 2 H2O}. */
    @Override
    public String toString() {
        return side(reactants) + " -> " + side(products);
    }

    private static String side(Map<Compound, Integer> compounds) {
        return compounds.entrySet().stream()
                .map(entry -> (entry.getValue() == 1 ? "" : entry.getValue() + " ") + entry.getKey().getFormula())
                .collect(Collectors.joining(" + "));
    }

    private static double enthalpy(Map<Compound, Integer> compounds) {
        return compounds.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * entry.getKey().getEnthalpyOfFormationKjPerMole()
                        .orElseThrow(() -> new IllegalStateException(
                                entry.getKey().getName() + " has no known enthalpy of formation")))
                .sum();
    }

    private static Set<ChemicalElement> elementsOf(Iterable<Compound> reactants, Iterable<Compound> products) {
        Set<ChemicalElement> elements = EnumSet.noneOf(ChemicalElement.class);
        reactants.forEach(compound -> elements.addAll(compound.getFormula().getElements()));
        products.forEach(compound -> elements.addAll(compound.getFormula().getElements()));
        return elements;
    }

    private static long atoms(Map<Compound, Integer> compounds, ChemicalElement element) {
        return compounds.entrySet().stream()
                .mapToLong(entry -> (long) entry.getValue() * entry.getKey().getFormula().count(element))
                .sum();
    }

    private static long charge(Map<Compound, Integer> compounds) {
        return compounds.entrySet().stream()
                .mapToLong(entry -> (long) entry.getValue() * entry.getKey().getFormula().getCharge())
                .sum();
    }
}
