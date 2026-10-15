package com.enlil.universe.life.human.sleep;

import lombok.Getter;

/**
 * One cycle of about 90 minutes through the stages of sleep. Deep sleep fills the first cycles and fades by morning,
 * while REM periods lengthen: about 10 minutes in the first cycle, 5 more in each of the next. Over a night of five
 * cycles this gives the textbook shares: about 5 % N1, 50 % N2, 20 % N3 and 20 % REM.
 */
@Getter
public class SleepCycle {

    public static final int MINUTES = 90;
    private static final int N1_MINUTES = 5;

    /** The cycle's rank in the night, from 1. */
    private final int number;

    public SleepCycle(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("cycles are numbered from 1");
        }
        this.number = number;
    }

    /** Minutes spent in the given stage during this cycle. */
    public int minutesIn(SleepStage stage) {
        return switch (stage) {
            case N1 -> N1_MINUTES;
            case N3 -> Math.max(0, 40 - 10 * (number - 1));
            case REM -> 5 + 5 * number;
            case N2 -> MINUTES - N1_MINUTES - minutesIn(SleepStage.N3) - minutesIn(SleepStage.REM);
        };
    }
}
