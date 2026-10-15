package com.enlil.universe.life.human;

import com.enlil.universe.life.Creature;
import com.enlil.universe.life.Kingdom;
import com.enlil.universe.life.human.activity.Activity;
import com.enlil.universe.life.human.identity.Identity;
import com.enlil.universe.life.human.language.Language;
import com.enlil.universe.life.human.language.LanguageSkill;
import com.enlil.universe.life.human.language.Proficiency;
import com.enlil.universe.life.human.language.Speech;
import com.enlil.universe.life.human.sleep.Sleep;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * An adult human: a body, from which the blood volume is derived, an inherited blood genotype, the reference organs of
 * their sex, their visible features and a mind. A human may know their mother and father; the family tree is only
 * linked from children to parents, so it stays immutable. A human can speak the languages they know, learn more of
 * them (becoming a new, more learned human), sleep, and dream while asleep. Their gender identity, sexual orientation
 * and pronouns are theirs to disclose: nothing is assumed when they don't.
 */
@Getter
public class Human extends Creature {

    private final Body body;
    private final Blood blood;
    private final Organs organs;
    private final Physiognomy physiognomy;
    private final Mind mind;
    @Getter(AccessLevel.NONE)
    private final Human mother;
    @Getter(AccessLevel.NONE)
    private final Human father;
    /** The languages this human knows, each once. */
    private final List<LanguageSkill> languages;
    /** Null when not disclosed. */
    @Getter(AccessLevel.NONE)
    private final Identity identity;

    /**
     * @param mother the biological mother, the parent who gave birth, assigned female whatever their gender; or
     *               {@code null} if unknown
     * @param father the biological father, the parent assigned male; or {@code null} if unknown
     * @param languages the languages this human knows, or {@code null} for none
     * @param identity their gender identity, orientation and pronouns, or {@code null} if not disclosed
     */
    @Builder
    public Human(Body body, BloodGenotype bloodGenotype, Physiognomy physiognomy, Mind mind, Human mother, Human father,
                 List<LanguageSkill> languages, Identity identity) {
        super("Homo sapiens", Kingdom.ANIMALIA, body.getMassKg());
        checkParent(mother, Sex.FEMALE, body);
        checkParent(father, Sex.MALE, body);
        checkInheritance(bloodGenotype, mother, father);
        this.body = body;
        this.blood = new Blood(body, bloodGenotype);
        this.organs = Organs.reference(body.getSex());
        this.physiognomy = physiognomy;
        this.mind = mind;
        this.mother = mother;
        this.father = father;
        this.languages = languages == null ? List.of() : List.copyOf(languages);
        this.identity = identity;
        if (this.languages.stream().map(LanguageSkill::getLanguage).distinct().count() != this.languages.size()) {
            throw new IllegalArgumentException("each language is known only once");
        }
    }

    private static void checkParent(Human parent, Sex sex, Body child) {
        if (parent == null) {
            return;
        }
        if (parent.body.getSex() != sex) {
            throw new IllegalArgumentException("a " + (sex == Sex.FEMALE ? "mother" : "father") + " must be " + sex);
        }
        if (parent.body.getAgeYears() <= child.getAgeYears()) {
            throw new IllegalArgumentException("a parent must be older than their child");
        }
    }

    private static void checkInheritance(BloodGenotype child, Human mother, Human father) {
        boolean possible;
        if (mother != null && father != null) {
            possible = mother.genotype().offspring(father.genotype()).containsKey(child);
        } else {
            possible = Stream.of(mother, father)
                    .filter(Objects::nonNull)
                    .allMatch(parent -> child.canInheritFrom(parent.genotype()));
        }
        if (!possible) {
            throw new IllegalArgumentException("blood genotype " + child + " cannot be inherited from the parents");
        }
    }

    private BloodGenotype genotype() {
        return blood.getGenotype();
    }

    /** Energy this human spends doing the given activity for the given hours, in kcal. */
    public double kcalBurned(Activity activity, double hours) {
        return activity.kcalBurned(body, hours);
    }

    /** How well this human knows the given language; empty if they never learned a word of it. */
    public Optional<Proficiency> proficiencyIn(Language language) {
        return skillIn(language).map(LanguageSkill::getProficiency);
    }

    /** Whether this human can hold a conversation in the given language, at level A1 or above. */
    public boolean speaks(Language language) {
        return proficiencyIn(language).map(Proficiency::canSpeak).orElse(false);
    }

    /**
     * Says the given number of words in the given language.
     *
     * @throws IllegalStateException if this human does not speak the language
     */
    public Speech speak(Language language, int words) {
        if (!speaks(language)) {
            throw new IllegalStateException("this human does not speak " + language);
        }
        return new Speech(language, words);
    }

    /** This same human after studying the given language for the given hours, starting from scratch if new to it. */
    public Human learn(Language language, double hours) {
        LanguageSkill learned = skillIn(language)
                .orElse(LanguageSkill.studied(language, 0))
                .afterStudying(hours);
        List<LanguageSkill> updated = new ArrayList<>();
        for (LanguageSkill skill : languages) {
            if (skill.getLanguage() != language) {
                updated.add(skill);
            }
        }
        updated.add(learned);
        return new Human(body, genotype(), physiognomy, mind, mother, father, updated, identity);
    }

    /** Their gender identity, sexual orientation and pronouns; empty if they did not disclose them. */
    public Optional<Identity> getIdentity() {
        return Optional.ofNullable(identity);
    }

    /** Whether this human disclosed a gender identity other than their sex assigned at birth. */
    public boolean isTransgender() {
        return identity != null && identity.isTransgender(body.getSex());
    }

    /** Whether this human is part of the LGBTQ+ community by what they disclosed, or by being intersex (LGBTQIA+). */
    public boolean isLgbtq() {
        return body.isIntersex() || identity != null && identity.isLgbtq(body.getSex());
    }

    /** A night's sleep of the given hours, judged against the recommendation for this human's age. */
    public Sleep sleep(double hours) {
        return new Sleep(hours, body.getAgeYears());
    }

    private Optional<LanguageSkill> skillIn(Language language) {
        return languages.stream().filter(skill -> skill.getLanguage() == language).findFirst();
    }

    public Optional<Human> getMother() {
        return Optional.ofNullable(mother);
    }

    public Optional<Human> getFather() {
        return Optional.ofNullable(father);
    }

    /** All known ancestors: parents, grandparents, and so on. */
    public Set<Human> getAncestors() {
        Set<Human> ancestors = new LinkedHashSet<>();
        for (Human parent : parents()) {
            ancestors.add(parent);
            ancestors.addAll(parent.getAncestors());
        }
        return Collections.unmodifiableSet(ancestors);
    }

    /** Whether the other human shares at least one parent with this one. */
    public boolean isSiblingOf(Human other) {
        return other != this && parents().stream().anyMatch(parent -> other.parents().contains(parent));
    }

    /**
     * Wright's coefficient of relationship: the expected share of genes the two humans have inherited from common
     * ancestors. Each path joining them through a common ancestor, without passing through anyone twice, contributes
     * (1/2) per step. Inbreeding of the common ancestors is ignored.
     */
    public double relationshipCoefficient(Human other) {
        if (other == this) {
            return 1;
        }
        double coefficient = 0;
        for (List<Human> mine : lineages()) {
            for (List<Human> theirs : other.lineages()) {
                Human ancestor = mine.get(mine.size() - 1);
                if (ancestor == theirs.get(theirs.size() - 1) && onlyMeetAtTheEnd(mine, theirs)) {
                    coefficient += Math.pow(0.5, mine.size() - 1 + theirs.size() - 1);
                }
            }
        }
        return coefficient;
    }

    private List<Human> parents() {
        return Stream.of(mother, father).filter(Objects::nonNull).toList();
    }

    /** Every upward path from this human to one of their ancestors, starting with this human alone. */
    private List<List<Human>> lineages() {
        List<List<Human>> lineages = new ArrayList<>();
        lineages.add(List.of(this));
        for (Human parent : parents()) {
            for (List<Human> lineage : parent.lineages()) {
                List<Human> path = new ArrayList<>();
                path.add(this);
                path.addAll(lineage);
                lineages.add(path);
            }
        }
        return lineages;
    }

    private static boolean onlyMeetAtTheEnd(List<Human> mine, List<Human> theirs) {
        Set<Human> shared = new HashSet<>(mine);
        shared.retainAll(theirs);
        return shared.size() == 1;
    }
}
