package com.enlil.universe.world.geoscience;

import com.enlil.universe.geoscience.MassExtinction;

import java.util.List;

/**
 * The "big five" mass extinctions of the Phanerozoic, with the share of marine species lost.
 */
public final class MassExtinctions {

    public static final MassExtinction END_ORDOVICIAN = new MassExtinction("End-Ordovician", 443.8, 0.85);
    public static final MassExtinction LATE_DEVONIAN = new MassExtinction("Late Devonian", 372, 0.75);
    /** The Great Dying, the worst of all, which ended the Paleozoic. */
    public static final MassExtinction END_PERMIAN = new MassExtinction("End-Permian", 251.902, 0.81);
    public static final MassExtinction END_TRIASSIC = new MassExtinction("End-Triassic", 201.4, 0.76);
    /** The asteroid that killed the non-avian dinosaurs. */
    public static final MassExtinction CRETACEOUS_PALEOGENE = new MassExtinction("Cretaceous–Paleogene", 66.0, 0.76);

    /** In chronological order. */
    public static final List<MassExtinction> BIG_FIVE =
            List.of(END_ORDOVICIAN, LATE_DEVONIAN, END_PERMIAN, END_TRIASSIC, CRETACEOUS_PALEOGENE);

    private MassExtinctions() {
    }
}
