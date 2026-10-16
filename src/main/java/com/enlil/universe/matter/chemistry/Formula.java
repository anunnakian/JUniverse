package com.enlil.universe.matter.chemistry;

import com.enlil.universe.matter.ChemicalElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A chemical formula: how many atoms of each element, and the net charge. Two formulas with the same counts are
 * equal whatever the order they were written in, so {@code OH2} equals {@code H2O}.
 */
public final class Formula {

    private final Map<ChemicalElement, Integer> counts;
    private final int charge;

    public Formula(Map<ChemicalElement, Integer> counts, int charge) {
        if (counts.isEmpty()) {
            throw new IllegalArgumentException("a formula needs at least one element");
        }
        if (counts.values().stream().anyMatch(count -> count <= 0)) {
            throw new IllegalArgumentException("atom counts must be positive");
        }
        this.counts = Collections.unmodifiableMap(new EnumMap<>(counts));
        this.charge = charge;
    }

    /**
     * Parses a formula: element symbols with optional counts ({@code C6H12O6}), nested groups in round or square
     * brackets ({@code K4[Fe(CN)6]}), hydrates joined by {@code ·} or {@code *} ({@code CuSO4·5H2O}) and an optional
     * trailing charge ({@code SO4^2-}, {@code Na^+}).
     */
    public static Formula parse(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("empty formula");
        }
        return new Parser(text.strip()).parse();
    }

    public int count(ChemicalElement element) {
        return counts.getOrDefault(element, 0);
    }

    /** The elements of this formula, in atomic number order. */
    public Set<ChemicalElement> getElements() {
        return counts.keySet();
    }

    /** The atom count of each element. */
    public Map<ChemicalElement, Integer> getCounts() {
        return counts;
    }

    /** Net charge, in elementary charges. */
    public int getCharge() {
        return charge;
    }

    /** Molar mass, in g/mol, from the standard atomic weights. */
    public double molarMassGramsPerMole() {
        return counts.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * entry.getKey().getStandardAtomicWeight())
                .sum();
    }

    /** Share of the mass carried by an element, between 0 and 1. */
    public double massFraction(ChemicalElement element) {
        return count(element) * element.getStandardAtomicWeight() / molarMassGramsPerMole();
    }

    /** Hill notation: carbon first, then hydrogen, then the rest alphabetically (all alphabetical without carbon). */
    @Override
    public String toString() {
        List<ChemicalElement> order = new ArrayList<>(counts.keySet());
        boolean organic = counts.containsKey(ChemicalElement.CARBON);
        order.sort(Comparator.comparing((ChemicalElement element) -> !organic ? 0
                        : element == ChemicalElement.CARBON ? 0 : element == ChemicalElement.HYDROGEN ? 1 : 2)
                .thenComparing(ChemicalElement::getSymbol));
        StringBuilder text = new StringBuilder();
        for (ChemicalElement element : order) {
            text.append(element.getSymbol());
            if (counts.get(element) > 1) {
                text.append(counts.get(element));
            }
        }
        if (charge != 0) {
            text.append('^');
            if (Math.abs(charge) > 1) {
                text.append(Math.abs(charge));
            }
            text.append(charge > 0 ? '+' : '-');
        }
        return text.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Formula formula && charge == formula.charge && counts.equals(formula.counts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(counts, charge);
    }

    /** A recursive-descent parser over the formula text. */
    private static final class Parser {

        private final String text;
        private int position;

        Parser(String text) {
            this.text = text;
        }

        Formula parse() {
            Map<ChemicalElement, Integer> counts = new EnumMap<>(ChemicalElement.class);
            add(counts, group(), 1);
            while (peek() == '·' || peek() == '*') {
                position++;
                int multiplier = number(1);
                add(counts, group(), multiplier);
            }
            int charge = 0;
            if (peek() == '^') {
                position++;
                int magnitude = number(1);
                char sign = peek();
                if (sign != '+' && sign != '-') {
                    throw error("a charge needs a sign");
                }
                position++;
                charge = sign == '+' ? magnitude : -magnitude;
            }
            if (position < text.length()) {
                throw error("unexpected character '" + text.charAt(position) + "'");
            }
            return new Formula(counts, charge);
        }

        /** A sequence of elements and bracketed groups, each with its count. */
        private Map<ChemicalElement, Integer> group() {
            Map<ChemicalElement, Integer> counts = new EnumMap<>(ChemicalElement.class);
            while (position < text.length()) {
                char next = peek();
                if (Character.isUpperCase(next)) {
                    ChemicalElement element = symbol();
                    add(counts, Map.of(element, 1), number(1));
                } else if (next == '(' || next == '[') {
                    char closing = next == '(' ? ')' : ']';
                    position++;
                    Map<ChemicalElement, Integer> inner = group();
                    if (peek() != closing) {
                        throw error("missing '" + closing + "'");
                    }
                    position++;
                    add(counts, inner, number(1));
                } else {
                    break;
                }
            }
            if (counts.isEmpty()) {
                throw error("expected an element");
            }
            return counts;
        }

        private ChemicalElement symbol() {
            int start = position++;
            while (position < text.length() && Character.isLowerCase(text.charAt(position))) {
                position++;
            }
            return ChemicalElement.fromSymbol(text.substring(start, position));
        }

        /** An optional positive count; the default when none is written. */
        private int number(int absent) {
            int start = position;
            while (position < text.length() && Character.isDigit(text.charAt(position))) {
                position++;
            }
            if (start == position) {
                return absent;
            }
            int value = Integer.parseInt(text.substring(start, position));
            if (value == 0) {
                throw error("a count must not be zero");
            }
            return value;
        }

        private char peek() {
            return position < text.length() ? text.charAt(position) : '\0';
        }

        private IllegalArgumentException error(String message) {
            return new IllegalArgumentException(message + " at position " + position + " in " + text);
        }

        private static void add(Map<ChemicalElement, Integer> into, Map<ChemicalElement, Integer> counts,
                                int multiplier) {
            counts.forEach((element, count) -> into.merge(element, count * multiplier, Integer::sum));
        }
    }
}
