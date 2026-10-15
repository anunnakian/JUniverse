package com.enlil.universe.world;

import com.enlil.universe.civilization.Civilization;
import com.enlil.universe.civilization.TechnologicalEra;
import com.enlil.universe.world.occupation.Doctor;
import com.enlil.universe.world.occupation.Engineer;
import com.enlil.universe.world.occupation.Firefighter;
import com.enlil.universe.world.occupation.Legionary;
import com.enlil.universe.world.occupation.Teacher;
import com.enlil.universe.world.polity.RomanEmpire;
import com.enlil.universe.world.science.Discoveries;

import java.util.List;

/**
 * Ancient Rome at the 2nd-century peak of its empire: about 60 million people, each capturing about 31,000 kcal of
 * energy a day (Ian Morris's estimate, food, fuel and fodder included), that is about 1.5 kW per person and 90 GW in
 * all. It knew the Greek logic and geometry it inherited, its own engineering, and Ptolemy's astronomy.
 */
public class AncientRome extends Civilization {

    public AncientRome() {
        super("Ancient Rome", 60_000_000L, 9.0e10, List.of(new RomanEmpire()),
                List.of(Discoveries.SYLLOGISM, Discoveries.EUCLID_ELEMENTS, Discoveries.DE_ARCHITECTURA,
                        Discoveries.ALMAGEST),
                List.of(new Legionary(), new Doctor(), new Engineer(), new Teacher(), new Firefighter()));
    }

    @Override
    public String getSpecies() {
        return "Homo sapiens";
    }

    @Override
    public TechnologicalEra getEra() {
        return TechnologicalEra.IRON_AGE;
    }
}
