package com.enlil.universe.cosmology;

import lombok.Getter;

/**
 * A moment of the universe's history, dated in years before the present.
 */
@Getter
public class CosmicEvent {

    private final String name;
    private final double yearsAgo;

    public CosmicEvent(String name, double yearsAgo) {
        if (yearsAgo < 0) {
            throw new IllegalArgumentException("an event of history lies in the past");
        }
        this.name = name;
        this.yearsAgo = yearsAgo;
    }

    /**
     * Time between the Big Bang and this event, in years, given the age of the universe in a model.
     *
     * @throws IllegalArgumentException if the event would come before the Big Bang
     */
    public double yearsAfterBigBang(CosmologicalModel model) {
        double years = model.ageYears() - yearsAgo;
        if (years < 0) {
            throw new IllegalArgumentException(name + " would come before the Big Bang");
        }
        return years;
    }

    @Override
    public String toString() {
        return name;
    }
}
