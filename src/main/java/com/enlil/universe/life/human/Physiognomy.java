package com.enlil.universe.life.human;

import lombok.Getter;

/**
 * A human's visible features: eyes, hair and skin.
 */
@Getter
public class Physiognomy {

    /** Erythemally weighted irradiance of one point of UV index, in W/m². */
    public static final double UV_INDEX_IRRADIANCE_WATTS_PER_SQUARE_METER = 0.025;

    private final EyeColor eyeColor;
    private final HairColor hairColor;
    private final HairTexture hairTexture;
    private final SkinType skinType;

    public Physiognomy(EyeColor eyeColor, HairColor hairColor, HairTexture hairTexture, SkinType skinType) {
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.hairTexture = hairTexture;
        this.skinType = skinType;
    }

    /** Time for unprotected skin to receive its minimal erythema dose under the given UV index, in minutes. */
    public double minutesToSunburn(double uvIndex) {
        if (uvIndex <= 0) {
            throw new IllegalArgumentException("UV index must be positive");
        }
        double irradiance = uvIndex * UV_INDEX_IRRADIANCE_WATTS_PER_SQUARE_METER;
        return skinType.getMinimalErythemaDoseJoulesPerSquareMeter() / irradiance / 60;
    }
}
