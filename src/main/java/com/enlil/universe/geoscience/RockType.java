package com.enlil.universe.geoscience;

import java.util.EnumMap;
import java.util.Map;

/**
 * The three families of rock, and the rock cycle that turns each into the others.
 */
public enum RockType {
    /** Cooled from magma or lava: granite, basalt. */
    IGNEOUS(RockCycleProcess.MELTING),
    /** Laid down in layers from sediments: sandstone, limestone. */
    SEDIMENTARY(RockCycleProcess.WEATHERING),
    /** Transformed by heat and pressure: marble, slate. */
    METAMORPHIC(RockCycleProcess.METAMORPHISM);

    private final RockCycleProcess formedBy;

    RockType(RockCycleProcess formedBy) {
        this.formedBy = formedBy;
    }

    /** The process that makes rocks of this type. */
    public RockCycleProcess getFormedBy() {
        return formedBy;
    }

    /** The types a rock of this type can become, each with the process that turns it into it. */
    public Map<RockType, RockCycleProcess> transformsInto() {
        Map<RockType, RockCycleProcess> transformations = new EnumMap<>(RockType.class);
        for (RockType other : values()) {
            if (other != this) {
                transformations.put(other, other.formedBy);
            }
        }
        return transformations;
    }
}
