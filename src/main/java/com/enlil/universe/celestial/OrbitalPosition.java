package com.enlil.universe.celestial;

import lombok.Getter;

/**
 * A position relative to a central body, in AU, in the frame of its reference plane
 * (the ecliptic for the Solar System): the x axis points to the vernal equinox, z to the north.
 */
@Getter
public class OrbitalPosition {

    private final double xAu;
    private final double yAu;
    private final double zAu;

    public OrbitalPosition(double xAu, double yAu, double zAu) {
        this.xAu = xAu;
        this.yAu = yAu;
        this.zAu = zAu;
    }

    /** Distance to the central body, in AU. */
    public double distanceAu() {
        return Math.sqrt(xAu * xAu + yAu * yAu + zAu * zAu);
    }

    /** Distance to another position in the same frame, in AU. */
    public double distanceAu(OrbitalPosition other) {
        return minus(other).distanceAu();
    }

    /** This position seen from another one in the same frame. */
    public OrbitalPosition minus(OrbitalPosition other) {
        return new OrbitalPosition(xAu - other.xAu, yAu - other.yAu, zAu - other.zAu);
    }

    /** Angle between the directions of this position and another one, seen from the central body, in degrees. */
    public double angleDegrees(OrbitalPosition other) {
        double dot = xAu * other.xAu + yAu * other.yAu + zAu * other.zAu;
        return Math.toDegrees(Math.acos(Math.max(-1, Math.min(1, dot / (distanceAu() * other.distanceAu())))));
    }

    /** Longitude seen from the central body, in degrees within [0, 360). */
    public double longitudeDegrees() {
        return (Math.toDegrees(Math.atan2(yAu, xAu)) + 360) % 360;
    }

    /** Latitude above the reference plane, seen from the central body, in degrees. */
    public double latitudeDegrees() {
        return Math.toDegrees(Math.asin(zAu / distanceAu()));
    }
}
