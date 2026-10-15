package com.enlil.universe.life.human;

import lombok.Getter;

import java.util.EnumMap;
import java.util.Map;

/**
 * The masses of a human's major organs.
 */
@Getter
public class Organs {

    /** Mass of each organ, in kg. */
    private final Map<Organ, Double> massesKg;

    public Organs(Map<Organ, Double> massesKg) {
        if (massesKg.values().stream().anyMatch(mass -> mass <= 0)) {
            throw new IllegalArgumentException("organ masses must be positive");
        }
        this.massesKg = Map.copyOf(massesKg);
    }

    /**
     * ICRP 89 reference adult organs for the given sex. These are population references: they do not scale with an
     * individual's height or mass.
     */
    public static Organs reference(Sex sex) {
        Map<Organ, Double> massesKg = new EnumMap<>(Organ.class);
        for (Organ organ : Organ.values()) {
            massesKg.put(organ, organ.referenceMassKg(sex));
        }
        return new Organs(massesKg);
    }

    /** Mass of the given organ, in kg. */
    public double massKg(Organ organ) {
        return massesKg.get(organ);
    }

    /** Combined mass of all the organs, in kg. */
    public double totalMassKg() {
        return massesKg.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
