package com.enlil.universe;

import com.enlil.universe.world.cosmology.Planck2018;

/**
 * The primeval atom the universe was born from (Lemaître), not a chemical atom.
 */
public class PrimitiveAtom {

    /** An empty universe, expanding as Planck measured it, so it knows its age. */
    public Universe bigBand() {
        return Universe.builder().cosmology(new Planck2018()).build();
    }
}
