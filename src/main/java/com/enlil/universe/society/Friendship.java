package com.enlil.universe.society;

import com.enlil.universe.life.human.Human;
import lombok.Getter;

import java.util.List;

/**
 * A friendship between two humans, since a given year. It goes both ways: the friendship between a and b is the one
 * between b and a. Humans are told apart by identity.
 */
public class Friendship {

    private final Human first;
    private final Human second;
    @Getter
    private final int sinceYear;

    private Friendship(Human first, Human second, int sinceYear) {
        if (first == second) {
            throw new IllegalArgumentException("a human cannot be their own friend here");
        }
        if (sinceYear == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        this.first = first;
        this.second = second;
        this.sinceYear = sinceYear;
    }

    public static Friendship between(Human first, Human second, int sinceYear) {
        return new Friendship(first, second, sinceYear);
    }

    /** The two friends. */
    public List<Human> getFriends() {
        return List.of(first, second);
    }

    public boolean involves(Human human) {
        return human == first || human == second;
    }

    /**
     * The other friend.
     *
     * @throws IllegalArgumentException if the given human is not part of this friendship
     */
    public Human friendOf(Human human) {
        if (human == first) {
            return second;
        }
        if (human == second) {
            return first;
        }
        throw new IllegalArgumentException("this human is not part of the friendship");
    }

    /** Whether the other friendship joins the same two humans, whatever the order and the year. */
    @Override
    public boolean equals(Object other) {
        return other instanceof Friendship friendship && friendship.involves(first) && friendship.involves(second);
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(first) ^ System.identityHashCode(second);
    }
}
