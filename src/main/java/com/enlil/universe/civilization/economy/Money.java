package com.enlil.universe.civilization.economy;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Locale;

/**
 * An amount of money, counted exactly in its currency's minor units (cents, pence, asses...) so that no fraction of a
 * cent is ever lost to floating point.
 */
@Getter
@EqualsAndHashCode
public class Money {

    private final Currency currency;
    private final long minorUnits;

    public Money(Currency currency, long minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    /** The given amount of units, rounded to the nearest minor unit. */
    public static Money of(Currency currency, double amount) {
        return new Money(currency, Math.round(amount * currency.getMinorUnitsPerUnit()));
    }

    /** This amount in units of its currency. */
    public double getAmount() {
        return (double) minorUnits / currency.getMinorUnitsPerUnit();
    }

    public Money plus(Money other) {
        return new Money(currency, Math.addExact(minorUnits, sameCurrency(other).minorUnits));
    }

    public Money minus(Money other) {
        return new Money(currency, Math.subtractExact(minorUnits, sameCurrency(other).minorUnits));
    }

    public Money times(double factor) {
        return new Money(currency, Math.round(minorUnits * factor));
    }

    /**
     * What this amount will be worth in today's money after the given years of compound inflation at the given annual
     * rate (0.02 for 2 % a year).
     */
    public Money purchasingPowerAfter(double annualInflationRate, double years) {
        if (annualInflationRate <= -1) {
            throw new IllegalArgumentException("prices cannot fall by 100 % or more");
        }
        return times(Math.pow(1 + annualInflationRate, -years));
    }

    /** Years for prices to double at the given annual inflation rate: ln 2 / ln(1 + r), close to 70 / percent. */
    public static double yearsToDoublePrices(double annualInflationRate) {
        if (annualInflationRate <= 0) {
            throw new IllegalArgumentException("prices only double under positive inflation");
        }
        return Math.log(2) / Math.log1p(annualInflationRate);
    }

    private Money sameCurrency(Money other) {
        if (other.currency != currency) {
            throw new IllegalArgumentException("cannot mix " + currency + " and " + other.currency);
        }
        return other;
    }

    @Override
    public String toString() {
        int decimals = (int) Math.round(Math.log10(currency.getMinorUnitsPerUnit()));
        boolean decimal = Math.pow(10, decimals) == currency.getMinorUnitsPerUnit();
        if (!decimal) {
            long units = minorUnits / currency.getMinorUnitsPerUnit();
            long rest = Math.abs(minorUnits % currency.getMinorUnitsPerUnit());
            return currency.getSymbol() + units + " and " + rest + " " + currency.getMinorUnitName().orElse("");
        }
        return currency.getSymbol() + String.format(Locale.ROOT, "%." + decimals + "f", getAmount());
    }
}
