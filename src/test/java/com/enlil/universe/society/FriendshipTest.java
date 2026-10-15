package com.enlil.universe.society;

import com.enlil.universe.life.human.Human;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FriendshipTest {

    private final Human alice = People.anyone();
    private final Human bob = People.anyone();

    @Test
    @DisplayName("a friendship should go both ways")
    void symmetric() {
        Friendship friendship = Friendship.between(alice, bob, 2010);

        Assertions.assertEquals(Friendship.between(bob, alice, 2015), friendship);
        Assertions.assertEquals(Friendship.between(bob, alice, 2015).hashCode(), friendship.hashCode());
        Assertions.assertSame(bob, friendship.friendOf(alice));
        Assertions.assertSame(alice, friendship.friendOf(bob));
        Assertions.assertEquals(List.of(alice, bob), friendship.getFriends());
        Assertions.assertEquals(2010, friendship.getSinceYear());
    }

    @Test
    @DisplayName("a friendship should not involve strangers, even alike ones")
    void strangers() {
        Human stranger = People.anyone();
        Friendship friendship = Friendship.between(alice, bob, 2010);

        Assertions.assertFalse(friendship.involves(stranger));
        Assertions.assertThrows(IllegalArgumentException.class, () -> friendship.friendOf(stranger));
        Assertions.assertNotEquals(Friendship.between(alice, stranger, 2010), friendship);
    }

    @Test
    @DisplayName("a human should not befriend themselves, nor in year 0")
    void rejects_invalid_friendships() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Friendship.between(alice, alice, 2010));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Friendship.between(alice, bob, 0));
    }
}
