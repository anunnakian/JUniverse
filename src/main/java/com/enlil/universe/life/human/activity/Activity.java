package com.enlil.universe.life.human.activity;

import com.enlil.universe.life.human.Body;
import lombok.Getter;

/**
 * Something a human does for its own sake: a sport, an art, music, a game... Each activity costs a metabolic
 * equivalent (MET), its energy expenditure as a multiple of rest: by definition 1 MET is about 1 kcal per kilogram of
 * body mass per hour, the cost of sitting quietly.
 */
@Getter
public abstract class Activity {

    private final String name;
    private final double metabolicEquivalent;

    protected Activity(String name, double metabolicEquivalent) {
        if (metabolicEquivalent < 1) {
            throw new IllegalArgumentException("no activity costs less than rest, 1 MET");
        }
        this.name = name;
        this.metabolicEquivalent = metabolicEquivalent;
    }

    public abstract ActivityCategory getCategory();

    public Intensity getIntensity() {
        return Intensity.of(metabolicEquivalent);
    }

    /** Energy the given body spends doing this activity for the given hours, in kcal: MET × mass × hours. */
    public double kcalBurned(Body body, double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("hours must not be negative");
        }
        return metabolicEquivalent * body.getMassKg() * hours;
    }

    @Override
    public String toString() {
        return name;
    }
}
