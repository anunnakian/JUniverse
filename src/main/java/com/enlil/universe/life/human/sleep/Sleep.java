package com.enlil.universe.life.human.sleep;

import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * A night's sleep: complete cycles of 90 minutes, then light N2 sleep for the minutes left over. How much is enough
 * depends on age: the National Sleep Foundation (2015) recommends 7 to 9 hours to adults up to 64, 7 to 8 after.
 */
@Getter
public class Sleep {

    private static final int OLDER_ADULT_AGE_YEARS = 65;

    private final double hours;
    private final int ageYears;
    private final List<SleepCycle> cycles;

    public Sleep(double hours, int ageYears) {
        if (hours <= 0 || hours > 24) {
            throw new IllegalArgumentException("a sleep lasts more than nothing and at most a day");
        }
        this.hours = hours;
        this.ageYears = ageYears;
        this.cycles = IntStream.rangeClosed(1, (int) (hours * 60 / SleepCycle.MINUTES))
                .mapToObj(SleepCycle::new)
                .toList();
    }

    /** Minutes spent in the given stage over the whole sleep. */
    public int minutesIn(SleepStage stage) {
        int minutes = cycles.stream().mapToInt(cycle -> cycle.minutesIn(stage)).sum();
        if (stage == SleepStage.N2) {
            minutes += leftoverMinutes();
        }
        return minutes;
    }

    /** Share of the sleep spent in the given stage, between 0 and 1. */
    public double shareOf(SleepStage stage) {
        return minutesIn(stage) / (hours * 60);
    }

    /** One dream per REM period; the last one, closest to waking, is the one remembered. */
    public List<Dream> getDreams() {
        return cycles.stream()
                .map(cycle -> new Dream(cycle.getNumber(), cycle.minutesIn(SleepStage.REM),
                        cycle.getNumber() == cycles.size()))
                .toList();
    }

    /** The dream remembered on waking; empty after a nap too short to reach REM sleep. */
    public Optional<Dream> rememberedDream() {
        return getDreams().stream().filter(Dream::isRemembered).findFirst();
    }

    public double minimumRecommendedHours() {
        return 7;
    }

    public double maximumRecommendedHours() {
        return ageYears >= OLDER_ADULT_AGE_YEARS ? 8 : 9;
    }

    public boolean isEnough() {
        return hours >= minimumRecommendedHours();
    }

    /** Hours missing to the recommended minimum; zero after a good night. */
    public double sleepDebtHours() {
        return Math.max(0, minimumRecommendedHours() - hours);
    }

    private int leftoverMinutes() {
        return (int) Math.round(hours * 60) - cycles.size() * SleepCycle.MINUTES;
    }
}
