package com.enlil.universe.society;

import com.enlil.universe.life.human.Human;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * A group of humans, the friendships between them and where they work. From these follow who is whose friend or
 * colleague, and the birthday parties of the year: each member with a known birthday and friends invites all of them.
 * Humans are told apart by identity.
 */
@Getter
public class SocialCircle {

    /**
     * Dunbar's number (Dunbar, 1992): the number of stable relationships a human can keep up, set by the size of the
     * neocortex.
     */
    public static final int DUNBAR_NUMBER = 150;

    private final List<Friendship> friendships;
    private final List<Employment> employments;

    @Builder
    public SocialCircle(@Singular List<Friendship> friendships, @Singular List<Employment> employments) {
        if (new HashSet<>(friendships).size() != friendships.size()) {
            throw new IllegalArgumentException("the same two humans are friends only once");
        }
        Set<Human> employed = new HashSet<>();
        for (Employment employment : employments) {
            if (!employed.add(employment.getEmployee())) {
                throw new IllegalArgumentException("a human holds only one job here");
            }
        }
        this.friendships = List.copyOf(friendships);
        this.employments = List.copyOf(employments);
    }

    /** Everyone in a friendship or an employment of this circle, in the order they first appear. */
    public Set<Human> getMembers() {
        Set<Human> members = new LinkedHashSet<>();
        friendships.forEach(friendship -> members.addAll(friendship.getFriends()));
        employments.forEach(employment -> members.add(employment.getEmployee()));
        return Collections.unmodifiableSet(members);
    }

    public List<Human> friendsOf(Human human) {
        return friendships.stream()
                .filter(friendship -> friendship.involves(human))
                .map(friendship -> friendship.friendOf(human))
                .toList();
    }

    public boolean areFriends(Human first, Human second) {
        return friendsOf(first).contains(second);
    }

    /** The humans who are friends with both. */
    public List<Human> mutualFriends(Human first, Human second) {
        List<Human> theirs = friendsOf(second);
        return friendsOf(first).stream().filter(theirs::contains).toList();
    }

    public Optional<Employment> employmentOf(Human human) {
        return employments.stream().filter(employment -> employment.getEmployee() == human).findFirst();
    }

    /** The other members working in the same company; none for a human without a job. */
    public List<Human> colleaguesOf(Human human) {
        return employmentOf(human)
                .map(mine -> employments.stream()
                        .filter(employment -> employment.getEmployer() == mine.getEmployer())
                        .map(Employment::getEmployee)
                        .filter(employee -> employee != human)
                        .toList())
                .orElse(List.of());
    }

    /** Whether this human's friends and colleagues together outnumber Dunbar's number. */
    public boolean exceedsDunbarNumber(Human human) {
        Set<Human> relationships = new HashSet<>(friendsOf(human));
        relationships.addAll(colleaguesOf(human));
        return relationships.size() > DUNBAR_NUMBER;
    }

    /**
     * The birthday parties of the given year, sorted by date: each member with a known birthday and at least one
     * friend hosts one on their birthday and invites all their friends.
     */
    public List<BirthdayParty> birthdayPartiesIn(int year) {
        List<BirthdayParty> parties = new ArrayList<>();
        for (Human member : getMembers()) {
            Optional<LocalDate> birthday = member.birthdayIn(year);
            List<Human> friends = friendsOf(member);
            if (birthday.isPresent() && !friends.isEmpty()) {
                parties.add(new BirthdayParty(member, birthday.get(), Set.copyOf(friends)));
            }
        }
        parties.sort(Comparator.comparing(BirthdayParty::getDate));
        return List.copyOf(parties);
    }
}
