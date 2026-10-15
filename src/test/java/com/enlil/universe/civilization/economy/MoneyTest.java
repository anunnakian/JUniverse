package com.enlil.universe.civilization.economy;

import com.enlil.universe.world.economy.Currencies;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    @DisplayName("0.1 + 0.2 euros should make exactly 30 cents, unlike 0.1 + 0.2 doubles")
    void exact_minor_units() {
        Money sum = Money.of(Currencies.EURO, 0.1).plus(Money.of(Currencies.EURO, 0.2));

        Assertions.assertEquals(new Money(Currencies.EURO, 30), sum);
        Assertions.assertEquals("€0.30", sum.toString());
    }

    @Test
    @DisplayName("each currency should print with its own decimals: none for the yen, three for the Omani rial")
    void decimals() {
        Assertions.assertEquals("¥1500", Money.of(Currencies.YEN, 1_500).toString());
        Assertions.assertEquals("OMR 1.250", Money.of(Currencies.OMANI_RIAL, 1.25).toString());
        Assertions.assertEquals("$-4.99", new Money(Currencies.US_DOLLAR, -499).toString());
    }

    @Test
    @DisplayName("a denarius should count in 16 asses, not in cents")
    void denarius() {
        Money wage = new Money(Currencies.DENARIUS, 16 * 3 + 4);

        Assertions.assertEquals(3.25, wage.getAmount(), 1e-9);
        Assertions.assertEquals("𐆖3 and 4 as", wage.toString());
    }

    @Test
    @DisplayName("100 euros should be worth about 82 after 10 years of 2 % inflation, and prices double in 35 years")
    void inflation() {
        Assertions.assertEquals(82.03, Money.of(Currencies.EURO, 100).purchasingPowerAfter(0.02, 10).getAmount(), 0.01);
        Assertions.assertEquals(35.0, Money.yearsToDoublePrices(0.02), 0.1);
    }

    @Test
    @DisplayName("money should not mix currencies")
    void rejects_mixed_currencies() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Money.of(Currencies.EURO, 1).plus(Money.of(Currencies.US_DOLLAR, 1)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Money.yearsToDoublePrices(0));
    }

    @Test
    @DisplayName("a currency should have at least one minor unit and a three-letter ISO code")
    void rejects_invalid_currencies() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency("Nothing", "NOT", "N", MonetaryStandard.FIAT, null, 0));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Currency("Euro", "euro", "€", MonetaryStandard.FIAT, "cent", 100));
    }
}
