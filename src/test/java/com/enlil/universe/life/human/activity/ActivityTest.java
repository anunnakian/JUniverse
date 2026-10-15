package com.enlil.universe.life.human.activity;

import com.enlil.universe.life.human.Body;
import com.enlil.universe.life.human.Sex;
import com.enlil.universe.world.activity.Activities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ActivityTest {

    private final Body body = new Body(Sex.MALE, 30, 1.80, 70);

    @Test
    @DisplayName("an hour of running at 10 km/h should burn about 686 kcal for a 70 kg body")
    void running_energy() {
        Assertions.assertEquals(686, Activities.RUNNING.kcalBurned(body, 1), 1);
    }

    @Test
    @DisplayName("an hour of guitar should burn 140 kcal, two hours of chess 210")
    void sitting_energy() {
        Assertions.assertEquals(140, Activities.GUITAR.kcalBurned(body, 1), 1e-9);
        Assertions.assertEquals(210, Activities.CHESS.kcalBurned(body, 2), 1e-9);
    }

    @Test
    @DisplayName("running and judo should be vigorous, dance moderate, drawing light, video games sedentary")
    void intensities() {
        Assertions.assertEquals(Intensity.VIGOROUS, Activities.RUNNING.getIntensity());
        Assertions.assertEquals(Intensity.VIGOROUS, Activities.JUDO.getIntensity());
        Assertions.assertEquals(Intensity.MODERATE, Activities.DANCE.getIntensity());
        Assertions.assertEquals(Intensity.MODERATE, Activities.THEATRE.getIntensity());
        Assertions.assertEquals(Intensity.LIGHT, Activities.DRAWING.getIntensity());
        Assertions.assertEquals(Intensity.SEDENTARY, Activities.VIDEO_GAMES.getIntensity());
    }

    @Test
    @DisplayName("football should be a team sport of 11, tennis a duel")
    void sports() {
        Assertions.assertTrue(Activities.FOOTBALL.isTeamSport());
        Assertions.assertEquals(11, Activities.FOOTBALL.getPlayersPerSide());
        Assertions.assertFalse(Activities.TENNIS.isTeamSport());
        Assertions.assertEquals(ActivityCategory.SPORT, Activities.TENNIS.getCategory());
    }

    @Test
    @DisplayName("the guitar and the piano should both be chordophones, the drums a membranophone")
    void instruments() {
        Assertions.assertEquals("Playing guitar", Activities.GUITAR.getName());
        Assertions.assertEquals(InstrumentFamily.CHORDOPHONE, Activities.GUITAR.getInstrumentFamily());
        Assertions.assertEquals(InstrumentFamily.CHORDOPHONE, Activities.PIANO.getInstrumentFamily());
        Assertions.assertEquals(InstrumentFamily.MEMBRANOPHONE, Activities.DRUMS.getInstrumentFamily());
        Assertions.assertEquals(ActivityCategory.MUSIC, Activities.DRUMS.getCategory());
    }

    @Test
    @DisplayName("theatre should be a performing art, drawing a visual one")
    void arts() {
        Assertions.assertEquals(ActivityCategory.PERFORMING_ART, Activities.THEATRE.getCategory());
        Assertions.assertEquals(ActivityCategory.VISUAL_ART, Activities.DRAWING.getCategory());
    }

    @Test
    @DisplayName("an activity should cost at least rest, a team at least one player, and time should not run back")
    void rejects_invalid_activities() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Pastime("Sleeping below rest", ActivityCategory.HOME, 0.9));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Sport("Empty", 5, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Activities.CHESS.kcalBurned(body, -1));
    }
}
