package com.enlil.universe.society;

import com.enlil.universe.life.human.Human;
import com.enlil.universe.world.occupation.Engineer;
import com.enlil.universe.world.occupation.Samurai;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmploymentTest {

    private final Human human = People.anyone();
    private final Company acme = new Company("Acme", 2015);

    @Test
    @DisplayName("an engineer at Acme since 2020 should be described as such")
    void employment() {
        Employment employment = new Employment(human, acme, new Engineer(), 2020);

        Assertions.assertSame(human, employment.getEmployee());
        Assertions.assertSame(acme, employment.getEmployer());
        Assertions.assertEquals(2020, employment.getSinceYear());
        Assertions.assertEquals("Engineer at Acme since 2020", employment.toString());
    }

    @Test
    @DisplayName("no one should work in a company before it was founded")
    void rejects_work_before_founding() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Employment(human, acme, new Engineer(), 2010));
    }

    @Test
    @DisplayName("no one should be hired as a samurai in 2020, 144 years after they were abolished")
    void rejects_extinct_occupations() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Employment(human, acme, new Samurai(), 2020));
    }
}
