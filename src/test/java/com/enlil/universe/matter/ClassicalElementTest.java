package com.enlil.universe.matter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClassicalElementTest {

    @Test
    @DisplayName("each classical element should stand for a state of matter")
    void states_of_matter() {
        Assertions.assertEquals(StateOfMatter.SOLID, ClassicalElement.EARTH.getStateOfMatter());
        Assertions.assertEquals(StateOfMatter.LIQUID, ClassicalElement.WATER.getStateOfMatter());
        Assertions.assertEquals(StateOfMatter.GAS, ClassicalElement.AIR.getStateOfMatter());
        Assertions.assertEquals(StateOfMatter.PLASMA, ClassicalElement.FIRE.getStateOfMatter());
    }

    @Test
    @DisplayName("each classical element should have Aristotle's qualities")
    void aristotelian_qualities() {
        Assertions.assertTrue(ClassicalElement.FIRE.isHot() && ClassicalElement.FIRE.isDry());
        Assertions.assertTrue(ClassicalElement.AIR.isHot() && !ClassicalElement.AIR.isDry());
        Assertions.assertTrue(!ClassicalElement.WATER.isHot() && !ClassicalElement.WATER.isDry());
        Assertions.assertTrue(!ClassicalElement.EARTH.isHot() && ClassicalElement.EARTH.isDry());
    }

    @Test
    @DisplayName("fire should oppose water, and air should oppose earth")
    void opposites() {
        Assertions.assertEquals(ClassicalElement.WATER, ClassicalElement.FIRE.getOpposite());
        Assertions.assertEquals(ClassicalElement.FIRE, ClassicalElement.WATER.getOpposite());
        Assertions.assertEquals(ClassicalElement.EARTH, ClassicalElement.AIR.getOpposite());
        Assertions.assertEquals(ClassicalElement.AIR, ClassicalElement.EARTH.getOpposite());
    }

    @Test
    @DisplayName("should find the classical element of a state of matter")
    void from_state_of_matter() {
        Assertions.assertEquals(ClassicalElement.WATER, ClassicalElement.fromStateOfMatter(StateOfMatter.LIQUID));
        Assertions.assertEquals(ClassicalElement.AIR,
                ClassicalElement.fromStateOfMatter(StateOfMatter.of(ChemicalElement.OXYGEN, 293.15)));
    }
}
