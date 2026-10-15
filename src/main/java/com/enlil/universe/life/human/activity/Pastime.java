package com.enlil.universe.life.human.activity;

/** Any other activity, from drawing to theatre to chess, filed in its category. */
public class Pastime extends Activity {

    private final ActivityCategory category;

    public Pastime(String name, ActivityCategory category, double metabolicEquivalent) {
        super(name, metabolicEquivalent);
        this.category = category;
    }

    @Override
    public ActivityCategory getCategory() {
        return category;
    }
}
