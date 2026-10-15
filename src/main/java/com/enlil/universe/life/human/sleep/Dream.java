package com.enlil.universe.life.human.sleep;

import lombok.Getter;

/**
 * A dream, dreamed during the REM period of a sleep cycle, lasting as long as that period. Dreams happen in non-REM
 * sleep too, but shorter and less vivid, so only REM dreams are modeled. Most dreams are forgotten: only the one
 * running when the sleeper wakes up, the last of the night, is usually remembered.
 */
@Getter
public class Dream {

    private final int cycle;
    private final int durationMinutes;
    private final boolean remembered;

    public Dream(int cycle, int durationMinutes, boolean remembered) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("a dream lasts at least a minute");
        }
        this.cycle = cycle;
        this.durationMinutes = durationMinutes;
        this.remembered = remembered;
    }

    @Override
    public String toString() {
        return "a " + durationMinutes + "-minute dream in cycle " + cycle + (remembered ? ", remembered" : "");
    }
}
