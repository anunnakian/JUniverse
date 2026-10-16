package com.enlil.universe.cosmology;

import java.time.LocalDateTime;

/**
 * Carl Sagan's cosmic calendar (The Dragons of Eden, 1977): the whole age of the universe squeezed into one year,
 * with the Big Bang on 1 January at midnight and the present at the very end of 31 December.
 */
public class CosmicCalendar {

    /** An arbitrary common year, so the calendar has 365 days. */
    private static final int YEAR = 2023;
    private static final double SECONDS_IN_YEAR = 365 * 86_400.0;

    private final double ageYears;

    public CosmicCalendar(CosmologicalModel model) {
        this.ageYears = model.ageYears();
    }

    /** The calendar date of an event. */
    public LocalDateTime dateOf(CosmicEvent event) {
        return dateOf(event.getYearsAgo());
    }

    /**
     * The calendar date of a moment, given in years ago; the present, 31 December at 24:00, is shown as 1 January of
     * the next year at midnight.
     */
    public LocalDateTime dateOf(double yearsAgo) {
        if (yearsAgo < 0 || yearsAgo > ageYears) {
            throw new IllegalArgumentException("the calendar runs from the Big Bang to the present");
        }
        double seconds = (1 - yearsAgo / ageYears) * SECONDS_IN_YEAR;
        long wholeSeconds = (long) seconds;
        long nanos = Math.round((seconds - wholeSeconds) * 1e9);
        return LocalDateTime.of(YEAR, 1, 1, 0, 0).plusSeconds(wholeSeconds).plusNanos(nanos);
    }

    /** How many real years one second of the calendar stands for. */
    public double realYearsPerCalendarSecond() {
        return ageYears / SECONDS_IN_YEAR;
    }
}
