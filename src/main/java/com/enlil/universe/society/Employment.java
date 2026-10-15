package com.enlil.universe.society;

import com.enlil.universe.civilization.occupation.Occupation;
import com.enlil.universe.life.human.Human;
import lombok.Getter;

/** A human working in a company, at a given occupation, since a given year. */
@Getter
public class Employment {

    private final Human employee;
    private final Company employer;
    private final Occupation occupation;
    private final int sinceYear;

    public Employment(Human employee, Company employer, Occupation occupation, int sinceYear) {
        if (sinceYear == 0) {
            throw new IllegalArgumentException("there is no year 0");
        }
        if (sinceYear < employer.getFoundedYear()) {
            throw new IllegalArgumentException("no one works in " + employer + " before it was founded");
        }
        if (!occupation.existedIn(sinceYear)) {
            throw new IllegalArgumentException("no one was a " + occupation + " in " + sinceYear);
        }
        this.employee = employee;
        this.employer = employer;
        this.occupation = occupation;
        this.sinceYear = sinceYear;
    }

    @Override
    public String toString() {
        return occupation + " at " + employer + " since " + sinceYear;
    }
}
