package com.enlil.universe.life.human;

import com.enlil.universe.life.Kingdom;
import com.enlil.universe.world.Earth;
import com.enlil.universe.world.Mars;
import com.enlil.universe.life.human.identity.GenderIdentity;
import com.enlil.universe.life.human.identity.Identity;
import com.enlil.universe.life.human.identity.Pronouns;
import com.enlil.universe.life.human.identity.SexualOrientation;
import com.enlil.universe.life.human.language.LanguageSkill;
import com.enlil.universe.life.human.language.Proficiency;
import com.enlil.universe.life.human.sleep.Sleep;
import com.enlil.universe.world.activity.Activities;
import com.enlil.universe.world.language.Languages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.enlil.universe.life.human.AboAllele.A;
import static com.enlil.universe.life.human.AboAllele.B;
import static com.enlil.universe.life.human.AboAllele.O;
import static com.enlil.universe.life.human.RhAllele.D;
import static com.enlil.universe.life.human.RhAllele.d;

public class HumanTest {

    private static final BloodGenotype O_NEGATIVE = new BloodGenotype(O, O, d, d);

    private final Mind mind = new Mind(new Personality(80, 65, 40, 70, 30), 118, Set.of(Activities.STARGAZING, Activities.CHESS));
    private final Physiognomy physiognomy = new Physiognomy(EyeColor.GREEN, HairColor.RED, HairTexture.WAVY, SkinType.I);
    private final Human human = Human.builder()
            .body(new Body(Sex.FEMALE, 30, 1.65, 60))
            .bloodGenotype(O_NEGATIVE)
            .physiognomy(physiognomy)
            .mind(mind)
            .build();

    @Test
    @DisplayName("a human should be a Homo sapiens of the animal kingdom")
    void classification() {
        Assertions.assertEquals("Homo sapiens", human.getSpecies());
        Assertions.assertEquals(Kingdom.ANIMALIA, human.getKingdom());
    }

    @Test
    @DisplayName("a human's mass should be their body's mass")
    void mass() {
        Assertions.assertEquals(60, human.getMassKg());
    }

    @Test
    @DisplayName("a human's blood and organs should match their body and genotype")
    void blood_and_organs() {
        Assertions.assertEquals(O_NEGATIVE, human.getBlood().getGenotype());
        Assertions.assertEquals(AboGroup.O, human.getBlood().getAboGroup());
        Assertions.assertEquals(RhFactor.NEGATIVE, human.getBlood().getRhFactor());
        Assertions.assertEquals(new Blood(human.getBody(), O_NEGATIVE).getVolumeLiters(),
                human.getBlood().getVolumeLiters());
        Assertions.assertEquals(Organs.reference(Sex.FEMALE).getMassesKg(), human.getOrgans().getMassesKg());
    }

    @Test
    @DisplayName("a human should keep the physiognomy and mind they were given")
    void physiognomy_and_mind() {
        Assertions.assertSame(physiognomy, human.getPhysiognomy());
        Assertions.assertSame(mind, human.getMind());
    }

    @Test
    @DisplayName("a 60 kg human should weigh about 589 N on the Earth and 223 N on Mars")
    void weight_across_planets() {
        Earth earth = new Earth(List.of(human));

        Assertions.assertEquals(589, earth.weightNewtons(human), 1);
        Assertions.assertEquals(223, new Mars().weightNewtons(human), 1);
    }

    @Nested
    class Family {

        // Two unrelated couples of grandparents, their children, and the grandchildren
        private final Human grandfather = person(Sex.MALE, 90, new BloodGenotype(A, O, D, d), null, null);
        private final Human grandmother = person(Sex.FEMALE, 88, new BloodGenotype(B, O, D, d), null, null);
        private final Human father = person(Sex.MALE, 60, new BloodGenotype(A, B, D, d), grandmother, grandfather);
        private final Human aunt = person(Sex.FEMALE, 58, new BloodGenotype(O, O, d, d), grandmother, grandfather);
        private final Human mother = person(Sex.FEMALE, 59, new BloodGenotype(O, O, D, D), null, null);
        private final Human uncleInLaw = person(Sex.MALE, 61, new BloodGenotype(O, O, d, d), null, null);
        private final Human child = person(Sex.FEMALE, 30, new BloodGenotype(A, O, D, D), mother, father);
        private final Human sibling = person(Sex.MALE, 28, new BloodGenotype(B, O, D, d), mother, father);
        private final Human otherWoman = person(Sex.FEMALE, 55, new BloodGenotype(B, O, d, d), null, null);
        private final Human halfSibling = person(Sex.MALE, 25, new BloodGenotype(B, O, D, d), otherWoman, father);
        private final Human cousin = person(Sex.FEMALE, 27, new BloodGenotype(O, O, d, d), aunt, uncleInLaw);
        private final Human stranger = person(Sex.MALE, 30, new BloodGenotype(O, O, D, D), null, null);

        @Test
        @DisplayName("a human should know their parents, if known")
        void parents() {
            Assertions.assertSame(mother, child.getMother().orElseThrow());
            Assertions.assertSame(father, child.getFather().orElseThrow());
            Assertions.assertTrue(stranger.getMother().isEmpty());
            Assertions.assertTrue(stranger.getFather().isEmpty());
        }

        @Test
        @DisplayName("a human's ancestors should be their parents and their parents' ancestors")
        void ancestors() {
            Assertions.assertEquals(Set.of(mother, father, grandmother, grandfather), child.getAncestors());
            Assertions.assertTrue(stranger.getAncestors().isEmpty());
        }

        @Test
        @DisplayName("siblings should share at least one parent")
        void siblings() {
            Assertions.assertTrue(child.isSiblingOf(sibling));
            Assertions.assertTrue(father.isSiblingOf(aunt));
            Assertions.assertFalse(child.isSiblingOf(child));
            Assertions.assertFalse(child.isSiblingOf(cousin));
            Assertions.assertFalse(child.isSiblingOf(stranger));
        }

        @Test
        @DisplayName("the coefficient of relationship should follow Wright's textbook values")
        void relationship_coefficient() {
            Assertions.assertEquals(1, child.relationshipCoefficient(child));
            Assertions.assertEquals(0.5, child.relationshipCoefficient(father));
            Assertions.assertEquals(0.5, father.relationshipCoefficient(child));
            Assertions.assertEquals(0.5, child.relationshipCoefficient(sibling));
            Assertions.assertEquals(0.25, child.relationshipCoefficient(grandfather));
            Assertions.assertEquals(0.25, child.relationshipCoefficient(aunt));
            Assertions.assertEquals(0.25, sibling.relationshipCoefficient(halfSibling));
            Assertions.assertEquals(0.125, child.relationshipCoefficient(cousin));
            Assertions.assertEquals(0, child.relationshipCoefficient(stranger));
            Assertions.assertEquals(0, mother.relationshipCoefficient(father));
        }

        @Test
        @DisplayName("a mother should be female and a father male")
        void rejects_wrong_sex_parents() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> person(Sex.MALE, 20, new BloodGenotype(A, O, D, d), father, father));
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> person(Sex.MALE, 20, new BloodGenotype(A, O, D, d), mother, mother));
        }

        @Test
        @DisplayName("parents should be older than their child")
        void rejects_younger_parents() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> person(Sex.MALE, 59, new BloodGenotype(A, O, D, d), mother, father));
        }

        @Test
        @DisplayName("a child should only have a blood genotype their parents can pass on")
        void rejects_impossible_blood() {
            // An OO mother and an AB father can't have an O child
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> person(Sex.MALE, 20, new BloodGenotype(O, O, D, d), mother, father));
            // Two AO parents can't have an AB child, although each of them alone could be its parent
            Human aoMother = person(Sex.FEMALE, 50, new BloodGenotype(A, O, d, d), null, null);
            Human aoFather = person(Sex.MALE, 50, new BloodGenotype(A, O, d, d), null, null);
            BloodGenotype ab = new BloodGenotype(A, B, d, d);
            Assertions.assertDoesNotThrow(() -> person(Sex.MALE, 20, ab, aoMother, null));
            Assertions.assertDoesNotThrow(() -> person(Sex.MALE, 20, ab, null, aoFather));
            Assertions.assertThrows(IllegalArgumentException.class, () -> person(Sex.MALE, 20, ab, aoMother, aoFather));
        }

        private Human person(Sex sex, int ageYears, BloodGenotype genotype, Human mother, Human father) {
            return Human.builder()
                    .body(new Body(sex, ageYears, 1.70, 65))
                    .bloodGenotype(genotype)
                    .physiognomy(physiognomy)
                    .mind(mind)
                    .mother(mother)
                    .father(father)
                    .build();
        }
    }

    @Nested
    class Capacities {

        private final Human francophone = Human.builder()
                .body(new Body(Sex.FEMALE, 30, 1.65, 60))
                .bloodGenotype(O_NEGATIVE)
                .physiognomy(physiognomy)
                .mind(mind)
                .languages(List.of(LanguageSkill.nativeTongue(Languages.FRENCH),
                        LanguageSkill.studied(Languages.ENGLISH, 400)))
                .build();

        @Test
        @DisplayName("a human should speak their mother tongue and the languages they studied enough")
        void speaks() {
            Assertions.assertEquals(Optional.of(Proficiency.NATIVE), francophone.proficiencyIn(Languages.FRENCH));
            Assertions.assertEquals(Optional.of(Proficiency.B1), francophone.proficiencyIn(Languages.ENGLISH));
            Assertions.assertTrue(francophone.speaks(Languages.ENGLISH));
            Assertions.assertFalse(francophone.speaks(Languages.JAPANESE));
            Assertions.assertTrue(human.getLanguages().isEmpty());
        }

        @Test
        @DisplayName("300 words should take 2 minutes to say, and only in a language one speaks")
        void speak() {
            Assertions.assertEquals(2, francophone.speak(Languages.FRENCH, 300).durationMinutes(), 1e-9);
            Assertions.assertThrows(IllegalStateException.class, () -> francophone.speak(Languages.JAPANESE, 10));
        }

        @Test
        @DisplayName("learning should make a new human, more fluent, and leave the old one as they were")
        void learn() {
            Human learned = francophone.learn(Languages.ENGLISH, 150).learn(Languages.JAPANESE, 100);

            Assertions.assertEquals(Optional.of(Proficiency.B2), learned.proficiencyIn(Languages.ENGLISH));
            Assertions.assertEquals(Optional.of(Proficiency.A1), learned.proficiencyIn(Languages.JAPANESE));
            Assertions.assertTrue(learned.speaks(Languages.JAPANESE));
            Assertions.assertEquals(Optional.of(Proficiency.B1), francophone.proficiencyIn(Languages.ENGLISH));
            Assertions.assertEquals(Optional.of(Proficiency.NATIVE),
                    francophone.learn(Languages.FRENCH, 1_000).proficiencyIn(Languages.FRENCH));
            Assertions.assertEquals(learned.getBody(), francophone.getBody());
        }

        @Test
        @DisplayName("a human should know each language only once")
        void rejects_duplicate_languages() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> Human.builder()
                    .body(new Body(Sex.FEMALE, 30, 1.65, 60))
                    .bloodGenotype(O_NEGATIVE)
                    .physiognomy(physiognomy)
                    .mind(mind)
                    .languages(List.of(LanguageSkill.nativeTongue(Languages.FRENCH),
                            LanguageSkill.studied(Languages.FRENCH, 10)))
                    .build());
        }

        @Test
        @DisplayName("a trans woman's identity should be hers to disclose, and her mother tongue kept as she learns")
        void identity() {
            Human transWoman = Human.builder()
                    .body(new Body(Sex.MALE, 30, 1.75, 65))
                    .bloodGenotype(O_NEGATIVE)
                    .physiognomy(physiognomy)
                    .mind(mind)
                    .identity(new Identity(GenderIdentity.WOMAN, SexualOrientation.LESBIAN, Pronouns.SHE_HER))
                    .build();

            Assertions.assertTrue(transWoman.isTransgender());
            Assertions.assertTrue(transWoman.isLgbtq());
            Assertions.assertEquals(Pronouns.SHE_HER, transWoman.getIdentity().orElseThrow().getPronouns());
            Assertions.assertTrue(transWoman.learn(Languages.LATIN, 10).isTransgender());
            Assertions.assertTrue(human.getIdentity().isEmpty());
            Assertions.assertFalse(human.isTransgender());
            Assertions.assertFalse(human.isLgbtq());
        }

        @Test
        @DisplayName("an intersex human should belong to the LGBTQIA+ community, whatever else they disclose")
        void intersex() {
            Human intersex = Human.builder()
                    .body(new Body(Sex.FEMALE, 30, 1.60, 55, IntersexVariation.TURNER_SYNDROME, null))
                    .bloodGenotype(O_NEGATIVE)
                    .physiognomy(physiognomy)
                    .mind(mind)
                    .build();

            Assertions.assertTrue(intersex.isLgbtq());
            Assertions.assertFalse(intersex.isTransgender());
        }

        @Test
        @DisplayName("a 30-year-old sleeping 8 hours should get enough sleep and remember one of five dreams")
        void sleep_and_dream() {
            Sleep night = human.sleep(8);

            Assertions.assertTrue(night.isEnough());
            Assertions.assertEquals(5, night.getDreams().size());
            Assertions.assertEquals(30, night.rememberedDream().orElseThrow().getDurationMinutes());
        }
    }
}
