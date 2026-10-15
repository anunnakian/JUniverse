package com.enlil.universe.world.activity;

import com.enlil.universe.life.human.activity.Activity;
import com.enlil.universe.life.human.activity.ActivityCategory;
import com.enlil.universe.life.human.activity.InstrumentFamily;
import com.enlil.universe.life.human.activity.MusicalPractice;
import com.enlil.universe.life.human.activity.Pastime;
import com.enlil.universe.life.human.activity.Sport;

/**
 * Real activities, with their metabolic equivalents from the Compendium of Physical Activities (Ainsworth et al.,
 * 2011). Where the Compendium has no exact entry (stargazing, theatre, manga), the closest one is used.
 */
public final class Activities {

    // Sports
    public static final Sport FOOTBALL = new Sport("Football", 7.0, 11);
    public static final Sport RUGBY = new Sport("Rugby union", 8.3, 15);
    public static final Sport BASKETBALL = new Sport("Basketball", 6.5, 5);
    public static final Sport VOLLEYBALL = new Sport("Volleyball", 4.0, 6);
    public static final Sport TENNIS = new Sport("Tennis", 7.3, 1);
    /** Running at about 10 km/h (6 mph). */
    public static final Sport RUNNING = new Sport("Running", 9.8, 1);
    /** Cycling for leisure at 16 to 19 km/h. */
    public static final Sport CYCLING = new Sport("Cycling", 6.8, 1);
    public static final Sport SWIMMING = new Sport("Swimming", 6.0, 1);
    public static final Sport BOXING = new Sport("Boxing", 7.8, 1);
    public static final Sport JUDO = new Sport("Judo", 10.3, 1);
    public static final Sport YOGA = new Sport("Yoga", 2.5, 1);
    /** Downhill skiing, alpine, at moderate effort. */
    public static final Sport SKIING = new Sport("Skiing", 5.3, 1);
    /** Surfing, body or board. */
    public static final Sport SURFING = new Sport("Surfing", 3.0, 1);

    // Outdoors
    public static final Activity HIKING = new Pastime("Hiking", ActivityCategory.OUTDOOR, 6.0);
    public static final Activity GARDENING = new Pastime("Gardening", ActivityCategory.OUTDOOR, 3.8);
    /** Standing quietly under the stars. */
    public static final Activity STARGAZING = new Pastime("Stargazing", ActivityCategory.OUTDOOR, 1.3);

    // Arts
    public static final Activity DRAWING = new Pastime("Drawing", ActivityCategory.VISUAL_ART, 1.8);
    public static final Activity PAINTING = new Pastime("Painting", ActivityCategory.VISUAL_ART, 1.8);
    /** Acting on stage, standing and moving, as light-to-moderate effort. */
    public static final Activity THEATRE = new Pastime("Theatre", ActivityCategory.PERFORMING_ART, 3.0);
    /** Ballet, modern or jazz dance. */
    public static final Activity DANCE = new Pastime("Dance", ActivityCategory.PERFORMING_ART, 5.0);

    // Music
    public static final MusicalPractice GUITAR = new MusicalPractice("guitar", InstrumentFamily.CHORDOPHONE, 2.0);
    public static final MusicalPractice PIANO = new MusicalPractice("piano", InstrumentFamily.CHORDOPHONE, 2.3);
    public static final MusicalPractice VIOLIN = new MusicalPractice("violin", InstrumentFamily.CHORDOPHONE, 2.5);
    public static final MusicalPractice FLUTE = new MusicalPractice("flute", InstrumentFamily.AEROPHONE, 2.0);
    public static final MusicalPractice DRUMS = new MusicalPractice("drums", InstrumentFamily.MEMBRANOPHONE, 3.8);

    // Games, the mind and the home
    public static final Activity CHESS = new Pastime("Chess", ActivityCategory.GAME, 1.5);
    public static final Activity VIDEO_GAMES = new Pastime("Video games", ActivityCategory.GAME, 1.0);
    public static final Activity READING = new Pastime("Reading", ActivityCategory.INTELLECTUAL, 1.3);
    /** Reading Japanese comics, as sitting and reading (the Compendium has no entry for comics). */
    public static final Activity MANGA = new Pastime("Manga", ActivityCategory.INTELLECTUAL, 1.3);
    public static final Activity WRITING = new Pastime("Writing", ActivityCategory.INTELLECTUAL, 1.3);
    public static final Activity COOKING = new Pastime("Cooking", ActivityCategory.HOME, 2.0);

    private Activities() {
    }
}
