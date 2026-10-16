package com.enlil.universe;

import com.enlil.universe.celestial.Galaxy;
import com.enlil.universe.celestial.NaturalSatellite;
import com.enlil.universe.celestial.OrbitalPosition;
import com.enlil.universe.celestial.PhysicalConstants;
import com.enlil.universe.celestial.Planet;
import com.enlil.universe.celestial.Star;
import com.enlil.universe.civilization.Civilization;
import com.enlil.universe.civilization.KardashevType;
import com.enlil.universe.civilization.economy.Money;
import com.enlil.universe.civilization.occupation.Warrior;
import com.enlil.universe.civilization.polity.Polity;
import com.enlil.universe.geoscience.Atmosphere;
import com.enlil.universe.geoscience.GeologicRank;
import com.enlil.universe.geoscience.Interior;
import com.enlil.universe.life.Creature;
import com.enlil.universe.life.Kingdom;
import com.enlil.universe.life.human.AboAllele;
import com.enlil.universe.life.human.BloodGenotype;
import com.enlil.universe.life.human.Body;
import com.enlil.universe.life.human.EyeColor;
import com.enlil.universe.life.human.HairColor;
import com.enlil.universe.life.human.HairTexture;
import com.enlil.universe.life.human.Human;
import com.enlil.universe.life.human.Mind;
import com.enlil.universe.life.human.Organ;
import com.enlil.universe.life.human.Personality;
import com.enlil.universe.life.human.Physiognomy;
import com.enlil.universe.life.human.PersonalityTrait;
import com.enlil.universe.life.human.RhAllele;
import com.enlil.universe.life.human.RhFactor;
import com.enlil.universe.life.human.Sex;
import com.enlil.universe.life.human.SkinType;
import com.enlil.universe.life.human.identity.GenderIdentity;
import com.enlil.universe.life.human.identity.Identity;
import com.enlil.universe.life.human.identity.Pronouns;
import com.enlil.universe.life.human.identity.SexualOrientation;
import com.enlil.universe.life.human.language.LanguageSkill;
import com.enlil.universe.life.human.sleep.Sleep;
import com.enlil.universe.life.human.sleep.SleepStage;
import com.enlil.universe.matter.ChemicalElement;
import com.enlil.universe.matter.chemistry.Reaction;
import com.enlil.universe.matter.chemistry.Solution;
import com.enlil.universe.world.*;
import com.enlil.universe.world.activity.Activities;
import com.enlil.universe.world.chemistry.Compounds;
import com.enlil.universe.world.chemistry.Mixtures;
import com.enlil.universe.world.chemistry.Reactions;
import com.enlil.universe.world.economy.Currencies;
import com.enlil.universe.world.geoscience.GeologicTimeScale;
import com.enlil.universe.world.geoscience.TectonicPlates;
import com.enlil.universe.world.language.Languages;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main
{
    public static void main( String[] args )
    {
        // Create the universe bottom-up: Earth's creatures, the planets and their moons,
        // the Sun and its neighbours, then the galaxies of the Local Group
        Human alice = Human.builder()
                .body(new Body(Sex.FEMALE, 50, 1.65, 60))
                .bloodGenotype(new BloodGenotype(AboAllele.O, AboAllele.O, RhAllele.d, RhAllele.d))
                .physiognomy(new Physiognomy(EyeColor.GREEN, HairColor.RED, HairTexture.WAVY, SkinType.I))
                .mind(new Mind(new Personality(80, 65, 40, 70, 30), 118, Set.of(Activities.STARGAZING, Activities.CHESS)))
                .languages(List.of(LanguageSkill.nativeTongue(Languages.FRENCH), LanguageSkill.studied(Languages.ENGLISH, 400)))
                .build();
        Human bob = Human.builder()
                .body(new Body(Sex.MALE, 52, 1.80, 85))
                .bloodGenotype(new BloodGenotype(AboAllele.A, AboAllele.B, RhAllele.D, RhAllele.d))
                .physiognomy(new Physiognomy(EyeColor.BROWN, HairColor.BLACK, HairTexture.CURLY, SkinType.V))
                .mind(new Mind(new Personality(45, 55, 85, 60, 50), 104, Set.of(Activities.FOOTBALL, Activities.COOKING)))
                .languages(List.of(LanguageSkill.nativeTongue(Languages.ENGLISH), LanguageSkill.studied(Languages.SPANISH, 200)))
                .build();
        Human clara = Human.builder()
                .body(new Body(Sex.FEMALE, 22, 1.70, 62))
                .bloodGenotype(new BloodGenotype(AboAllele.A, AboAllele.O, RhAllele.D, RhAllele.d))
                .physiognomy(new Physiognomy(EyeColor.HAZEL, HairColor.BROWN, HairTexture.CURLY, SkinType.III))
                .mind(new Mind(new Personality(70, 50, 60, 65, 40), 112, Set.of(Activities.GUITAR, Activities.HIKING)))
                .mother(alice)
                .father(bob)
                .languages(List.of(LanguageSkill.nativeTongue(Languages.FRENCH), LanguageSkill.nativeTongue(Languages.ENGLISH)))
                .identity(new Identity(GenderIdentity.WOMAN, SexualOrientation.BISEXUAL, Pronouns.SHE_HER))
                .build();
        Earth earth = new Earth(List.of(
                alice, bob, clara,
                new Creature("Balaenoptera musculus", Kingdom.ANIMALIA, 1.5e5),
                new Creature("Loxodonta africana", Kingdom.ANIMALIA, 6_000),
                new Creature("Apis mellifera", Kingdom.ANIMALIA, 1e-4),
                new Creature("Sequoiadendron giganteum", Kingdom.PLANTAE, 1.9e6),
                new Creature("Quercus robur", Kingdom.PLANTAE, 20_000),
                new Creature("Saccharomyces cerevisiae", Kingdom.FUNGI, 6e-14),
                new Creature("Paramecium caudatum", Kingdom.PROTISTA, 3e-10),
                new Creature("Escherichia coli", Kingdom.BACTERIA, 1e-15),
                new Creature("Halobacterium salinarum", Kingdom.ARCHAEA, 1e-15)),
                List.of(new AncientRome(), new Humanity()));
        Sun sun = new Sun(List.of(
                new Mercury(), new Venus(), earth, new Mars(),
                new Jupiter(), new Saturn(), new Uranus(), new Neptune()));
        MilkyWay milkyWay = new MilkyWay(List.of(sun, new AlphaCentauriA(), new ProximaCentauri(), new Sirius()));

        Universe universe = Universe.builder()
                .galaxy(milkyWay)
                .galaxy(new Andromeda())
                .galaxy(new Triangulum())
                .galaxy(new LargeMagellanicCloud())
                .build();

        System.out.printf("The universe holds %d galaxies, %d modeled stars, %d planets and %d civilizations%n%n",
                universe.getGalaxies().size(), universe.getStars().size(), universe.getPlanets().size(),
                universe.getCivilizations().size());
        for (Galaxy galaxy : universe.getGalaxies()) {
            System.out.printf("%s (%s galaxy)%n", galaxy.getName(), galaxy.getType());
            for (Star star : galaxy.getStars()) {
                System.out.printf("  %s: %s-type star, %.0f K, %.1f m/s^2%n",
                        star.getName(), star.getSpectralClass(), star.getSurfaceTemperatureKelvin(), star.surfaceGravity());
                for (Planet planet : star.getPlanets()) {
                    System.out.printf("    %s: %.2f AU, orbits in %.2f days, %.2f m/s^2%n",
                            planet.getName(), planet.getSemiMajorAxisAu(), star.orbitalPeriodDays(planet), planet.surfaceGravity());
                    for (NaturalSatellite moon : planet.getMoons()) {
                        System.out.printf("      %s: %.0f km away, orbits in %.2f days, %.2f m/s^2%n",
                                moon.getName(), moon.getOrbit().getSemiMajorAxisAu() * PhysicalConstants.AU / 1_000,
                                planet.orbitalPeriodDays(moon), moon.surfaceGravity());
                    }
                    for (Creature creature : planet.getCreatures()) {
                        System.out.printf("      %s (%s): weighs %.3g N%n",
                                creature.getSpecies(), creature.getKingdom(), planet.weightNewtons(creature));
                    }
                }
            }
        }

        // The same human, carried across the Solar System
        Creature human = earth.getCreatures().get(0);
        System.out.printf("%nA %.0f kg human would weigh:%n", human.getMassKg());
        for (Planet planet : sun.getPlanets()) {
            System.out.printf("  %-8s %6.0f N%n", planet.getName(), planet.weightNewtons(human));
        }

        // A family, from their measurements to their physiology
        System.out.println();
        for (Human person : List.of(alice, bob, clara)) {
            Body body = person.getBody();
            System.out.printf("A %d-year-old %s, %.2f m and %.0f kg: BMI %.1f (%s), BMR %.0f kcal/day, %.2f m^2 of skin,%n",
                    body.getAgeYears(), body.getSex(), body.getHeightMeters(), body.getMassKg(), body.bmi(),
                    body.bmiCategory(), body.basalMetabolicRateKcalPerDay(), body.bodySurfaceAreaSquareMeters());
            System.out.printf("  %.1f L of %s%s blood, a %.0f g heart and a %.0f g brain%n",
                    person.getBlood().getVolumeLiters(), person.getBlood().getAboGroup(),
                    person.getBlood().getRhFactor() == RhFactor.POSITIVE ? "+" : "-",
                    person.getOrgans().massKg(Organ.HEART) * 1_000, person.getOrgans().massKg(Organ.BRAIN) * 1_000);
            System.out.printf("  a heart beating up to %.0f bpm and %.1f L of vital capacity%n",
                    body.maxHeartRateBpm(), body.vitalCapacityLiters());
            Physiognomy looks = person.getPhysiognomy();
            System.out.printf("  %s eyes, %s %s hair, type %s skin that burns in %.0f min at UV index 8%n",
                    looks.getEyeColor(), looks.getHairTexture(), looks.getHairColor(), looks.getSkinType(),
                    looks.minutesToSunburn(8));
            Mind mind = person.getMind();
            System.out.printf("  an IQ of %d (%s, higher than %.0f%% of people), above average in %s, enjoys %s%n",
                    mind.getIq(), mind.iqClassification(), mind.iqPercentile(),
                    Arrays.stream(PersonalityTrait.values()).filter(mind.getPersonality()::isAboveAverage).toList(),
                    mind.getHobbies());
        }
        // What humans can do: speak, learn, sleep and dream
        System.out.printf("%nAlice speaks %s; a 300-word toast takes her %.0f minutes in French%n",
                alice.getLanguages(), alice.speak(Languages.FRENCH, 300).durationMinutes());
        Human fluentAlice = alice.learn(Languages.ENGLISH, 150).learn(Languages.JAPANESE, 100);
        System.out.printf("After 150 hours of English and 100 of Japanese, she speaks %s%n", fluentAlice.getLanguages());
        Sleep night = clara.sleep(7.5);
        System.out.printf("Clara sleeps 7.5 h (%s): %d cycles, %.0f%% deep sleep, %.0f%% REM, %d dreams;"
                        + " she only remembers the last, %d minutes long%n",
                night.isEnough() ? "enough" : "not enough", night.getCycles().size(), 100 * night.shareOf(SleepStage.N3),
                100 * night.shareOf(SleepStage.REM), night.getDreams().size(),
                night.rememberedDream().orElseThrow().getDurationMinutes());
        Identity claraIdentity = clara.getIdentity().orElseThrow();
        System.out.printf("Clara is a %s woman (%s), part of the LGBTQ+ community: %b; Alice did not say: %s%n",
                claraIdentity.getSexualOrientation().name().toLowerCase(), claraIdentity.getPronouns(), clara.isLgbtq(),
                alice.getIdentity().isPresent() ? "disclosed" : "not disclosed");
        System.out.printf("Clara could marry a woman in %s, but would risk prison in %s%n",
                new Humanity().getPolities().stream()
                        .filter(nation -> nation.getSameSexMarriageYear().isPresent())
                        .map(nation -> nation.getName() + " (" + nation.getSameSexMarriageYear().getAsInt() + ")")
                        .toList(),
                new Humanity().getPolities().stream()
                        .filter(Polity::criminalizesHomosexuality)
                        .map(Polity::getName)
                        .toList());
        System.out.printf("Bob sleeps 5 h before a match: %.0f hours of sleep debt%n", bob.sleep(5).sleepDebtHours());
        System.out.printf("Alice can give blood to Bob: %b; Bob can give blood to Alice: %b%n",
                alice.getBlood().canDonateTo(bob.getBlood()), bob.getBlood().canDonateTo(alice.getBlood()));
        System.out.printf("Their children's blood genotypes: %s%n",
                alice.getBlood().getGenotype().offspring(bob.getBlood().getGenotype()));
        System.out.printf("Clara (%s) shares %.0f%% of her genes with her mother; Alice and Bob share %.0f%%%n",
                clara.getBlood().getGenotype(), 100 * clara.relationshipCoefficient(alice),
                100 * alice.relationshipCoefficient(bob));

        // The civilizations of the Earth, measured by the power they harness
        System.out.println();
        for (Civilization civilization : earth.getCivilizations()) {
            System.out.printf("%s (%s, %s era): %.3g people harnessing %.3g W, %.0f W each, Kardashev %.2f (%s)%n",
                    civilization.getName(), civilization.getSpecies(), civilization.getEra(),
                    (double) civilization.getPopulation(), civilization.getPowerWatts(),
                    civilization.powerPerCapitaWatts(), civilization.kardashevRating(), civilization.kardashevType());
            for (Polity polity : civilization.getPolities()) {
                System.out.printf("  %-14s %-13s %s, %s %s since %s%s, %.3g people, %.0f per km^2%n",
                        polity.getName(), polity.getClass().getSuperclass().getSimpleName(),
                        polity.getTerritorialOrganization(), polity.getSuccession(), polity.getHeadOfStateTitle(),
                        year(polity.getFoundedYear()),
                        polity.getDissolvedYear().isPresent() ? " until " + year(polity.getDissolvedYear().getAsInt()) : "",
                        (double) polity.getPopulation(), polity.populationDensityPerSquareKm());
            }
            System.out.printf("  pays in %s, %s money%n",
                    civilization.getCurrencies(), civilization.getMonetaryStandards());
            System.out.printf("  works as %s%n", civilization.getOccupations());
            System.out.printf("  knows %d discoveries in %s, the latest: %s%n",
                    civilization.getDiscoveries().size(), civilization.getSciences(),
                    civilization.getDiscoveries().get(civilization.getDiscoveries().size() - 1));
        }
        System.out.printf("At 2%% growth a year, humanity reaches type I in %.0f years%n",
                new Humanity().yearsToReach(KardashevType.TYPE_I, 0.02));
        System.out.printf("In 1600, humanity also had %s%n", new Humanity().occupationsIn(1600).stream()
                .filter(occupation -> !occupation.isExtant())
                .map(occupation -> occupation.getName() + " (" + ((Warrior) occupation).getCodeOfHonour() + ")")
                .toList());
        Money savings = Money.of(Currencies.EURO, 100);
        System.out.printf("%s saved today buy %s worth of today's goods in 10 years at 2%% inflation;"
                        + " prices double every %.0f years%n",
                savings, savings.purchasingPowerAfter(0.02, 10), Money.yearsToDoublePrices(0.02));

        // Chemistry: what the Earth's water, fuel, vinegar and air are made of
        Reaction combustion = Reactions.METHANE_COMBUSTION;
        System.out.printf("%nWater (%s) weighs %.3f g/mol; %d chemical elements are known%n",
                Compounds.WATER.getFormula(), Compounds.WATER.molarMassGramsPerMole(), ChemicalElement.values().length);
        System.out.printf("Burning natural gas: %s releases %.0f kJ/mol; 1 kg of methane gives %.0f g of CO2%n",
                combustion, -combustion.enthalpyKjPerMole(),
                combustion.massGrams(Compounds.METHANE, 1_000, Compounds.CARBON_DIOXIDE));
        System.out.printf("Vinegar (0.83 M acetic acid) has a pH of %.2f; air weighs %.2f g/mol%n",
                new Solution(Compounds.ACETIC_ACID, Compounds.WATER, 0.83).ph(),
                Mixtures.DRY_AIR.molarMassGramsPerMole());

        // Earth sciences: the Earth inside and out, and the age we live in
        Interior interior = earth.getInterior().orElseThrow();
        Atmosphere air = earth.getAtmosphere().orElseThrow();
        System.out.printf("%nThe Earth from the center out: %s, adding up to %.3g kg%n",
                interior.getLayers().stream()
                        .map(layer -> layer.getName() + " (" + layer.getState().name().toLowerCase() + ")")
                        .toList(),
                interior.massKg());
        System.out.printf("Its air thins out by e every %.1f km; on Everest it is down to %.0f kPa%n",
                air.scaleHeightMeters(earth.surfaceGravity()) / 1_000,
                air.pressureAtPascals(8_849, earth.surfaceGravity()) / 1_000);
        System.out.printf("Its greenhouse effect warms it by %.0f K, from %.0f K to %.0f K%n",
                air.greenhouseWarmingKelvin(sun.equilibriumTemperatureKelvin(earth, Earth.BOND_ALBEDO)),
                sun.equilibriumTemperatureKelvin(earth, Earth.BOND_ALBEDO), air.getMeanSurfaceTemperatureKelvin());
        System.out.printf("We live in the %s period of the %s era; the Atlantic widens 1 km every %.0f years%n",
                GeologicTimeScale.at(0, GeologicRank.PERIOD).orElseThrow(),
                GeologicTimeScale.at(0, GeologicRank.ERA).orElseThrow(),
                TectonicPlates.MID_ATLANTIC_RIDGE.yearsToWiden(1));

        // Simulate the Solar System: where the planets are now, and when they come closest to the Earth
        Instant now = Instant.now();
        Instant inTwoYears = now.plus(Duration.ofDays(730));
        System.out.printf("%nThe Solar System on %s:%n", LocalDate.ofInstant(now, ZoneOffset.UTC));
        System.out.printf("  %-8s %10s %11s %10s %13s   %s%n",
                "", "from Sun", "longitude", "latitude", "from Earth", "closest to Earth in the next 2 years");
        for (Planet planet : sun.getPlanets()) {
            OrbitalPosition position = sun.positionOf(planet, now);
            System.out.printf("  %-8s %7.3f AU %10.1f° %9.2f°",
                    planet.getName(), position.distanceAu(), position.longitudeDegrees(), position.latitudeDegrees());
            if (planet == earth) {
                System.out.println();
                continue;
            }
            Instant closest = sun.closestApproach(earth, planet, now, inTwoYears);
            System.out.printf(" %10.3f AU   %s at %.3f AU%n", sun.distanceAu(earth, planet, now),
                    LocalDate.ofInstant(closest, ZoneOffset.UTC), sun.distanceAu(earth, planet, closest));
        }
        NaturalSatellite moon = earth.getMoons().get(0);
        System.out.printf("  The Moon is %.0f km from the Earth and %.0f%% lit%n",
                earth.positionOf(moon, now).distanceAu() * PhysicalConstants.AU / 1_000,
                100 * sun.illuminatedFraction(earth, moon, now));
    }

    private static String year(int year) {
        return year < 0 ? -year + " BCE" : String.valueOf(year);
    }
}
