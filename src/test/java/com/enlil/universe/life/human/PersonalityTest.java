package com.enlil.universe.life.human;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersonalityTest {

    private final Personality personality = new Personality(80, 65, 50, 51, 30);

    @Test
    @DisplayName("each Big Five trait should be scored as given")
    void scores() {
        Assertions.assertEquals(80, personality.score(PersonalityTrait.OPENNESS));
        Assertions.assertEquals(65, personality.score(PersonalityTrait.CONSCIENTIOUSNESS));
        Assertions.assertEquals(50, personality.score(PersonalityTrait.EXTRAVERSION));
        Assertions.assertEquals(51, personality.score(PersonalityTrait.AGREEABLENESS));
        Assertions.assertEquals(30, personality.score(PersonalityTrait.NEUROTICISM));
    }

    @Test
    @DisplayName("a trait should be above average only above the 50th percentile")
    void above_average() {
        Assertions.assertTrue(personality.isAboveAverage(PersonalityTrait.AGREEABLENESS));
        Assertions.assertFalse(personality.isAboveAverage(PersonalityTrait.EXTRAVERSION));
        Assertions.assertFalse(personality.isAboveAverage(PersonalityTrait.NEUROTICISM));
    }

    @Test
    @DisplayName("a trait score should be a percentile between 0 and 100")
    void rejects_out_of_range_scores() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Personality(-1, 50, 50, 50, 50));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Personality(50, 50, 50, 50, 101));
    }
}
