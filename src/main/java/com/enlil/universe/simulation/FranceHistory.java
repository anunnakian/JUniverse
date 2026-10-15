package com.enlil.universe.simulation;

import com.enlil.universe.civilization.economy.Currency;
import com.enlil.universe.civilization.economy.Money;
import com.enlil.universe.civilization.history.HistoricalEvent;
import com.enlil.universe.civilization.occupation.Occupation;
import com.enlil.universe.civilization.polity.Empire;
import com.enlil.universe.civilization.polity.Kingdom;
import com.enlil.universe.civilization.polity.Monarchy;
import com.enlil.universe.civilization.polity.Polity;
import com.enlil.universe.civilization.polity.Republic;
import com.enlil.universe.civilization.science.Discovery;
import com.enlil.universe.world.economy.Currencies;
import com.enlil.universe.world.history.HistoryOfFrance;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.NavigableMap;

/**
 * Simulates the history of France from Clovis to today, year by year: regimes rise and fall, the money changes, events
 * happen, discoveries are made, and occupations appear and disappear. Then it sums up fifteen centuries.
 * <p>
 * Run it with {@code mvn compile && java -cp target/classes com.enlil.universe.simulation.FranceHistory}.
 */
public class FranceHistory {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        int today = Year.now().getValue();
        List<Polity> regimes = HistoryOfFrance.regimes();
        List<HistoricalEvent> events = HistoryOfFrance.events();
        List<Discovery> discoveries = HistoryOfFrance.discoveries();
        List<Occupation> occupations = HistoryOfFrance.occupations();
        NavigableMap<Integer, Currency> currencies = HistoryOfFrance.currencies();

        System.out.printf("THE HISTORY OF FRANCE, FROM CLOVIS (%d) TO %d%n%n", HistoryOfFrance.CLOVIS_YEAR, today);
        System.out.printf("Already practised when Clovis was raised on the shield: %s%n%n", occupations.stream()
                .filter(occupation -> occupation.existedIn(HistoryOfFrance.CLOVIS_YEAR))
                .map(Occupation::getName)
                .toList());

        Currency money = null;
        boolean ruled = false;
        for (int year = HistoryOfFrance.CLOVIS_YEAR; year <= today; year++) {
            List<String> lines = new ArrayList<>();
            for (Polity regime : regimes) {
                if (regime.getDissolvedYear().orElse(Integer.MIN_VALUE) == year) {
                    lines.add(String.format("  - %s ends, after %d years", regime.getName(),
                            regime.durationYears().orElseThrow()));
                }
            }
            for (Polity regime : regimes) {
                if (regime.getFoundedYear() == year) {
                    lines.add(String.format("  + %s: %s, its %s ruling from %s, %s", regime.getName(),
                            formOf(regime), regime.getHeadOfStateTitle(), regime.getCapital(),
                            regime.getSuccession().name().toLowerCase()));
                }
            }
            Currency newMoney = currencies.get(year);
            if (newMoney != null) {
                lines.add(String.format("  $ Money: %s%s", money == null ? "" : money.getName() + " -> ",
                        describe(newMoney)));
                money = newMoney;
            }
            int current = year;
            boolean ruledThisYear = regimes.stream().anyMatch(regime -> regime.existedIn(current));
            if (ruled && !ruledThisYear) {
                lines.add("  ? No modeled regime: France is run by a provisional government");
            }
            ruled = ruledThisYear;
            for (HistoricalEvent event : events) {
                if (event.getYear() == year) {
                    lines.add("    " + event.getDescription());
                }
            }
            for (Discovery discovery : discoveries) {
                if (discovery.getYear() == year) {
                    lines.add(String.format("  * Discovery in %s: %s, by %s", discovery.getScience(),
                            discovery.getName(), discovery.getDiscoverer()));
                }
            }
            for (Occupation occupation : occupations) {
                if (occupation.getSinceYear() == year) {
                    lines.add("  > " + occupation.getName() + "s appear");
                }
                if (occupation.getUntilYear().orElse(Integer.MIN_VALUE) == year) {
                    lines.add("  < The last " + occupation.getName().toLowerCase() + "s");
                }
            }
            if (!lines.isEmpty()) {
                System.out.println(year);
                lines.forEach(System.out::println);
            }
        }

        summarize(regimes, currencies, today);
    }

    private static void summarize(List<Polity> regimes, NavigableMap<Integer, Currency> currencies, int today) {
        System.out.printf("%nFIFTEEN CENTURIES IN NUMBERS%n%n");
        long others = regimes.stream()
                .filter(regime -> !(regime instanceof Monarchy) && !(regime instanceof Republic))
                .count();
        System.out.printf("France since Clovis: %d years and %d regimes:"
                        + " %d kingdoms, %d empires, %d republics, %d other%n",
                today - HistoryOfFrance.CLOVIS_YEAR, regimes.size(), count(regimes, Kingdom.class),
                count(regimes, Empire.class), count(regimes, Republic.class), others);

        int monarchyYears = 0;
        int republicYears = 0;
        for (Polity regime : regimes) {
            int years = lifespan(regime, today);
            if (regime instanceof Monarchy) {
                monarchyYears += years;
            } else if (regime instanceof Republic) {
                republicYears += years;
            }
        }
        System.out.printf("Years under a monarch: %d; under a republic: %d%n", monarchyYears, republicYears);

        Comparator<Polity> byLifespan = Comparator.comparingInt(regime -> lifespan(regime, today));
        Polity longest = regimes.stream().max(byLifespan).orElseThrow();
        Polity shortest = regimes.stream().min(byLifespan).orElseThrow();
        System.out.printf("Longest regime: the %s, %d years; shortest: the %s, %d years%n",
                longest.getName(), lifespan(longest, today), shortest.getName(), lifespan(shortest, today));
        Polity biggest = regimes.stream().max(Comparator.comparingDouble(Polity::getAreaSquareKm)).orElseThrow();
        System.out.printf("Largest: the %s, %.0f km^2 at its height, %.1f times today's France%n",
                biggest.getName(), biggest.getAreaSquareKm(),
                biggest.getAreaSquareKm() / HistoryOfFrance.FIFTH_REPUBLIC.getAreaSquareKm());
        System.out.printf("People: from about %,d under Clovis's heirs to %,d today%n",
                HistoryOfFrance.KINGDOM_OF_THE_FRANKS.getPopulation(),
                HistoryOfFrance.FIFTH_REPUBLIC.getPopulation());

        System.out.printf("Money: %s%n", currencies.values().stream().map(Currency::getName).toList());
        Money francs = Money.of(Currencies.FRENCH_FRANC, 100);
        Money euros = Money.of(Currencies.EURO, francs.getAmount() / HistoryOfFrance.FRANCS_PER_EURO);
        System.out.printf("100 francs became %.2f euros in 2002; at 2%% inflation a year, those euros buy %.2f euros"
                        + " of %d goods%n",
                euros.getAmount(), euros.purchasingPowerAfter(0.02, today - 2002).getAmount(), today);
    }

    /** Years a regime lasted, or has lasted so far. */
    private static int lifespan(Polity regime, int today) {
        return regime.durationYears().orElse(regime.ageYears(today));
    }

    private static long count(List<Polity> regimes, Class<? extends Polity> form) {
        return regimes.stream().filter(form::isInstance).count();
    }

    private static String formOf(Polity regime) {
        if (regime instanceof Monarchy monarchy) {
            String form = regime instanceof Empire ? "an empire" : "a kingdom";
            return form + (monarchy.isAbsolute() ? ", absolute" : ", constitutional");
        }
        if (regime instanceof Republic republic) {
            return "a " + republic.getSystemOfGovernment().name().toLowerCase().replace('_', '-') + " republic";
        }
        return "an authoritarian state";
    }

    private static String describe(Currency currency) {
        return String.format("%s, %s money, 1 = %d %s", currency.getName(),
                currency.getStandard().name().toLowerCase(), currency.getMinorUnitsPerUnit(),
                currency.getMinorUnitName().orElse("minor units"));
    }
}
