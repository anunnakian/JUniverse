package com.enlil.universe.cosmology;

import com.enlil.universe.world.cosmology.CosmicHistory;
import com.enlil.universe.world.cosmology.Planck2018;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class CosmicCalendarTest {

    private final CosmicCalendar calendar = new CosmicCalendar(new Planck2018());

    @Test
    @DisplayName("the Big Bang should fall on 1 January at midnight, and today at the very end of the year")
    void year_bounds() {
        Assertions.assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0), calendar.dateOf(CosmicHistory.BIG_BANG));
        Assertions.assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), calendar.dateOf(0));
    }

    @Test
    @DisplayName("the Solar System should form in early September")
    void solar_system() {
        LocalDateTime date = calendar.dateOf(CosmicHistory.SOLAR_SYSTEM);

        Assertions.assertEquals(Month.SEPTEMBER, date.getMonth());
        Assertions.assertTrue(date.getDayOfMonth() <= 5, date.toString());
    }

    @Test
    @DisplayName("the dinosaurs should die on 30 December, and Homo sapiens appear after 23:45 on the 31st")
    void last_days() {
        LocalDateTime dinosaurs = calendar.dateOf(CosmicHistory.DINOSAURS_EXTINCTION);
        LocalDateTime humans = calendar.dateOf(CosmicHistory.HOMO_SAPIENS);

        Assertions.assertEquals(LocalDateTime.of(2023, 12, 30, 0, 0).toLocalDate(), dinosaurs.toLocalDate());
        Assertions.assertTrue(humans.isAfter(LocalDateTime.of(2023, 12, 31, 23, 45)), humans.toString());
    }

    @Test
    @DisplayName("one calendar second should stand for 437 real years")
    void years_per_second() {
        Assertions.assertEquals(437, calendar.realYearsPerCalendarSecond(), 1);
    }
}
