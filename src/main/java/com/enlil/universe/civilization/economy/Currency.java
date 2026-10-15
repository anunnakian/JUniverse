package com.enlil.universe.civilization.economy;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;

/**
 * A currency, divided into minor units: 100 cents to the euro, 1,000 baisa to the Omani rial, 16 asses to the Roman
 * denarius. A currency with no minor unit in use, like the yen, has one minor unit per unit.
 */
@Getter
public class Currency {

    private final String name;
    /** Null for a currency older than ISO 4217. */
    @Getter(AccessLevel.NONE)
    private final String isoCode;
    private final String symbol;
    private final MonetaryStandard standard;
    /** Null when no minor unit is in use. */
    @Getter(AccessLevel.NONE)
    private final String minorUnitName;
    private final int minorUnitsPerUnit;

    /** A modern currency with its ISO 4217 code; the minor unit name may be null if none is in use. */
    public Currency(String name, String isoCode, String symbol, MonetaryStandard standard,
                    String minorUnitName, int minorUnitsPerUnit) {
        if (isoCode == null || !isoCode.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("an ISO 4217 code has three capital letters: " + isoCode);
        }
        if (minorUnitsPerUnit < 1) {
            throw new IllegalArgumentException("a unit holds at least one minor unit");
        }
        this.name = name;
        this.isoCode = isoCode;
        this.symbol = symbol;
        this.standard = standard;
        this.minorUnitName = minorUnitName;
        this.minorUnitsPerUnit = minorUnitsPerUnit;
    }

    /** A currency older than ISO 4217, without a code. */
    public Currency(String name, String symbol, MonetaryStandard standard, String minorUnitName,
                    int minorUnitsPerUnit) {
        if (minorUnitsPerUnit < 1) {
            throw new IllegalArgumentException("a unit holds at least one minor unit");
        }
        this.name = name;
        this.isoCode = null;
        this.symbol = symbol;
        this.standard = standard;
        this.minorUnitName = minorUnitName;
        this.minorUnitsPerUnit = minorUnitsPerUnit;
    }

    /** The ISO 4217 code; empty for a currency older than the standard. */
    public Optional<String> getIsoCode() {
        return Optional.ofNullable(isoCode);
    }

    /** The name of the minor unit (cent, penny, as...); empty when none is in use. */
    public Optional<String> getMinorUnitName() {
        return Optional.ofNullable(minorUnitName);
    }

    @Override
    public String toString() {
        return isoCode != null ? isoCode : name;
    }
}
