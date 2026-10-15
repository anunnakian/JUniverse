package com.enlil.universe.world.economy;

import com.enlil.universe.civilization.economy.Currency;
import com.enlil.universe.civilization.economy.MonetaryStandard;

/**
 * Real currencies, shared by the polities that use them: the euro is France's and Germany's, and also Monaco's and
 * Vatican City's by monetary agreement with the European Union.
 */
public final class Currencies {

    public static final Currency EURO =
            new Currency("Euro", "EUR", "€", MonetaryStandard.FIAT, "cent", 100);
    public static final Currency US_DOLLAR =
            new Currency("US dollar", "USD", "$", MonetaryStandard.FIAT, "cent", 100);
    /** The people's currency of China, counted in yuan of 100 fen. */
    public static final Currency RENMINBI =
            new Currency("Renminbi", "CNY", "¥", MonetaryStandard.FIAT, "fen", 100);
    /** The last currency to drop its gold backing, in 2000. */
    public static final Currency SWISS_FRANC =
            new Currency("Swiss franc", "CHF", "CHF ", MonetaryStandard.FIAT, "centime", 100);
    /** Decimal since 1971; a pound was 240 pence before. */
    public static final Currency POUND_STERLING =
            new Currency("Pound sterling", "GBP", "£", MonetaryStandard.FIAT, "penny", 100);
    public static final Currency SAUDI_RIYAL =
            new Currency("Saudi riyal", "SAR", "SR ", MonetaryStandard.FIAT, "halala", 100);
    /** Its sen are no longer in use, so the yen has no minor unit. */
    public static final Currency YEN =
            new Currency("Japanese yen", "JPY", "¥", MonetaryStandard.FIAT, null, 1);
    public static final Currency OMANI_RIAL =
            new Currency("Omani rial", "OMR", "OMR ", MonetaryStandard.FIAT, "baisa", 1_000);
    public static final Currency RINGGIT =
            new Currency("Malaysian ringgit", "MYR", "RM", MonetaryStandard.FIAT, "sen", 100);
    /** The silver coin of Rome, worth 16 bronze asses from about 141 BCE. */
    public static final Currency DENARIUS =
            new Currency("Denarius", "𐆖", MonetaryStandard.COMMODITY, "as", 16);

    /** The gold coin of Constantine (309), struck by the Merovingian kings, worth 3 tremisses. */
    public static final Currency SOLIDUS =
            new Currency("Solidus", "sol. ", MonetaryStandard.COMMODITY, "tremissis", 3);
    /**
     * The pound of Charlemagne's reform, a unit of account of 20 sous of 12 silver deniers each, 240 deniers in all,
     * until the Revolution.
     */
    public static final Currency LIVRE =
            new Currency("Livre", "₶", MonetaryStandard.COMMODITY, "denier", 240);
    /** The first decimal currency of France (1795), struck in silver and gold by the law of 7 Germinal year XI. */
    public static final Currency FRANC_GERMINAL =
            new Currency("Franc germinal", "₣", MonetaryStandard.COMMODITY, "centime", 100);
    /** The French franc once off gold, revalued as the new franc of 1960, replaced by the euro in 2002. */
    public static final Currency FRENCH_FRANC =
            new Currency("French franc", "FRF", "₣", MonetaryStandard.FIAT, "centime", 100);

    private Currencies() {
    }
}
