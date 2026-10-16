package com.enlil.universe.celestial;

import lombok.Getter;

/**
 * The range of distances from a star where a planet could keep liquid water on its surface.
 */
@Getter
public class HabitableZone {

    private final double innerEdgeAu;
    private final double outerEdgeAu;

    public HabitableZone(double innerEdgeAu, double outerEdgeAu) {
        if (innerEdgeAu <= 0 || innerEdgeAu >= outerEdgeAu) {
            throw new IllegalArgumentException("the inner edge must be positive and closer than the outer edge");
        }
        this.innerEdgeAu = innerEdgeAu;
        this.outerEdgeAu = outerEdgeAu;
    }

    /** Whether a distance from the star, in AU, lies in this zone, edges included. */
    public boolean contains(double distanceAu) {
        return distanceAu >= innerEdgeAu && distanceAu <= outerEdgeAu;
    }

    /** Whether a planet orbits in this zone, judged on its semi-major axis. */
    public boolean contains(Planet planet) {
        return contains(planet.getSemiMajorAxisAu());
    }
}
