package com.enlil.universe.life.human;

import com.enlil.universe.world.activity.Activities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

public class MindTest {

    private static final Personality AVERAGE_PERSONALITY = new Personality(50, 50, 50, 50, 50);

    private static Mind withIq(int iq) {
        return new Mind(AVERAGE_PERSONALITY, iq, Set.of());
    }

    @ParameterizedTest(name = "an IQ of {0} should beat {1}% of the population")
    @CsvSource({"100, 50.0", "115, 84.1", "130, 97.7", "85, 15.9"})
    @DisplayName("IQ percentiles should follow a normal distribution of mean 100 and standard deviation 15")
    void iq_percentile(int iq, double percentile) {
        Assertions.assertEquals(percentile, withIq(iq).iqPercentile(), 0.1);
    }

    @ParameterizedTest(name = "an IQ of {0} should be {1}")
    @CsvSource({
            "69, EXTREMELY_LOW", "70, BORDERLINE", "79, BORDERLINE", "80, LOW_AVERAGE", "89, LOW_AVERAGE",
            "90, AVERAGE", "109, AVERAGE", "110, HIGH_AVERAGE", "119, HIGH_AVERAGE", "120, SUPERIOR",
            "129, SUPERIOR", "130, VERY_SUPERIOR"})
    @DisplayName("IQ classifications should follow the WAIS-IV bands")
    void iq_classification(int iq, IqClassification classification) {
        Assertions.assertEquals(classification, withIq(iq).iqClassification());
    }

    @Test
    @DisplayName("an IQ should be positive")
    void rejects_non_positive_iq() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> withIq(0));
    }

    @Test
    @DisplayName("a mind may have no hobbies, and its hobbies should not be modifiable")
    void hobbies() {
        Mind idle = withIq(100);
        Mind curious = new Mind(AVERAGE_PERSONALITY, 100, Set.of(Activities.STARGAZING, Activities.CHESS));

        Assertions.assertTrue(idle.getHobbies().isEmpty());
        Assertions.assertEquals(Set.of(Activities.STARGAZING, Activities.CHESS), curious.getHobbies());
        Assertions.assertThrows(UnsupportedOperationException.class, () -> curious.getHobbies().add(Activities.RUNNING));
    }
}
