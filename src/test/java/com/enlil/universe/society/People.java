package com.enlil.universe.society;

import com.enlil.universe.life.human.AboAllele;
import com.enlil.universe.life.human.BloodGenotype;
import com.enlil.universe.life.human.Body;
import com.enlil.universe.life.human.EyeColor;
import com.enlil.universe.life.human.HairColor;
import com.enlil.universe.life.human.HairTexture;
import com.enlil.universe.life.human.Human;
import com.enlil.universe.life.human.Mind;
import com.enlil.universe.life.human.Personality;
import com.enlil.universe.life.human.Physiognomy;
import com.enlil.universe.life.human.RhAllele;
import com.enlil.universe.life.human.Sex;
import com.enlil.universe.life.human.SkinType;

import java.time.Month;
import java.time.MonthDay;
import java.util.Set;

/** Humans for the society tests, alike but for their birthday. */
final class People {

    private People() {
    }

    static Human bornOn(Month month, int day) {
        return human(MonthDay.of(month, day));
    }

    /** A human whose birthday is unknown. */
    static Human anyone() {
        return human(null);
    }

    private static Human human(MonthDay birthday) {
        return Human.builder()
                .body(new Body(Sex.FEMALE, 30, 1.65, 60))
                .bloodGenotype(new BloodGenotype(AboAllele.O, AboAllele.O, RhAllele.D, RhAllele.d))
                .physiognomy(new Physiognomy(EyeColor.BROWN, HairColor.BLACK, HairTexture.STRAIGHT, SkinType.III))
                .mind(new Mind(new Personality(50, 50, 50, 50, 50), 100, Set.of()))
                .birthday(birthday)
                .build();
    }
}
