package com.enlil.universe.geoscience;

import com.enlil.universe.world.geoscience.GeologicTimeScale;
import com.enlil.universe.world.geoscience.MassExtinctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MassExtinctionTest {

    @Test
    @DisplayName("the big five should be in chronological order")
    void chronological_order() {
        List<MassExtinction> bigFive = MassExtinctions.BIG_FIVE;

        Assertions.assertEquals(5, bigFive.size());
        for (int i = 1; i < bigFive.size(); i++) {
            Assertions.assertTrue(bigFive.get(i - 1).getMillionYearsAgo() > bigFive.get(i).getMillionYearsAgo());
        }
    }

    @Test
    @DisplayName("the end-Permian and K–Pg extinctions should fall on period boundaries")
    void extinctions_end_periods() {
        Assertions.assertEquals(GeologicTimeScale.PERMIAN.getEndMillionYearsAgo(),
                MassExtinctions.END_PERMIAN.getMillionYearsAgo(), 0.01);
        Assertions.assertEquals(GeologicTimeScale.CRETACEOUS.getEndMillionYearsAgo(),
                MassExtinctions.CRETACEOUS_PALEOGENE.getMillionYearsAgo(), 0.01);
    }

    @Test
    @DisplayName("should reject an extinction that wiped out every species")
    void reject_total_extinction() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new MassExtinction("everything", 1, 1));
    }
}
