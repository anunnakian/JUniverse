package com.enlil.universe.life.human;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PhysiognomyTest {

    private static Physiognomy withSkin(SkinType skinType) {
        return new Physiognomy(EyeColor.BLUE, HairColor.BLONDE, HairTexture.STRAIGHT, skinType);
    }

    @Test
    @DisplayName("Fitzpatrick type II skin should burn in about 21 minutes at a UV index of 8")
    void minutes_to_sunburn() {
        Assertions.assertEquals(20.8, withSkin(SkinType.II).minutesToSunburn(8), 0.1);
    }

    @Test
    @DisplayName("darker skin types should take longer to burn")
    void darker_skin_burns_later() {
        for (int i = 1; i < SkinType.values().length; i++) {
            Assertions.assertTrue(withSkin(SkinType.values()[i]).minutesToSunburn(8)
                    > withSkin(SkinType.values()[i - 1]).minutesToSunburn(8));
        }
    }

    @Test
    @DisplayName("burning should take half as long under twice the UV index")
    void uv_index_scaling() {
        Physiognomy physiognomy = withSkin(SkinType.IV);

        Assertions.assertEquals(physiognomy.minutesToSunburn(4) / 2, physiognomy.minutesToSunburn(8), 1e-9);
    }

    @Test
    @DisplayName("there should be no sunburn time without UV")
    void rejects_non_positive_uv_index() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> withSkin(SkinType.I).minutesToSunburn(0));
    }
}
