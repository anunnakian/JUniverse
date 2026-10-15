package com.enlil.universe.society;

import com.enlil.universe.life.human.Human;
import com.enlil.universe.world.occupation.Doctor;
import com.enlil.universe.world.occupation.Engineer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SocialCircleTest {

    private final Human me = People.bornOn(Month.MARCH, 3);
    private final Human alice = People.bornOn(Month.JULY, 14);
    private final Human bob = People.bornOn(Month.JANUARY, 20);
    private final Human colleague = People.bornOn(Month.MAY, 1);
    private final Company acme = new Company("Acme", 2015);
    private final Employment myJob = new Employment(me, acme, new Engineer(), 2020);
    private final SocialCircle circle = SocialCircle.builder()
            .friendship(Friendship.between(me, alice, 2008))
            .friendship(Friendship.between(me, bob, 2012))
            .friendship(Friendship.between(alice, bob, 2012))
            .employment(myJob)
            .employment(new Employment(colleague, acme, new Doctor(), 2021))
            .employment(new Employment(alice, new Company("Initech", 1999), new Engineer(), 2019))
            .build();

    @Test
    @DisplayName("the circle should gather everyone, in the order they appear")
    void members() {
        Assertions.assertEquals(List.of(me, alice, bob, colleague), List.copyOf(circle.getMembers()));
    }

    @Test
    @DisplayName("friends should be known both ways, with their mutual friends")
    void friends() {
        Assertions.assertEquals(List.of(alice, bob), circle.friendsOf(me));
        Assertions.assertTrue(circle.areFriends(bob, me));
        Assertions.assertFalse(circle.areFriends(me, colleague));
        Assertions.assertEquals(List.of(bob), circle.mutualFriends(me, alice));
        Assertions.assertEquals(List.of(), circle.friendsOf(colleague));
    }

    @Test
    @DisplayName("colleagues should be those working in the same company")
    void colleagues() {
        Assertions.assertEquals(Optional.of(myJob), circle.employmentOf(me));
        Assertions.assertEquals(List.of(colleague), circle.colleaguesOf(me));
        Assertions.assertEquals(List.of(), circle.colleaguesOf(alice));
        Assertions.assertEquals(Optional.empty(), circle.employmentOf(bob));
        Assertions.assertEquals(List.of(), circle.colleaguesOf(bob));
    }

    @Test
    @DisplayName("the same friendship twice, or two jobs for one human, should be rejected")
    void rejects_duplicates() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SocialCircle.builder()
                .friendship(Friendship.between(me, alice, 2008))
                .friendship(Friendship.between(alice, me, 2010))
                .build());
        Assertions.assertThrows(IllegalArgumentException.class, () -> SocialCircle.builder()
                .employment(myJob)
                .employment(new Employment(me, new Company("Initech", 1999), new Engineer(), 2010))
                .build());
    }

    @Test
    @DisplayName("150 friends should be within Dunbar's number, 151 beyond it")
    void dunbar_number() {
        SocialCircle.SocialCircleBuilder builder = SocialCircle.builder();
        for (int i = 0; i < SocialCircle.DUNBAR_NUMBER; i++) {
            builder.friendship(Friendship.between(me, People.anyone(), 2020));
        }

        Assertions.assertFalse(builder.build().exceedsDunbarNumber(me));
        Assertions.assertTrue(builder.friendship(Friendship.between(me, alice, 2020)).build().exceedsDunbarNumber(me));
    }

    @Test
    @DisplayName("each member with friends should throw a party on their birthday, the year's parties sorted by date")
    void birthday_parties() {
        Human mystery = People.anyone();
        SocialCircle withMystery = SocialCircle.builder()
                .friendships(circle.getFriendships())
                .friendship(Friendship.between(mystery, bob, 2020))
                .employments(circle.getEmployments())
                .build();

        List<BirthdayParty> parties = withMystery.birthdayPartiesIn(2026);

        Assertions.assertEquals(List.of(bob, me, alice), parties.stream().map(BirthdayParty::getHost).toList());
        Assertions.assertEquals(LocalDate.of(2026, 1, 20), parties.get(0).getDate());
        Assertions.assertEquals(Set.of(me, alice, mystery), parties.get(0).getGuests());
        Assertions.assertEquals(3, parties.get(1).headcount());
    }
}
