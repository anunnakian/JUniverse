package com.enlil.universe.world;

import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.Orbit;
import com.enlil.universe.celestial.Planet;

import java.util.List;

import static com.enlil.universe.celestial.PhysicalConstants.AU;

/** Its moons have their real orbit size and shape; their orientation and phase at J2000 are not modeled. */
public class Uranus extends Planet {

    public Uranus() {
        super("Uranus", 8.681_0e25, 2.536_2e7,
                new Orbit(19.189_164_64, 0.047_257_44, 0.772_637_83, 74.016_925_03, 170.954_276_30, 313.238_104_51), List.of(
                new NaturalSatellite("Titania", 3.400e21, 7.889e5, new Orbit(435_910e3 / AU, 0.001_1, 0, 0)),
                new NaturalSatellite("Oberon", 3.076e21, 7.614e5, new Orbit(583_520e3 / AU, 0.001_4, 0, 0))));
    }
}
