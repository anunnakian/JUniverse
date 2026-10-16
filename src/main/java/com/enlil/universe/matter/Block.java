package com.enlil.universe.matter;

/**
 * Block of the periodic table: the type of the subshell an element's last electron fills.
 */
public enum Block {
    S, P, D, F;

    /** The block of a subshell type letter: 's', 'p', 'd' or 'f'. */
    static Block of(char subshellType) {
        return valueOf(String.valueOf(Character.toUpperCase(subshellType)));
    }
}
