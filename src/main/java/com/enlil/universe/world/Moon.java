package com.enlil.universe.world;

import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.Orbit;

import static com.enlil.universe.celestial.PhysicalConstants.AU;

/**
 * The Earth's Moon, on its mean geocentric ecliptic orbit at J2000. Its perigee and nodes precess
 * quickly in reality (8.85 and 18.6 years) and are kept fixed here, so its position drifts by a few degrees.
 */
public class Moon extends NaturalSatellite {

    public Moon() {
        super("Moon", 7.346e22, 1.737_4e6,
                new Orbit(384_748e3 / AU, 0.054_9, 5.145, 125.044_548, 83.353_247, 218.316_448));
    }
}
