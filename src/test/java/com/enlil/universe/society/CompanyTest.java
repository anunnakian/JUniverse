package com.enlil.universe.society;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    @DisplayName("a company should keep its name and founding year")
    void company() {
        Company company = new Company("Acme", 2015);

        Assertions.assertEquals("Acme", company.getName());
        Assertions.assertEquals(2015, company.getFoundedYear());
        Assertions.assertEquals("Acme", company.toString());
    }

    @Test
    @DisplayName("a company should need a name and a real year")
    void rejects_invalid_companies() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Company(" ", 2015));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Company("Acme", 0));
    }
}
