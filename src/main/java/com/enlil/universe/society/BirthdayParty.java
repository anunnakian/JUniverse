package com.enlil.universe.society;

import com.enlil.universe.life.human.Human;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Optional;
import java.util.Set;

/** A party a human throws on their birthday for their guests. Humans are told apart by identity. */
@Getter
public class BirthdayParty {

    private final Human host;
    private final LocalDate date;
    private final Set<Human> guests;

    public BirthdayParty(Human host, LocalDate date, Set<Human> guests) {
        Optional<LocalDate> birthday = host.birthdayIn(date.getYear());
        if (birthday.isEmpty()) {
            throw new IllegalArgumentException("the host's birthday is unknown");
        }
        if (!birthday.get().equals(date)) {
            throw new IllegalArgumentException("a birthday party is held on the host's birthday, " + birthday.get());
        }
        Set<Human> invited = Collections.newSetFromMap(new IdentityHashMap<>());
        invited.addAll(guests);
        if (invited.isEmpty()) {
            throw new IllegalArgumentException("a party needs at least one guest");
        }
        if (invited.contains(host)) {
            throw new IllegalArgumentException("the host is not their own guest");
        }
        this.host = host;
        this.date = date;
        this.guests = Collections.unmodifiableSet(invited);
    }

    /** Everyone at the party: the guests and the host. */
    public int headcount() {
        return guests.size() + 1;
    }
}
