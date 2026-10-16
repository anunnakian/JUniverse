package com.enlil.universe.geoscience;

import com.enlil.universe.matter.StateOfMatter;
import com.enlil.universe.world.Earth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InteriorTest {

    private final Earth earth = new Earth();
    private final Interior interior = earth.getInterior().orElseThrow();

    @Test
    @DisplayName("the Earth's layers should add up to its mass, within 2 %")
    void layers_add_up_to_earth_mass() {
        Assertions.assertEquals(earth.getMassKg(), interior.massKg(), 0.02 * earth.getMassKg());
        Assertions.assertEquals(earth.getRadiusMeters(), interior.radiusMeters());
    }

    @Test
    @DisplayName("the outer core should be liquid, the inner core solid")
    void liquid_outer_core() {
        Assertions.assertEquals(StateOfMatter.LIQUID, interior.getLayers().get(1).getState());
        Assertions.assertEquals(StateOfMatter.SOLID, interior.getLayers().get(0).getState());
        Assertions.assertEquals(1_221e3, interior.innerRadiusMeters(interior.getLayers().get(1)));
    }

    @Test
    @DisplayName("2 000 km down should be the lower mantle, 3 000 km the outer core (below 2 891 km)")
    void layer_at_depth() {
        Assertions.assertEquals("Lower mantle", interior.layerAt(2_000e3).getName());
        Assertions.assertEquals("Outer core", interior.layerAt(3_000e3).getName());
        Assertions.assertEquals("Crust", interior.layerAt(0).getName());
        Assertions.assertEquals("Inner core", interior.layerAt(6_371e3).getName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> interior.layerAt(7_000e3));
    }

    @Test
    @DisplayName("should reject layers whose radii don't increase, and a gaseous layer")
    void reject_bad_layers() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Interior(List.of(
                new InternalLayer("core", 3e6, 10_000, StateOfMatter.SOLID),
                new InternalLayer("mantle", 3e6, 4_000, StateOfMatter.SOLID))));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new InternalLayer("air", 1e6, 1, StateOfMatter.GAS));
    }
}
