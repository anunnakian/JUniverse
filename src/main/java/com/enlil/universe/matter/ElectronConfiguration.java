package com.enlil.universe.matter;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * How the electrons of an atom spread over its subshells, filled with the aufbau principle.
 * Exceptions to the rule (like chromium or copper) are not modeled.
 */
public class ElectronConfiguration {

    private final Map<Subshell, Integer> occupancy = new EnumMap<>(Subshell.class);

    public ElectronConfiguration(int electrons) {
        if (electrons < 0) {
            throw new IllegalArgumentException("electron count must not be negative");
        }
        int remaining = electrons;
        for (Subshell subshell : Subshell.values()) {
            if (remaining == 0) {
                break;
            }
            int filled = Math.min(remaining, subshell.getCapacity());
            occupancy.put(subshell, filled);
            remaining -= filled;
        }
        if (remaining > 0) {
            throw new IllegalArgumentException("too many electrons: " + electrons);
        }
    }

    /** Electrons per occupied subshell, in filling order. */
    public Map<Subshell, Integer> getOccupancy() {
        return Collections.unmodifiableMap(occupancy);
    }

    /** Number of shells holding at least one electron. */
    public int getShellCount() {
        return occupancy.keySet().stream().mapToInt(Subshell::getShell).max().orElse(0);
    }

    /** Electrons in the outermost shell. */
    public int getValenceElectrons() {
        int outermost = getShellCount();
        return occupancy.entrySet().stream()
                .filter(entry -> entry.getKey().getShell() == outermost)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    /** Whether every occupied subshell is full, like in noble gases. */
    public boolean isClosedShell() {
        return occupancy.entrySet().stream().allMatch(entry -> entry.getValue() == entry.getKey().getCapacity());
    }

    /** Standard notation, e.g. "1s2 2s2 2p6". */
    @Override
    public String toString() {
        return occupancy.entrySet().stream()
                .map(entry -> entry.getKey().getLabel() + entry.getValue())
                .collect(Collectors.joining(" "));
    }
}
