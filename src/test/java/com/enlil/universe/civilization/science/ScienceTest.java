package com.enlil.universe.civilization.science;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScienceTest {

    @Test
    @DisplayName("physics and biology should be natural sciences, psychology a social one")
    void branches() {
        Assertions.assertEquals(ScienceBranch.NATURAL, Science.PHYSICS.getBranch());
        Assertions.assertEquals(ScienceBranch.NATURAL, Science.BIOLOGY.getBranch());
        Assertions.assertEquals(ScienceBranch.SOCIAL, Science.PSYCHOLOGY.getBranch());
        Assertions.assertEquals(ScienceBranch.FORMAL, Science.MATHEMATICS.getBranch());
        Assertions.assertEquals(ScienceBranch.APPLIED, Science.MEDICINE.getBranch());
    }

    @Test
    @DisplayName("a discovery should print its discoverer and its year, BCE included")
    void discovery_to_string() {
        Assertions.assertEquals("Elements (Euclid, 300 BCE)",
                new Discovery("Elements", Science.MATHEMATICS, "Euclid", -300).toString());
    }

    @Test
    @DisplayName("a discovery should have a name and a real year")
    void rejects_invalid_discoveries() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Discovery(" ", Science.PHYSICS, "Nobody", 2000));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Discovery("Year zero", Science.PHYSICS, "Nobody", 0));
    }
}
