package com.enlil.universe.world.science;

import com.enlil.universe.civilization.science.Discovery;
import com.enlil.universe.civilization.science.Science;

/**
 * Real discoveries, starting with those this universe is built on: Kepler's and Newton's laws move its planets,
 * Bohr's atom and Mendeleev's table fill its matter, Mendel and Landsteiner give its humans their blood types, the
 * Big Five and Binet their minds, and Lemaître the primeval atom it all comes from.
 */
public final class Discoveries {

    public static final Discovery SYLLOGISM =
            new Discovery("Syllogistic logic", Science.LOGIC, "Aristotle", -350);
    public static final Discovery EUCLID_ELEMENTS =
            new Discovery("Euclid's Elements", Science.MATHEMATICS, "Euclid", -300);
    public static final Discovery DE_ARCHITECTURA =
            new Discovery("De architectura", Science.ENGINEERING, "Vitruvius", -15);
    public static final Discovery ALMAGEST =
            new Discovery("Geocentric model of the Almagest", Science.ASTRONOMY, "Ptolemy", 150);
    public static final Discovery HELIOCENTRISM =
            new Discovery("Heliocentrism", Science.ASTRONOMY, "Nicolaus Copernicus", 1543);
    public static final Discovery KEPLER_LAWS =
            new Discovery("Kepler's laws of planetary motion", Science.ASTRONOMY, "Johannes Kepler", 1619);
    public static final Discovery ANALYTIC_GEOMETRY =
            new Discovery("Analytic geometry", Science.MATHEMATICS, "René Descartes", 1637);
    public static final Discovery MECHANICAL_CALCULATOR =
            new Discovery("Mechanical calculator", Science.ENGINEERING, "Blaise Pascal", 1642);
    public static final Discovery UNIVERSAL_GRAVITATION =
            new Discovery("Law of universal gravitation", Science.PHYSICS, "Isaac Newton", 1687);
    public static final Discovery STEAM_ENGINE_CONDENSER =
            new Discovery("Steam engine with a separate condenser", Science.ENGINEERING, "James Watt", 1769);
    public static final Discovery WEALTH_OF_NATIONS =
            new Discovery("The Wealth of Nations", Science.ECONOMICS, "Adam Smith", 1776);
    public static final Discovery DEEP_TIME =
            new Discovery("Uniformitarianism and deep time", Science.GEOLOGY, "James Hutton", 1788);
    public static final Discovery CONSERVATION_OF_MASS =
            new Discovery("Conservation of mass", Science.CHEMISTRY, "Antoine Lavoisier", 1789);
    public static final Discovery NATURAL_SELECTION =
            new Discovery("Evolution by natural selection", Science.BIOLOGY, "Charles Darwin", 1859);
    public static final Discovery MENDELIAN_INHERITANCE =
            new Discovery("Laws of inheritance", Science.BIOLOGY, "Gregor Mendel", 1866);
    public static final Discovery PERIODIC_TABLE =
            new Discovery("Periodic table of the elements", Science.CHEMISTRY, "Dmitri Mendeleev", 1869);
    public static final Discovery RABIES_VACCINE =
            new Discovery("Rabies vaccine", Science.MEDICINE, "Louis Pasteur", 1885);
    public static final Discovery SOCIAL_FACTS =
            new Discovery("The Rules of Sociological Method", Science.SOCIOLOGY, "Émile Durkheim", 1895);
    public static final Discovery RADIOACTIVITY =
            new Discovery("Radioactivity", Science.PHYSICS, "Henri Becquerel", 1896);
    public static final Discovery ABO_BLOOD_GROUPS =
            new Discovery("ABO blood groups", Science.MEDICINE, "Karl Landsteiner", 1901);
    public static final Discovery INTELLIGENCE_TEST =
            new Discovery("Intelligence test", Science.PSYCHOLOGY, "Alfred Binet and Théodore Simon", 1905);
    public static final Discovery BOHR_ATOM =
            new Discovery("Bohr model of the atom", Science.PHYSICS, "Niels Bohr", 1913);
    public static final Discovery GENERAL_RELATIVITY =
            new Discovery("General relativity", Science.PHYSICS, "Albert Einstein", 1915);
    public static final Discovery STRUCTURAL_LINGUISTICS =
            new Discovery("Course in General Linguistics", Science.LINGUISTICS, "Ferdinand de Saussure", 1916);
    public static final Discovery PRIMEVAL_ATOM =
            new Discovery("Expanding universe", Science.ASTRONOMY, "Georges Lemaître", 1927);
    public static final Discovery PENICILLIN =
            new Discovery("Penicillin", Science.MEDICINE, "Alexander Fleming", 1928);
    public static final Discovery HUBBLE_LAW =
            new Discovery("Velocity–distance relation of galaxies", Science.ASTRONOMY, "Edwin Hubble", 1929);
    public static final Discovery TURING_MACHINE =
            new Discovery("Turing machine", Science.COMPUTER_SCIENCE, "Alan Turing", 1936);
    public static final Discovery DNA_DOUBLE_HELIX =
            new Discovery("Double helix of DNA", Science.BIOLOGY,
                    "James Watson, Francis Crick and Rosalind Franklin", 1953);
    public static final Discovery COSMIC_MICROWAVE_BACKGROUND =
            new Discovery("Cosmic microwave background", Science.ASTRONOMY, "Arno Penzias and Robert Wilson", 1965);
    public static final Discovery BIG_FIVE =
            new Discovery("Five personality factors", Science.PSYCHOLOGY, "Ernest Tupes and Raymond Christal", 1961);

    private Discoveries() {
    }
}
