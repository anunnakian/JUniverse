package com.enlil.universe.geoscience;

import com.enlil.universe.world.geoscience.Rocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class RockTypeTest {

    @Test
    @DisplayName("every type of rock should be able to become the two others")
    void rock_cycle() {
        for (RockType type : RockType.values()) {
            EnumSet<RockType> others = EnumSet.complementOf(EnumSet.of(type));

            Assertions.assertEquals(others, type.transformsInto().keySet());
        }
    }

    @Test
    @DisplayName("igneous rock should weather into sedimentary rock and recrystallize into metamorphic rock")
    void igneous_transformations() {
        Assertions.assertEquals(Map.of(RockType.SEDIMENTARY, RockCycleProcess.WEATHERING,
                RockType.METAMORPHIC, RockCycleProcess.METAMORPHISM), RockType.IGNEOUS.transformsInto());
        Assertions.assertEquals(RockCycleProcess.MELTING, RockType.METAMORPHIC.transformsInto().get(RockType.IGNEOUS));
    }

    @Test
    @DisplayName("marble should be metamorphosed limestone, both made of calcite")
    void marble_and_limestone() {
        Assertions.assertEquals(Rocks.LIMESTONE.getMinerals(), Rocks.MARBLE.getMinerals());
        Assertions.assertEquals(RockType.METAMORPHIC, Rocks.MARBLE.getType());
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rock("nothing", RockType.IGNEOUS, List.of()));
    }
}
