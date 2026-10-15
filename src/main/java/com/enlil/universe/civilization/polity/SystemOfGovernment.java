package com.enlil.universe.civilization.polity;

/** How a republic splits power between its head of state, its government and its parliament. */
public enum SystemOfGovernment {
    /** An elected president both heads the state and leads the government (United States, Brazil). */
    PRESIDENTIAL,
    /** An elected president shares executive power with a prime minister answerable to parliament (France). */
    SEMI_PRESIDENTIAL,
    /** A largely ceremonial president; the government answers to parliament (Germany, Italy, India). */
    PARLIAMENTARY,
    /** A council collectively heads the state and the government (the Swiss Federal Council). */
    DIRECTORIAL,
    /** A single party constitutionally leads the state (China, Vietnam, Cuba). */
    ONE_PARTY
}
