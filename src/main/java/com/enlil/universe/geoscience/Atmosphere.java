package com.enlil.universe.geoscience;

import com.enlil.universe.matter.chemistry.ChemicalConstants;
import com.enlil.universe.matter.chemistry.Mixture;
import lombok.Getter;

import java.util.Objects;

/**
 * The gas envelope of a planet. It does not point back to its planet, so the methods that need the surface gravity
 * take it as an argument ({@code planet.surfaceGravity()}).
 */
@Getter
public class Atmosphere {

    private final Mixture composition;
    private final double surfacePressurePascals;
    private final double meanSurfaceTemperatureKelvin;

    public Atmosphere(Mixture composition, double surfacePressurePascals, double meanSurfaceTemperatureKelvin) {
        if (surfacePressurePascals <= 0 || meanSurfaceTemperatureKelvin <= 0) {
            throw new IllegalArgumentException("pressure and temperature must be positive");
        }
        this.composition = Objects.requireNonNull(composition, "composition");
        this.surfacePressurePascals = surfacePressurePascals;
        this.meanSurfaceTemperatureKelvin = meanSurfaceTemperatureKelvin;
    }

    /**
     * Height over which the pressure drops by a factor e, H = R·T / (M·g), in meters.
     *
     * @param surfaceGravity the planet's surface gravity, in m/s²
     */
    public double scaleHeightMeters(double surfaceGravity) {
        double molarMassKgPerMole = composition.molarMassGramsPerMole() / 1_000;
        return ChemicalConstants.MOLAR_GAS_CONSTANT * meanSurfaceTemperatureKelvin
                / (molarMassKgPerMole * surfaceGravity);
    }

    /**
     * Pressure at a given altitude, in pascals, by the isothermal barometric formula p₀·e^(−h/H).
     *
     * @param altitudeMeters height above the surface, in meters
     * @param surfaceGravity the planet's surface gravity, in m/s²
     */
    public double pressureAtPascals(double altitudeMeters, double surfaceGravity) {
        return surfacePressurePascals * Math.exp(-altitudeMeters / scaleHeightMeters(surfaceGravity));
    }

    /**
     * How much warmer the surface is than it would be without this atmosphere, in kelvins.
     *
     * @param equilibriumTemperatureKelvin the planet's radiative equilibrium temperature, from its star
     */
    public double greenhouseWarmingKelvin(double equilibriumTemperatureKelvin) {
        return meanSurfaceTemperatureKelvin - equilibriumTemperatureKelvin;
    }
}
