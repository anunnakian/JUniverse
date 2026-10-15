package com.enlil.universe.matter;

/**
 * Electron subshells, in the order they fill (Madelung rule).
 */
public enum Subshell {
    S1(1, 's'), S2(2, 's'), P2(2, 'p'), S3(3, 's'), P3(3, 'p'), S4(4, 's'), D3(3, 'd'), P4(4, 'p'),
    S5(5, 's'), D4(4, 'd'), P5(5, 'p'), S6(6, 's'), F4(4, 'f'), D5(5, 'd'), P6(6, 'p'), S7(7, 's'),
    F5(5, 'f'), D6(6, 'd'), P7(7, 'p');

    private final int shell;
    private final char type;

    Subshell(int shell, char type) {
        this.shell = shell;
        this.type = type;
    }

    /** Principal quantum number n. */
    public int getShell() {
        return shell;
    }

    /** Maximum number of electrons: 2 for s, 6 for p, 10 for d, 14 for f. */
    public int getCapacity() {
        return switch (type) {
            case 's' -> 2;
            case 'p' -> 6;
            case 'd' -> 10;
            case 'f' -> 14;
            default -> throw new IllegalStateException("unknown subshell type " + type);
        };
    }

    /** Spectroscopic label, e.g. "2p". */
    public String getLabel() {
        return "" + shell + type;
    }
}
