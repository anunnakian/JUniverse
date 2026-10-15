package com.enlil.universe.society;

import com.enlil.universe.life.human.Human;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

public class BirthdayPartyTest {

    private final Human host = People.bornOn(Month.JULY, 14);
    private final Human guest = People.anyone();

    @Test
    @DisplayName("a party on the host's birthday with two guests should gather three people")
    void party() {
        BirthdayParty party = new BirthdayParty(host, LocalDate.of(2026, 7, 14), Set.of(guest, People.anyone()));

        Assertions.assertSame(host, party.getHost());
        Assertions.assertEquals(2, party.getGuests().size());
        Assertions.assertEquals(3, party.headcount());
    }

    @Test
    @DisplayName("a birthday party should be held on the host's known birthday")
    void rejects_wrong_dates() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BirthdayParty(host, LocalDate.of(2026, 7, 15), Set.of(guest)));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BirthdayParty(guest, LocalDate.of(2026, 7, 14), Set.of(host)));
    }

    @Test
    @DisplayName("a party should have guests, and the host should not be one")
    void rejects_invalid_guests() {
        LocalDate birthday = LocalDate.of(2026, 7, 14);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new BirthdayParty(host, birthday, Set.of()));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BirthdayParty(host, birthday, Set.of(host, guest)));
    }
}
