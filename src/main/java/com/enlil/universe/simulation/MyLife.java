package com.enlil.universe.simulation;

import com.enlil.universe.life.human.AboAllele;
import com.enlil.universe.life.human.BloodGenotype;
import com.enlil.universe.life.human.Body;
import com.enlil.universe.life.human.EyeColor;
import com.enlil.universe.life.human.HairColor;
import com.enlil.universe.life.human.HairTexture;
import com.enlil.universe.life.human.Human;
import com.enlil.universe.life.human.Mind;
import com.enlil.universe.life.human.Personality;
import com.enlil.universe.life.human.Physiognomy;
import com.enlil.universe.life.human.RhAllele;
import com.enlil.universe.life.human.Sex;
import com.enlil.universe.life.human.SkinType;
import com.enlil.universe.life.human.activity.Activity;
import com.enlil.universe.society.BirthdayParty;
import com.enlil.universe.society.Company;
import com.enlil.universe.society.Employment;
import com.enlil.universe.society.Friendship;
import com.enlil.universe.society.SocialCircle;
import com.enlil.universe.world.activity.Activities;
import com.enlil.universe.world.occupation.Doctor;
import com.enlil.universe.world.occupation.Engineer;
import com.enlil.universe.world.occupation.Teacher;

import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tells the story of one life: "I do judo, ski and surf, I like mangas and drawing. I work in a company, I have
 * friends, and we party on our birthdays." The people, the company and the jobs are made-up placeholders, to be
 * replaced with real ones.
 * <p>
 * Run it with {@code mvn compile && java -cp target/classes com.enlil.universe.simulation.MyLife}.
 */
public class MyLife {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        int today = Year.now().getValue();

        Human me = person(Sex.MALE, 32, 1.78, 74, Month.MARCH, 3, Set.of(Activities.JUDO, Activities.SKIING,
                Activities.SURFING, Activities.MANGA, Activities.DRAWING));
        Human yanis = person(Sex.MALE, 33, 1.82, 80, Month.JANUARY, 20, Set.of(Activities.FOOTBALL));
        Human sarah = person(Sex.FEMALE, 31, 1.66, 58, Month.JULY, 14, Set.of(Activities.DRAWING, Activities.DANCE));
        Human ines = person(Sex.FEMALE, 29, 1.70, 62, Month.FEBRUARY, 29, Set.of(Activities.SURFING));
        Human colleague = person(Sex.FEMALE, 41, 1.62, 60, Month.OCTOBER, 2, Set.of(Activities.READING));
        Map<Human, String> names = new IdentityHashMap<>(Map.of(
                me, "me", yanis, "Yanis", sarah, "Sarah", ines, "Ines", colleague, "Claire"));

        Company acme = new Company("Acme", 2012);
        SocialCircle circle = SocialCircle.builder()
                .friendship(Friendship.between(me, yanis, 2005))
                .friendship(Friendship.between(me, sarah, 2011))
                .friendship(Friendship.between(me, ines, 2018))
                .friendship(Friendship.between(yanis, sarah, 2011))
                .friendship(Friendship.between(sarah, ines, 2019))
                .employment(new Employment(me, acme, new Engineer(), 2019))
                .employment(new Employment(colleague, acme, new Engineer(), 2014))
                .employment(new Employment(sarah, new Company("Victor Hugo High School", 1896), new Teacher(), 2016))
                .employment(new Employment(yanis, new Company("CHU de Bordeaux", 1964), new Doctor(), 2020))
                .build();

        System.out.printf("MY LIFE, IN %d%n%n", today);

        System.out.println("What I do:");
        me.getMind().getHobbies().stream()
                .sorted(Comparator.comparing(Activity::getMetabolicEquivalent).reversed())
                .forEach(hobby -> System.out.printf("  %-8s %-14s %-10s %4.0f kcal an hour%n", hobby,
                        hobby.getCategory().name().toLowerCase().replace('_', ' '),
                        hobby.getIntensity().name().toLowerCase(), me.kcalBurned(hobby, 1)));

        Employment job = circle.employmentOf(me).orElseThrow();
        System.out.printf("%nWhere I work: %s at %s since %d (%d years), with %s%n", job.getOccupation(),
                job.getEmployer(), job.getSinceYear(), today - job.getSinceYear(),
                namesOf(circle.colleaguesOf(me), names));

        System.out.printf("%nMy friends: %s%n", namesOf(circle.friendsOf(me), names));
        for (Human friend : circle.friendsOf(me)) {
            System.out.printf("  %-6s %s, %s%n", names.get(friend),
                    circle.employmentOf(friend).map(Employment::toString).orElse("not working"),
                    circle.mutualFriends(me, friend).isEmpty()
                            ? "no friend in common"
                            : "also friends with " + namesOf(circle.mutualFriends(me, friend), names));
        }

        System.out.printf("%nOur birthday parties in %d:%n", today);
        for (BirthdayParty party : circle.birthdayPartiesIn(today)) {
            MonthDay birthday = party.getHost().getBirthday().orElseThrow();
            System.out.printf("  %2d %-9s at %s, %d of us%s%n", party.getDate().getDayOfMonth(),
                    party.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                    party.getHost() == me ? "my place" : names.get(party.getHost()) + "'s", party.headcount(),
                    birthday.equals(MonthDay.of(Month.FEBRUARY, 29)) && !Year.isLeap(today)
                            ? " (no 29 February this year)" : "");
        }
    }

    private static Human person(Sex sex, int ageYears, double heightMeters, double massKg, Month month, int day,
                                Set<Activity> hobbies) {
        return Human.builder()
                .body(new Body(sex, ageYears, heightMeters, massKg))
                .bloodGenotype(new BloodGenotype(AboAllele.A, AboAllele.O, RhAllele.D, RhAllele.d))
                .physiognomy(new Physiognomy(EyeColor.BROWN, HairColor.BLACK, HairTexture.CURLY, SkinType.IV))
                .mind(new Mind(new Personality(70, 55, 75, 70, 35), 110, hobbies))
                .birthday(MonthDay.of(month, day))
                .build();
    }

    private static String namesOf(List<Human> humans, Map<Human, String> names) {
        return humans.stream().map(names::get).collect(Collectors.joining(", "));
    }
}
