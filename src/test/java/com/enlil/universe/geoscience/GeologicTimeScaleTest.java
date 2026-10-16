package com.enlil.universe.geoscience;

import com.enlil.universe.world.geoscience.GeologicTimeScale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.enlil.universe.world.geoscience.GeologicTimeScale.*;

public class GeologicTimeScaleTest {

    @Test
    @DisplayName("the units of each rank should tile their parent, without gap or overlap")
    void units_tile_their_parent() {
        List<GeologicTimeUnit> eons = GeologicTimeScale.ofRank(GeologicRank.EON);
        assertTiles(eons, 4_567, 0);
        for (GeologicTimeUnit parent : GeologicTimeScale.all()) {
            List<GeologicTimeUnit> children = GeologicTimeScale.all().stream()
                    .filter(unit -> unit.getParent().equals(Optional.of(parent)))
                    .toList();
            if (!children.isEmpty()) {
                assertTiles(children, parent.getStartMillionYearsAgo(), parent.getEndMillionYearsAgo());
            }
        }
    }

    @Test
    @DisplayName("100 million years ago should be the Cretaceous, in the Mesozoic, in the Phanerozoic")
    void cretaceous() {
        Assertions.assertEquals(Optional.of(CRETACEOUS), GeologicTimeScale.at(100, GeologicRank.PERIOD));
        Assertions.assertEquals(Optional.of(MESOZOIC), GeologicTimeScale.at(100, GeologicRank.ERA));
        Assertions.assertEquals(Optional.of(PHANEROZOIC), GeologicTimeScale.at(100, GeologicRank.EON));
        Assertions.assertEquals(Optional.of(MESOZOIC), CRETACEOUS.getParent());
    }

    @Test
    @DisplayName("the dinosaurs' extinction 66 million years ago should start the Paleogene; today is the Quaternary")
    void paleogene_and_today() {
        Assertions.assertEquals(Optional.of(PALEOGENE), GeologicTimeScale.at(66.0, GeologicRank.PERIOD));
        Assertions.assertEquals(Optional.of(QUATERNARY), GeologicTimeScale.at(0, GeologicRank.PERIOD));
    }

    @Test
    @DisplayName("there should be no period 3 000 million years ago")
    void no_period_in_archean() {
        Assertions.assertEquals(Optional.empty(), GeologicTimeScale.at(3_000, GeologicRank.PERIOD));
        Assertions.assertEquals(Optional.of(ARCHEAN), GeologicTimeScale.at(3_000, GeologicRank.EON));
    }

    @Test
    @DisplayName("should reject a unit outside its parent or of the wrong rank")
    void reject_bad_units() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new GeologicTimeUnit("too old", GeologicRank.PERIOD, 600, 500, PALEOZOIC));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new GeologicTimeUnit("skips a rank", GeologicRank.PERIOD, 500, 490, PHANEROZOIC));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new GeologicTimeUnit("backwards", GeologicRank.EON, 10, 20));
    }

    private static void assertTiles(List<GeologicTimeUnit> units, double start, double end) {
        Assertions.assertEquals(start, units.get(0).getStartMillionYearsAgo(), units.get(0).getName());
        for (int i = 1; i < units.size(); i++) {
            Assertions.assertEquals(units.get(i - 1).getEndMillionYearsAgo(), units.get(i).getStartMillionYearsAgo(),
                    units.get(i).getName());
        }
        Assertions.assertEquals(end, units.get(units.size() - 1).getEndMillionYearsAgo());
    }
}
