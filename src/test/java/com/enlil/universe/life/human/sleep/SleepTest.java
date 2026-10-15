package com.enlil.universe.life.human.sleep;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SleepTest {

    @Test
    @DisplayName("7.5 hours should hold five cycles, about 5 % N1, 50 % N2, 22 % N3 and 22 % REM")
    void stage_shares() {
        Sleep night = new Sleep(7.5, 30);

        Assertions.assertEquals(5, night.getCycles().size());
        Assertions.assertEquals(0.056, night.shareOf(SleepStage.N1), 0.01);
        Assertions.assertEquals(0.50, night.shareOf(SleepStage.N2), 0.01);
        Assertions.assertEquals(0.22, night.shareOf(SleepStage.N3), 0.01);
        Assertions.assertEquals(0.22, night.shareOf(SleepStage.REM), 0.01);
    }

    @Test
    @DisplayName("deep sleep should fade through the night while REM periods lengthen")
    void cycles_change() {
        SleepCycle first = new SleepCycle(1);
        SleepCycle fifth = new SleepCycle(5);

        Assertions.assertEquals(40, first.minutesIn(SleepStage.N3));
        Assertions.assertEquals(0, fifth.minutesIn(SleepStage.N3));
        Assertions.assertEquals(10, first.minutesIn(SleepStage.REM));
        Assertions.assertEquals(30, fifth.minutesIn(SleepStage.REM));
        for (SleepCycle cycle : List.of(first, new SleepCycle(3), fifth, new SleepCycle(7))) {
            int total = 0;
            for (SleepStage stage : SleepStage.values()) {
                Assertions.assertTrue(cycle.minutesIn(stage) >= 0);
                total += cycle.minutesIn(stage);
            }
            Assertions.assertEquals(SleepCycle.MINUTES, total);
        }
    }

    @Test
    @DisplayName("leftover minutes after the last full cycle should be light N2 sleep")
    void leftover_minutes() {
        Sleep night = new Sleep(8, 30);

        Assertions.assertEquals(5, night.getCycles().size());
        Assertions.assertEquals(8 * 60, night.getCycles().size() * SleepCycle.MINUTES + 30);
        int total = 0;
        for (SleepStage stage : SleepStage.values()) {
            total += night.minutesIn(stage);
        }
        Assertions.assertEquals(8 * 60, total);
    }

    @Test
    @DisplayName("only the last dream of the night should be remembered, and a short nap brings none")
    void dreams() {
        List<Dream> dreams = new Sleep(7.5, 30).getDreams();

        Assertions.assertEquals(List.of(10, 15, 20, 25, 30), dreams.stream().map(Dream::getDurationMinutes).toList());
        Assertions.assertEquals(1, dreams.stream().filter(Dream::isRemembered).count());
        Assertions.assertTrue(dreams.get(4).isRemembered());
        Assertions.assertTrue(new Sleep(1, 30).rememberedDream().isEmpty());
    }

    @Test
    @DisplayName("adults should sleep 7 to 9 hours, 7 to 8 from 65, and a 5-hour night leaves 2 hours of debt")
    void recommendation() {
        Assertions.assertEquals(9, new Sleep(8, 40).maximumRecommendedHours());
        Assertions.assertEquals(8, new Sleep(8, 70).maximumRecommendedHours());
        Assertions.assertFalse(new Sleep(5, 40).isEnough());
        Assertions.assertEquals(2, new Sleep(5, 40).sleepDebtHours(), 1e-9);
        Assertions.assertEquals(0, new Sleep(8, 40).sleepDebtHours());
    }

    @Test
    @DisplayName("a sleep should last more than nothing and at most a day")
    void rejects_invalid_sleep() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Sleep(0, 30));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Sleep(25, 30));
    }
}
