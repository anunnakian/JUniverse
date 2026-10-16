package com.enlil.universe.world;

import com.enlil.universe.civilization.Civilization;
import com.enlil.universe.civilization.TechnologicalEra;
import com.enlil.universe.world.occupation.*;
import com.enlil.universe.world.polity.*;

import java.util.List;

import static com.enlil.universe.world.science.Discoveries.*;

/**
 * Present-day humanity: 8.1 billion people (UN, 2024) consuming 620 EJ of primary energy a year (Energy Institute,
 * 2023), that is about 19.6 TW. Only a few of its 195 sovereign states are modeled, one or two of each form of
 * government.
 */
public class Humanity extends Civilization {

    public Humanity() {
        super("Humanity", 8_100_000_000L, 1.965e13, List.of(
                new France(), new UnitedStates(), new Germany(), new China(), new Switzerland(),
                new UnitedKingdom(), new SaudiArabia(), new Japan(), new Monaco(), new Oman(), new Malaysia(),
                new VaticanCity()),
                List.of(SYLLOGISM, EUCLID_ELEMENTS, DE_ARCHITECTURA, ALMAGEST, HELIOCENTRISM, KEPLER_LAWS,
                        ANALYTIC_GEOMETRY, MECHANICAL_CALCULATOR, UNIVERSAL_GRAVITATION, STEAM_ENGINE_CONDENSER,
                        WEALTH_OF_NATIONS, DEEP_TIME, CONSERVATION_OF_MASS, NATURAL_SELECTION, MENDELIAN_INHERITANCE,
                        PERIODIC_TABLE, RABIES_VACCINE, SOCIAL_FACTS, RADIOACTIVITY, ABO_BLOOD_GROUPS,
                        INTELLIGENCE_TEST, BOHR_ATOM, GENERAL_RELATIVITY, STRUCTURAL_LINGUISTICS, PRIMEVAL_ATOM,
                        PENICILLIN, HUBBLE_LAW, TURING_MACHINE, DNA_DOUBLE_HELIX, BIG_FIVE,
                        COSMIC_MICROWAVE_BACKGROUND),
                List.of(new Doctor(), new Engineer(), new Teacher(), new Firefighter(), new Soldier(),
                        new PoliceOfficer(), new FbiSpecialAgent(), new SecretAgent(), new Knight(), new Samurai(),
                        new Legionary()));
    }

    @Override
    public String getSpecies() {
        return "Homo sapiens";
    }

    @Override
    public TechnologicalEra getEra() {
        return TechnologicalEra.INFORMATION;
    }
}