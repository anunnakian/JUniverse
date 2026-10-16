package com.enlil.universe.world.cosmology;

import com.enlil.universe.cosmology.CosmicEvent;
import com.enlil.universe.cosmology.CosmologicalModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CosmicHistoryTest {

    @Test
    @DisplayName("events should be in chronological order, none before the Big Bang")
    void chronological_order() {
        List<CosmicEvent> events = CosmicHistory.all();
        CosmologicalModel planck = new Planck2018();

        Assertions.assertEquals(CosmicHistory.BIG_BANG, events.get(0));
        Assertions.assertEquals(CosmicHistory.WRITING, events.get(events.size() - 1));
        Assertions.assertEquals(0, CosmicHistory.BIG_BANG.yearsAfterBigBang(planck));
        for (int i = 1; i < events.size(); i++) {
            Assertions.assertTrue(events.get(i).yearsAfterBigBang(planck) > events.get(i - 1).yearsAfterBigBang(planck));
        }
    }

    @Test
    @DisplayName("an event older than the universe should be rejected")
    void reject_before_big_bang() {
        CosmicEvent tooOld = new CosmicEvent("Before time", 20e9);

        Assertions.assertThrows(IllegalArgumentException.class, () -> tooOld.yearsAfterBigBang(new Planck2018()));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CosmicEvent("Tomorrow", -1));
    }
}
