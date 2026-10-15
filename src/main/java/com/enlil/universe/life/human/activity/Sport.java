package com.enlil.universe.life.human.activity;

import lombok.Getter;

/** A sport, played alone or in teams. */
@Getter
public class Sport extends Activity {

    /** Players fielded by each side at once: 11 in football, 1 in a tennis single or a run. */
    private final int playersPerSide;

    public Sport(String name, double metabolicEquivalent, int playersPerSide) {
        super(name, metabolicEquivalent);
        if (playersPerSide < 1) {
            throw new IllegalArgumentException("a side has at least one player");
        }
        this.playersPerSide = playersPerSide;
    }

    public boolean isTeamSport() {
        return playersPerSide > 1;
    }

    @Override
    public ActivityCategory getCategory() {
        return ActivityCategory.SPORT;
    }
}
