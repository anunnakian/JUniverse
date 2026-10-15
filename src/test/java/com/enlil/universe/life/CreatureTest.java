package com.enlil.universe.life;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreatureTest {

    @Test
    @DisplayName("a 70 kg human should weigh about 686 N under Earth gravity")
    void human_weight() {
        Creature human = new Creature("Homo sapiens", Kingdom.ANIMALIA, 70);

        Assertions.assertEquals(686.5, human.weightNewtons(9.807), 0.5);
    }

    @Test
    @DisplayName("a creature should not have a non-positive mass")
    void rejects_non_positive_mass() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Creature("Ghost", Kingdom.ANIMALIA, 0));
    }
}
